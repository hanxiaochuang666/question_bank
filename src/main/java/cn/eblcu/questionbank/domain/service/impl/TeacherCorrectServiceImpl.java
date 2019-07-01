package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITeacherCorrectService;
import cn.eblcu.questionbank.domain.service.ITestPaperQuestionService;
import cn.eblcu.questionbank.infrastructure.util.CommonUtils;
import cn.eblcu.questionbank.infrastructure.util.DateUtils;
import cn.eblcu.questionbank.infrastructure.util.MapUtils;
import cn.eblcu.questionbank.infrastructure.util.StringUtils;
import cn.eblcu.questionbank.persistence.dao.IQuestionTypeDao;
import cn.eblcu.questionbank.persistence.dao.ITestPaperDao;
import cn.eblcu.questionbank.persistence.dao.ITestResultDao;
import cn.eblcu.questionbank.persistence.dao.ITestResultDetailDao;
import cn.eblcu.questionbank.persistence.entity.dto.QuestionType;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaper;
import cn.eblcu.questionbank.persistence.entity.dto.TestResult;
import cn.eblcu.questionbank.persistence.entity.dto.TestResultDetail;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.TeacherCorrectResultModel;
import cn.eblcu.questionbank.ui.model.TeacherPaperResultModel;
import cn.eblcu.questionbank.ui.model.TestPaperQuestionResModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TeacherCorrectServiceImpl implements ITeacherCorrectService {

    @Autowired
    private ITestResultDao testResultDao;
    @Autowired
    private ITestResultDetailDao testResultDetailDao;
    @Autowired
    private ITestPaperDao testPaperDao;
    @Autowired
    private IQuestionTypeDao questionTypeDao;
    @Autowired
    private ITestResultDetailDao detailDao;

    @Resource(name = "testPaperQuestionService")
    private ITestPaperQuestionService testPaperQuestionService;

    @Override
    public BaseModle getTestListByPaperId(String paperId) throws Exception {

        List<Map<String, Object>> list = new ArrayList<>();
        String[] paperIds = paperId.split(";");
        if (paperIds.length > 0) {
            for (String id : paperIds) {
                Map<String, Object> retMap = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                map.put("testPaperId", id);
                List<TestResult> results = testResultDao.selectList(map);
                Integer shouldCommit = null;
                Long haveCommit = null;
                Long haveCorrect = null;
                Long haveNotCorrect = null;
                if (CommonUtils.listIsEmptyOrNull(results)) {
                    shouldCommit = results.size();
                    haveCommit = results.stream().filter(r -> r.getStatus() == 2).count();
                    haveCorrect = results.stream().filter(r -> r.getStatus() == 4).count();
                    haveNotCorrect = haveCommit - haveCorrect;
                }
                TestPaper paper = new TestPaper();
                paper = testPaperDao.selectByPrimaryKey(Integer.parseInt(id));
                if (null == paper) {
                    throw new BusinessException("-1", "未查询到试卷！");
                }
                String period = "";
                period = DateUtils.date2String(paper.getStartTime(), "YYYY-MM-DD") + "~"
                        + DateUtils.date2String(paper.getStartTime(), "YYYY-MM-DD");
                retMap.put("shouldCommit", shouldCommit);
                retMap.put("haveCommit", haveCommit);
                retMap.put("haveCorrect", haveCorrect);
                retMap.put("haveNotCorrect", haveNotCorrect);
                retMap.put("paperName", paper.getName());
                retMap.put("period", period);
                list.add(retMap);
            }
        }
        return BaseModle.getSuccessData(list);
    }

    @Override
    public BaseModle getCorrectList(String paperId, String type) throws Exception {

        List<Map<String, Object>> list = new ArrayList<>();
        TestPaper paper = new TestPaper();
        paper = testPaperDao.selectByPrimaryKey(Integer.parseInt(paperId));
        if (null == paper) {
            throw new BusinessException("-1", "未查询到试卷！");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("testPaperId", paperId);
        List<TestResult> results = testResultDao.selectList(map);
        if (CommonUtils.listIsEmptyOrNull(results)) {
            if ("1".equals(type)) {// 已批阅
                for (TestResult r : results) {
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("studentName", r.getStudentName());
                    map1.put("endTime", DateUtils.date2String(r.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
                    map1.put("markingTime", DateUtils.date2String(r.getMarkingTime(), "yyyy-MM-dd HH:mm:ss"));
                    map1.put("totalScore", r.getTotalScore());
                    list.add(map1);
                }
            } else {// 未批阅
                for (TestResult r : results) {
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("studentName", r.getStudentName());
                    map1.put("endTime", DateUtils.date2String(r.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
                    list.add(map1);
                }
            }
        }
        return BaseModle.getSuccessData(list);
    }

    @Override
    public List<TestPaperQuestionResModel> getTestPaperInfo(Integer paperId, Integer studentId) throws Exception {

        List<TestPaperQuestionResModel> testPaperQuestionResModels = testPaperQuestionService.queryTestPaper(paperId, 1);
        Map<String, Object> initMap = MapUtils.initMap("studentId", studentId);
        initMap.put("testPaperId", paperId);
        List<TestResult> objects = testResultDao.selectList(initMap);
        if (CommonUtils.listIsEmptyOrNull(objects)) {
            log.info("试卷不匹配");
            throw new BusinessException("-1", "试卷不匹配");
        }
        Integer testResultId = objects.get(0).getTestResultId();
        initMap.clear();
        initMap.put("testResultId", testResultId);
        for (TestPaperQuestionResModel testPaperQuestionResModel : testPaperQuestionResModels) {
            List<Map<String, Object>> questionLst = testPaperQuestionResModel.getQuestionLst();
            for (Map<String, Object> stringObjectMap : questionLst) {
                String questionType = (String) stringObjectMap.get("questionType");
                QuestionType qType = questionTypeDao.selectByPrimaryKey(Integer.parseInt(questionType));
                if (!qType.getIsObjective()) {// 客观题跳过
                    continue;
                }
                initMap.put("questionId", stringObjectMap.get("questionId"));
                List<TestResultDetail> objectList = testResultDetailDao.selectList(initMap);
                if (CommonUtils.listIsEmptyOrNull(objectList)) {
                    TestResultDetail resultDetail = objectList.get(0);
                    String giveAnswer = resultDetail.getGiveAnswer();
                    if (!StringUtils.isEmpty(giveAnswer)) {
                        stringObjectMap.put("giveAnswer", giveAnswer);
                    }
                    Integer score = resultDetail.getScore();
                    if (score != null) {
                        if (score > 0) {
                            stringObjectMap.put("isTrue", true);
                        } else {
                            stringObjectMap.put("isTrue", false);
                        }
                        stringObjectMap.put("score", score);
                    }
                    String comment = resultDetail.getComment();
                    if (StringUtils.isEmpty(comment)) {
                        stringObjectMap.put("comment", comment);
                    }
                }
            }
        }
        return testPaperQuestionResModels;
    }

    @Override
    public BaseModle saveTeacherCorrect(TeacherPaperResultModel model) throws Exception{

        Integer totalScore = 0;
        Integer zhuGuanScore = 0;
        Integer paperId = model.getPaperId();
        Integer studentId = model.getStudentId();
        List<TeacherCorrectResultModel> resultList = model.getResultList();

        Map<String,Object> map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("testPaperId", paperId);
        List<TestResult> results = testResultDao.selectList(map);
        if(CommonUtils.listIsEmptyOrNull(results)){
            throw new BusinessException("-1","未查询到试卷！");
        }
        TestResult testResult = results.get(0);

        if(CommonUtils.listIsEmptyOrNull(resultList)){
            for (TeacherCorrectResultModel resultModel : resultList) {
                zhuGuanScore += resultModel.getScore();
                TestResultDetail detail = new TestResultDetail();
                detail.setComment(resultModel.getComment());
                detail.setScore(resultModel.getScore());
                detailDao.updateByPrimaryKeySelective(detail);
            }
        }

        // 更新分数和时间
        totalScore = testResult.getObjectiveScore() + zhuGuanScore;
        testResult.setStatus(4);
        testResult.setMarkingTime(DateUtils.now());
        testResult.setSubjectiveScore(zhuGuanScore);
        testResult.setTotalScore(totalScore);
        testPaperDao.updateByPrimaryKeySelective(testResult);

        return BaseModle.getSuccessData();
    }
}
