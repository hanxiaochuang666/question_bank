package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestResultService;
import cn.eblcu.questionbank.infrastructure.util.DateUtils;
import cn.eblcu.questionbank.infrastructure.util.MapUtils;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.ITestPaperDao;
import cn.eblcu.questionbank.persistence.dao.ITestResultDao;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaper;
import cn.eblcu.questionbank.persistence.entity.dto.TestResult;
import cn.eblcu.questionbank.ui.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("testResultService")
public class TestResultServiceImpl extends BaseServiceImpl implements ITestResultService {

    Logger logger=LoggerFactory.getLogger(TestResultServiceImpl.class);
    @Autowired
    private ITestResultDao testResultDao;

    @Autowired
    private ITestPaperDao testPaperDao;

    @Override
    IBaseDao getDao() {
        return this.testResultDao;
    }

    @Override
    @Transactional
    public BaseModle syncTestPaper(Integer userId, Integer courseId, Integer studentId, String studentName) {
        Map<String, Object> initMap = MapUtils.initMap("courseId", courseId);
        initMap.put("createUser",userId);
        List<TestPaper> testPaperList = testPaperDao.selectList(initMap);
        if(testPaperList.size()<=0)
            return BaseModle.getSuccessData();
        for (TestPaper testPaper : testPaperList) {
            TestResult testResult=new TestResult();
            testResult.setStudentId(studentId);
            testResult.setStudentName(studentName);
            testResult.setTestPaperId(testPaper.getTestPaperId());
            testResult.setStatus(0);
            testResultDao.insertNoExists(testResult);
        }
        return BaseModle.getSuccessData();
    }

    @Override
    public BaseModle addTestCallBack(Integer courseId, Integer testPaperId) {
        TestPaper testPaper = testPaperDao.selectByPrimaryKey(testPaperId);
        if(null==testPaper){
            logger.info("试卷不存在:(试卷id"+testPaperId+")");
            return BaseModle.getFailData(StatusCodeEnum.PARAM_ERROR.getCode(),"试卷不存在:(试卷id"+testPaperId+")");
        }
        if(testPaper.getCourseId().intValue()!=courseId.intValue()){
            logger.info("课程id与试卷id不匹配");
            return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"课程id与试卷id不匹配");
        }
        //根据课程id,调用商城（课程平台查询当前课程的所有学生信息）
        List<Student> studentList=new ArrayList<>();
        //测试造价数据
        //*******
        Student student1 =new Student();
        student1.setName("韩闯");
        student1.setStudentId(1);
        Student student2 =new Student();
        student2.setName("李涛");
        student2.setStudentId(2);
        studentList.add(student1);
        studentList.add(student2);
        //*******
        for (Student student : studentList) {
            TestResult testResult=new TestResult();
            testResult.setStudentId(student.getStudentId());
            testResult.setStudentName(student.getName());
            testResult.setTestPaperId(testPaperId);
            testResult.setStatus(0);
            testResultDao.insertNoExists(testResult);
        }
        return BaseModle.getSuccessData();
    }

    @Override
    public List<StudentCourseModel> getStudentCourseLst(Integer useType, Integer studentId, Integer courseId) throws Exception{
        Map<String, Object> initMap = MapUtils.initMap("studentId", studentId);
        List<TestResult> testResults = testResultDao.selectList(initMap);
        Map<String,List<StudentCourseInfoModel>> map=new HashMap<>();
        initMap.clear();
        if(courseId!=null)
            initMap.put("courseId",courseId);
        if(useType!=null)
            initMap.put("useType",useType);
        for (TestResult testResult : testResults) {
            Integer testPaperId = testResult.getTestPaperId();
            if(null!=testPaperId){
                initMap.put("testPaperId",testPaperId);
                List<TestPaper> testPaperLst = testPaperDao.selectList(initMap);
                if(testPaperLst.size()<=0)
                    continue;
                TestPaper testPaper=testPaperLst.get(0);
                if(null!=testPaper){
                    String name = testPaper.getCourseName();
                    StudentCourseInfoModel studentCourseInfoModel = new StudentCourseInfoModel();
                    studentCourseInfoModel.setStatus(testResult.getStatus());
                    studentCourseInfoModel.setTestPaperId(testPaperId);
                    studentCourseInfoModel.setTestPaperName(testPaper.getName());
                    Date startTime = testPaper.getStartTime();
                    String startTimeStr = DateUtils.date2String(startTime, "yyy-MM-dd");
                    Date endTime = testPaper.getEndTime();
                    String endTimeStr =DateUtils.date2String(endTime, "yyy-MM-dd");
                    studentCourseInfoModel.setDateTime(startTimeStr+"~"+endTimeStr);
                    if(map.containsKey(name)){
                        List<StudentCourseInfoModel> studentCourseInfoModels = map.get(name);
                        studentCourseInfoModels.add(studentCourseInfoModel);
                    }else {
                        List<StudentCourseInfoModel> studentCourseInfoModels=new ArrayList<>();
                        studentCourseInfoModels.add(studentCourseInfoModel);
                        map.put(name,studentCourseInfoModels);
                    }
                }
            }
        }
        List<StudentCourseModel> resList=new ArrayList<>();
        for(Map.Entry<String, List<StudentCourseInfoModel>> entry:map.entrySet()){
            StudentCourseModel temp=new StudentCourseModel();
            temp.setCourseName(entry.getKey());
            temp.setStudentCourseInfoLst(entry.getValue());
            resList.add(temp);
        }

        return resList;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "0 1/1 * * * ?")
    @Transactional
    public void checkTestResultStatus() {
        logger.info("开始执行定时任务....");
        Map<String, Object> initMap = MapUtils.initMap("maxStatus", 1);
        initMap.put("forUpdate",true);
        List<TestResult> testResults = getDao().selectList(initMap);
        Date now=new Date();
        for (TestResult testResult : testResults) {
            Integer testPaperId = testResult.getTestPaperId();
            TestPaper testPaper = testPaperDao.selectByPrimaryKey(testPaperId);
            if(testPaper.getEndTime().before(now)){
                testResult.setStatus(5);
                getDao().updateByPrimaryKeySelective(testResult);
            }
        }
    }
}