package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.IQuestionService;
import cn.eblcu.questionbank.infrastructure.util.CommonUtils;
import cn.eblcu.questionbank.infrastructure.util.DateUtils;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.IQuestionDao;
import cn.eblcu.questionbank.persistence.dao.IQuestionTypeDao;
import cn.eblcu.questionbank.persistence.entity.dto.Question;
import cn.eblcu.questionbank.persistence.entity.dto.QuestionType;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.QuestionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("questionService")
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl implements IQuestionService {

    @Autowired
    private IQuestionDao questionDao;
    @Autowired
    private IQuestionTypeDao questionTypeDao;

    @Override
    IBaseDao getDao() {
        return this.questionDao;
    }

    @Override
    @Transactional
    public void insertQuestion(QuestionModel model,HttpServletRequest httpServletRequest) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        // 如果是综合题，先插入小题
        StringBuilder childQuestionIds = new StringBuilder();
        String childIds = "";
        List<QuestionModel> models = new ArrayList<>();
        models = model.getModelList();
        if (!CommonUtils.listIsEmptyOrNull(models)){
            for (QuestionModel m : models) {
                childQuestionIds.append(insert(m, userId, childQuestionIds.toString())).append(";");
            }
            childIds = childQuestionIds.substring(0, childQuestionIds.length()-1);
            log.info("子题主键id字符串==================="+childIds);
        }
        insert(model, userId,childIds);
    }

    private int insert(QuestionModel model,int userId,String childQuestionIds) throws BusinessException{

        List<QuestionType> types = new ArrayList<>();
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("code", model.getQuestionType());
        QuestionType type = new QuestionType();
        type.setCode(model.getQuestionType());
        types = questionTypeDao.selectList(paraMap);
        if (CommonUtils.listIsEmptyOrNull(types)){
            throw new BusinessException("-1", "未查询到该试题类型【"+model.getQuestionType()+"】，请核实！");
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
        if("zongHe".equals(model.getQuestionType()) || "peiDui".equals(model.getQuestionType())){
            question.setQuestionOpt(childQuestionIds);
        }else{
            question.setQuestionOpt(model.getQuestionOpt());
        }
        question.setQuestionResolve(model.getQuestionResolve());
        question.setQuestionSound(model.getQuestionSound());
        question.setQuestionType(types.get(0).getQuestionTypeId());
        question.setUpdateUser(userId);
        insertSelective(question);
        log.info("主键id【{}】",question.getQuestionId());
        return question.getQuestionId();
    }

    @Override
    @Transactional
    public void editQuestion(QuestionModel model, HttpServletRequest httpServletRequest) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        Question question = new Question();
        List<Question> questions = new ArrayList<>();
        question = selectByPrimaryKey(model.getQuestionId());
        if (null == question){
            throw new BusinessException("-1","试题不存在！" );
        }
        update(model,userId);
    }

    private void update(QuestionModel model,int userId) throws BusinessException{

        List<QuestionType> types = new ArrayList<>();
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("code", model.getQuestionType());
        QuestionType type = new QuestionType();
        type.setCode(model.getQuestionType());
        types = questionTypeDao.selectList(paraMap);
        if (CommonUtils.listIsEmptyOrNull(types)){
            throw new BusinessException("-1", "未查询到该试题类型【"+model.getQuestionType()+"】，请核实！");
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
    public List<Map<String, Object>> selectQuestionListCount(HttpServletRequest httpServletRequest,Map<String,Object> map) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        List<Map<String,Object>> questionCountList = new ArrayList<>();
        /*if (!havePower(httpServletRequest)){ // 没有权限就只能看自己的试题
            map.put("createUserId", userId);
        }*/
        questionCountList = questionDao.queryQuestionListCount(map);
        return questionCountList;
    }

    @Override
    public List<Question> selectQuestionList(HttpServletRequest httpServletRequest, Map<String, Object> map) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        List<Question> questions = new ArrayList<>();

        /*if (!havePower(httpServletRequest)){// 没有权限就只能查询自己的试题
            map.put("createUserId", userId);
        }*/
        if (-1 == Integer.parseInt(String.valueOf(map.get("questionType")))){
            map.remove("questionType");
        }
        questions = selectList(map);
        return questions;
    }

    private boolean havePower(HttpServletRequest httpServletRequest) throws Exception{

        boolean havePower = false;
        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        // TODO 调接口校验有没有权限

        return havePower;
    }

    @Override
    public List<Question> selectQuestionListByName(HttpServletRequest httpServletRequest, Map<String, Object> map) throws Exception {
        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        List<Question> questions = new ArrayList<>();

        /*if (!havePower(httpServletRequest)){// 没有权限就只能查询自己的试题
            map.put("createUserId", userId);
        }*/
        if (-1 == Integer.parseInt(String.valueOf(map.get("questionType")))){
            map.remove("questionType");
        }
        questions = selectList(map);
        return questions;
    }
}