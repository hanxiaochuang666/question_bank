package cn.eblcu.questionbank.domain.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.eblcu.questionbank.domain.service.IQuestionService;
import cn.eblcu.questionbank.infrastructure.util.CommonUtils;
import cn.eblcu.questionbank.infrastructure.util.DateUtils;
import cn.eblcu.questionbank.infrastructure.util.StringUtils;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.persistence.dao.*;
import cn.eblcu.questionbank.persistence.entity.dto.KnowledgePoints;
import cn.eblcu.questionbank.persistence.entity.dto.KnowledgePointsDetail;
import cn.eblcu.questionbank.persistence.entity.dto.Question;
import cn.eblcu.questionbank.persistence.entity.dto.QuestionType;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.QuestionExcelModel;
import cn.eblcu.questionbank.ui.model.QuestionModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Service("questionService")
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl implements IQuestionService {

    @Autowired
    private IQuestionDao questionDao;
    @Autowired
    private IQuestionTypeDao questionTypeDao;
    @Autowired
    private IKnowledgePointsDao pointsDao;
    @Autowired
    private IKnowledgePointsDetailDao detailDao;

    @Value("${excel.questionFields}")
    private String questionFields;

    @Override
    IBaseDao getDao() {
        return this.questionDao;
    }

    @Override
    @Transactional
    public void insertQuestion(QuestionModel model, HttpServletRequest httpServletRequest) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        // 如果是综合题，或者配对题先插入小题
        StringBuilder childQuestionIds = new StringBuilder();
        String childIds = "";
        List<QuestionModel> models = new ArrayList<>();
        models = model.getModelList();
        if (!CommonUtils.listIsEmptyOrNull(models)) {
            for (QuestionModel m : models) {
                childQuestionIds.append(insert(m, userId, childQuestionIds.toString(), true)).append(";");
            }
            childIds = childQuestionIds.substring(0, childQuestionIds.length() - 1);
            log.info("子题主键id字符串===================" + childIds);
        }
        insert(model, userId, childIds, false);
    }

    private int insert(QuestionModel model, int userId, String childQuestionIds, boolean isChild) throws BusinessException {

        List<QuestionType> types = new ArrayList<>();
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("code", model.getQuestionType());
        QuestionType type = new QuestionType();
        type.setCode(model.getQuestionType());
        types = questionTypeDao.selectList(paraMap);
        if (CommonUtils.listIsEmptyOrNull(types)) {
            throw new BusinessException("-1", "未查询到该试题类型【" + model.getQuestionType() + "】，请核实！");
        }

        Question question = new Question();
        question.setCategoryOne(model.getCategoryOne());
        question.setCategoryTwo(model.getCategoryTwo());
        question.setCourseId(model.getCourseId());
        question.setDifficultyLevel(model.getDifficultyLevel());
        question.setCreateTime(DateUtils.now());
        // 知识点如果是-1表示全部的知识点
        question.setKnowledgePoints(model.getKnowledgePoints());
        question.setOrgId(model.getOrgId());
        question.setCreateUser(userId);
        question.setQuestionBody(model.getQuestionBody());
        question.setQuestionAnswer(model.getQuestionAnswer());
        // TODO 如何判断试题类型
        if ("zongHe".equals(model.getQuestionType()) || "peiDui".equals(model.getQuestionType())) {
            question.setQuestionOpt(childQuestionIds);
        } else {
            question.setQuestionOpt(model.getQuestionOpt());
        }
        question.setQuestionResolve(model.getQuestionResolve());
        question.setQuestionSound(model.getQuestionSound());
        if (isChild) {
            question.setQuestionType(100 + types.get(0).getQuestionTypeId());
        } else {
            question.setQuestionType(types.get(0).getQuestionTypeId());
        }
        question.setUpdateUser(userId);
        insertSelective(question);
        log.info("主键id【{}】", question.getQuestionId());
        return question.getQuestionId();
    }

    @Override
    @Transactional
    public void editQuestion(QuestionModel model, HttpServletRequest httpServletRequest) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        Question question = new Question();
        List<Question> questions = new ArrayList<>();
        question = selectByPrimaryKey(model.getQuestionId());
        if (null == question) {
            throw new BusinessException("-1", "试题不存在！");
        }
        update(model, userId);
    }

    private void update(QuestionModel model, int userId) throws BusinessException {

        List<QuestionType> types = new ArrayList<>();
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("code", model.getQuestionType());
        QuestionType type = new QuestionType();
        type.setCode(model.getQuestionType());
        types = questionTypeDao.selectList(paraMap);
        if (CommonUtils.listIsEmptyOrNull(types)) {
            throw new BusinessException("-1", "未查询到该试题类型【" + model.getQuestionType() + "】，请核实！");
        }
        Question question = new Question();
        question.setQuestionId(model.getQuestionId());
        question.setCategoryOne(model.getCategoryOne());
        question.setCategoryTwo(model.getCategoryTwo());
        question.setCourseId(model.getCourseId());
        question.setDifficultyLevel(model.getDifficultyLevel());
        question.setUpdateTime(DateUtils.now());
        question.setKnowledgePoints(model.getKnowledgePoints());
        question.setOrgId(model.getOrgId());
        question.setQuestionBody(model.getQuestionBody());
        question.setQuestionAnswer(model.getQuestionAnswer());
        question.setQuestionOpt(model.getQuestionOpt());
        question.setQuestionResolve(model.getQuestionResolve());
        question.setQuestionSound(model.getQuestionSound());
        question.setQuestionType(types.get(0).getQuestionTypeId());
        question.setUpdateUser(userId);
        updateByPrimaryKeySelective(question);
    }

    @Override
    public List<Map<String, Object>> selectQuestionListCount(HttpServletRequest httpServletRequest, Map<String, Object> map) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        List<Map<String, Object>> questionCountList = new ArrayList<>();
        /*if (!havePower(httpServletRequest)){ // 没有权限就只能看自己的试题
            map.put("createUserId", userId);
        }*/
        questionCountList = questionDao.queryQuestionListCount(map);
        return questionCountList;
    }

    @Override
    public List<Question> selectQuestionList(HttpServletRequest httpServletRequest, Map<String, Object> map) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        /*if (!havePower(httpServletRequest)){// 没有权限就只能查询自己的试题
            map.put("createUserId", userId);
        }*/
        return selectByPointAndName(map);
    }

    private boolean havePower(HttpServletRequest httpServletRequest) throws Exception {

        boolean havePower = false;
        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        // TODO 调接口校验有没有权限

        return havePower;
    }

    private List<Question> selectByPointAndName(Map<String, Object> map) throws BusinessException {

        List<Question> questions = new ArrayList<>();
        String points = String.valueOf(map.get("points"));
        Integer courseId = Integer.parseInt(String.valueOf(map.get("courseId")));
        List<Question> questionsList = new ArrayList<>();
        String questionType = String.valueOf(map.get("questionType"));
        String difficultyLevel = String.valueOf(map.get("difficultyLevel"));
        if (StringUtils.isEmpty(questionType) || "-1".equals(questionType) || "null".equals(questionType)) { // 试题类型全选
            map.remove("questionType");
        } else {
            List<QuestionType> types = new ArrayList<>();
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("code", questionType);
            types = questionTypeDao.selectList(paraMap);
            if (CommonUtils.listIsEmptyOrNull(types)) {
                throw new BusinessException("-1", "未查询到该试题类型【" + questionType + "】，请核实！");
            } else {
                map.put("questionType", types.get(0).getQuestionTypeId());
            }
        }
        if (StringUtils.isEmpty(difficultyLevel) || "-1".equals(difficultyLevel) || "null".equals(difficultyLevel)) { // 难度全部
            map.remove("difficultyLevel");
        }
        if (StringUtils.isEmpty(points) || "-1".equals(points) || "null".equals(points)) {// 知识点全选
            // 查询知识点
            Map<String, Object> pMap = new HashMap<>();
            pMap.put("courseId", courseId);
            List<KnowledgePointsDetail> details = detailDao.selectList(pMap);
            if (!CommonUtils.listIsEmptyOrNull(details)) {
                details.forEach(p -> {
                    map.put("knowledgePoints", p.getKnowledgePointsDetailId());
                    questionsList.addAll(questionDao.selectListByPoints(map));
                });
            }
        } else {
            String[] pointStr = points.split(";");
            if (pointStr.length > 0) {
                for (String s : pointStr) {
                    map.put("knowledgePoints", s);
                    questionsList.addAll(questionDao.selectListByPoints(map));
                }
            }
        }
        // 根据题干去重
        questions = questionsList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(Question::getQuestionBody))), ArrayList::new));
        // 过滤掉子题
        questions = questions.stream().filter(q -> q.getQuestionType() < 100).collect(Collectors.toList());
        return questions;
    }


    @Override
    @Transactional
    public void importQuestion(Map<String, Object> paraMap, MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {

        if (!havePower(httpServletRequest)) {
//            throw new BusinessException("-1", "没有权限！");
        }
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);// 表头
        importParams.setTitleRows(0);
        // 验证字段
        importParams.setNeedVerfiy(true);
        // 验证标题是否正确
        importParams.setImportFields(questionFields.split(","));
        try {
            ExcelImportResult<QuestionExcelModel> result = ExcelImportUtil.importExcelMore(file.getInputStream(), QuestionExcelModel.class,
                    importParams);
            List<QuestionExcelModel> successList = result.getList();
            List<QuestionExcelModel> failList = result.getFailList();
            if (!StringUtils.isEmpty(failList) && failList.size() > 0) {
                throw new BusinessException("9998", "上传失败：" + failList.size() + "条，请检查excel内容是否规范！");
            }
            insertExcelQuestion(successList, paraMap, httpServletRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("9999", "上传出错：" + e.getMessage());
        }
    }

    void insertExcelQuestion(List<QuestionExcelModel> successList, Map<String, Object> paraMap, HttpServletRequest httpServletRequest) throws Exception {

        Integer courseId = Integer.parseInt(String.valueOf(paraMap.get("courseId")));

        Integer knowledgeId;
        List<QuestionModel> childModels = new ArrayList<>();

        List<QuestionExcelModel> childQuestions = new ArrayList<>();
        List<QuestionExcelModel> singleQuestions = new ArrayList<>();
        childQuestions = successList.stream().filter(s ->
                !StringUtils.isEmpty(s.getChildQuestionType()) || "配对题".equals(s.getQuestionType()) || "综合题".equals(s.getQuestionType()))
                .collect(Collectors.toList());
        singleQuestions = successList.stream().filter(s ->
                StringUtils.isEmpty(s.getChildQuestionType()) && !"配对题".equals(s.getQuestionType()) && !"综合题".equals(s.getQuestionType()))
                .collect(Collectors.toList());
        Map<String, Object> pointMap = new HashMap<>();
        pointMap.put("courseId", courseId);
        List<KnowledgePoints> points = pointsDao.selectList(pointMap);
        if (CommonUtils.listIsEmptyOrNull(points)) {
            throw new BusinessException("-1", "未查询到该课程！");
        } else {
            knowledgeId = points.get(0).getKnowledgePointsId();
        }
        // 独立的题导入
        insertSingleQuestion(httpServletRequest, singleQuestions, knowledgeId, paraMap);
        // 带子题的导入
        insertChildQuestion(httpServletRequest, childQuestions, knowledgeId, paraMap);
    }

    private void insertSingleQuestion(HttpServletRequest httpServletRequest, List<QuestionExcelModel> singleQuestions,
                                      Integer knowledgeId, Map<String, Object> paraMap) throws Exception {

        List<QuestionModel> singleModels = new ArrayList<>();
        Integer categoryOne = Integer.parseInt(String.valueOf(paraMap.get("categoryOne")));
        Integer categoryTwo = Integer.parseInt(String.valueOf(paraMap.get("categoryTwo")));
        Integer courseId = Integer.parseInt(String.valueOf(paraMap.get("courseId")));
        Integer orgId = Integer.parseInt(String.valueOf(paraMap.get("orgId")));

        for (QuestionExcelModel ss : singleQuestions) {
            QuestionType type = getQuestionTypeByName(ss.getQuestionType());
            QuestionModel cModel = new QuestionModel();
            cModel.setQuestionType(type.getCode());
            cModel.setQuestionBody(ss.getQuestionBody());
            cModel.setCategoryOne(categoryOne);
            cModel.setCategoryTwo(categoryTwo);
            cModel.setCourseId(courseId);
            cModel.setOrgId(orgId);
            cModel.setDifficultyLevel(ss.getDifficultyLevel());
            cModel.setQuestionResolve(ss.getQuestionResolve());
            if (!StringUtils.isEmpty(ss.getQuestionAnswer())) {
                cModel.setQuestionAnswer(getAnswerJson(ss.getQuestionAnswer(), type.getCode()));
            }
            if (!StringUtils.isEmpty(ss.getQuestionOpt())) {
                cModel.setQuestionOpt(getJsonOption(ss.getQuestionOpt()));
            }
            if (!StringUtils.isEmpty(ss.getKnowledgePoints())) {
                cModel.setKnowledgePoints(getPointsByName(ss.getKnowledgePoints(), knowledgeId));
            }
            singleModels.add(cModel);
        }
        if (singleModels.size() > 0) {
            for (QuestionModel m : singleModels) {
                insertQuestion(m, httpServletRequest);
            }
        }
    }

    private void insertChildQuestion(HttpServletRequest httpServletRequest, List<QuestionExcelModel> childQuestions,
                                     Integer knowledgeId, Map<String, Object> paraMap) throws Exception {

        List<QuestionModel> mainModels = new ArrayList<>();
        List<QuestionExcelModel> mains = new ArrayList<>();
        List<QuestionExcelModel> childs = new ArrayList<>();

        Integer categoryOne = Integer.parseInt(String.valueOf(paraMap.get("categoryOne")));
        Integer categoryTwo = Integer.parseInt(String.valueOf(paraMap.get("categoryTwo")));
        Integer courseId = Integer.parseInt(String.valueOf(paraMap.get("courseId")));
        Integer orgId = Integer.parseInt(String.valueOf(paraMap.get("orgId")));

        mains = childQuestions.stream().filter(c -> StringUtils.isEmpty(c.getChildQuestionType())).collect(Collectors.toList());
        childs = childQuestions.stream().filter(c -> !StringUtils.isEmpty(c.getChildQuestionType())).collect(Collectors.toList());


        for (QuestionExcelModel s : mains) {
            List<QuestionModel> modelList = new ArrayList<>();

            QuestionType questionType = getQuestionTypeByName(s.getQuestionType());
            QuestionModel model = new QuestionModel();
            model.setQuestionType(questionType.getCode());
            model.setQuestionBody(s.getQuestionBody());
            model.setCategoryOne(categoryOne);
            model.setCategoryTwo(categoryTwo);
            model.setCourseId(courseId);
            model.setOrgId(orgId);
            model.setDifficultyLevel(s.getDifficultyLevel());
            model.setQuestionResolve(s.getQuestionResolve());
            if ("peiDui".equals(questionType.getCode())) {
                model.setQuestionAnswer(getJsonOption(s.getQuestionOpt()));
                for (QuestionExcelModel ss : childs) {
                    if ("配对题".equals(ss.getQuestionType())) {
                        modelList.add(getModel(ss, categoryOne, categoryTwo, courseId, orgId, knowledgeId));
                    }
                }
                model.setModelList(modelList);
            }
            if ("zongHe".equals(questionType.getCode())) {
                for (QuestionExcelModel ss : childs) {
                    if ("综合题".equals(ss.getQuestionType())) {
                        modelList.add(getModel(ss, categoryOne, categoryTwo, courseId, orgId, knowledgeId));
                    }
                }
                model.setModelList(modelList);
            }
            model.setKnowledgePoints(getPointsByName(s.getKnowledgePoints(), knowledgeId));
            mainModels.add(model);
        }
        if (mainModels.size() > 0) {
            for (QuestionModel m : mainModels) {
                insertQuestion(m, httpServletRequest);
            }
        }
    }

    private QuestionModel getModel(QuestionExcelModel ss, Integer categoryOne, Integer categoryTwo,
                                   Integer courseId, Integer orgId, Integer knowledgeId) throws Exception {
        QuestionType type = getQuestionTypeByName(ss.getChildQuestionType());
        QuestionModel cModel = new QuestionModel();
        cModel.setQuestionType(type.getCode());
        cModel.setQuestionBody(ss.getQuestionBody());
        cModel.setCategoryOne(categoryOne);
        cModel.setCategoryTwo(categoryTwo);
        cModel.setCourseId(courseId);
        cModel.setOrgId(orgId);
        cModel.setDifficultyLevel(ss.getDifficultyLevel());
        cModel.setQuestionResolve(ss.getQuestionResolve());
        if (!StringUtils.isEmpty(ss.getQuestionAnswer())) {
            cModel.setQuestionAnswer(getAnswerJson(ss.getQuestionAnswer(), type.getCode()));
        }
        if (!StringUtils.isEmpty(ss.getQuestionOpt())) {
            cModel.setQuestionOpt(getJsonOption(ss.getQuestionOpt()));
        }
        if (!StringUtils.isEmpty(ss.getKnowledgePoints())) {
            cModel.setKnowledgePoints(getPointsByName(ss.getKnowledgePoints(), knowledgeId));
        }
        return cModel;
    }


    private QuestionType getQuestionTypeByName(String name) throws BusinessException {
        List<QuestionType> types = new ArrayList<>();
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("name", name);
        types = questionTypeDao.selectList(paraMap);
        if (CommonUtils.listIsEmptyOrNull(types)) {
            throw new BusinessException("-1", "未查询到该试题类型【" + name + "】，请核实！");
        }
        return types.get(0);
    }


    private String getAnswerJson(String answer, String questionType) {

        String answerS = "";
        StringBuilder answerStr = new StringBuilder();
        switch (questionType) {
            case "duoXuan":
            case "tianKong":
                String[] ans = answer.split(";");
                if (ans.length > 0) {
                    for (String an : ans) {
                        answerStr.append(an).append("#&&&#");
                    }
                }
                answerS = answerStr.toString().substring(0, answerStr.length() - 5);
                break;
            case "xuanCi":
                String[] anss = answer.split(";");
                if (anss.length > 0) {
                    for (String an : anss) {
                        answerStr.append(an).append("   ");
                    }
                }
                answerS = answerStr.toString().trim();
                break;
            default:
                answerS = answerStr.append(answer).toString();

        }
        return answerS;
    }

    private String getJsonOption(String option) {

        JSONArray array = new JSONArray();
        String[] opts = option.split(";");
        if (opts.length > 0) {
            for (String opt : opts) {
                JSONObject object = new JSONObject();
                object.put("option", opt);
                array.add(object);
            }
        }
        log.info("选项转换成JSON格式=================" + array.toJSONString());
        return array.toJSONString();
    }

    private String getPointsByName(String pointsName, Integer knowledgeId) {

        log.info("知识点列表=================" + pointsName);
        StringBuilder names = new StringBuilder();
        String[] points = pointsName.split(";");
        Map<String, Object> paraMap = new HashMap<>();
        if (points.length > 0) {
            for (String pointName : points) {
                List<KnowledgePointsDetail> details = new ArrayList<>();
                paraMap.put("knowledgePointsId", knowledgeId);
                paraMap.put("name", pointName);
                details = detailDao.selectList(paraMap);
                if (!CommonUtils.listIsEmptyOrNull(details)) {
                    names.append(details.get(0).getKnowledgePointsDetailId()).append(";");
                }
            }
        }
        String retName = names.toString();
        if (retName.length() > 0) {
            retName = retName.substring(0, retName.length() - 1);
        }
        log.info("转换后的知识点主键列表=================" + retName);
        return retName;

    }


}