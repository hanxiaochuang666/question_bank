package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.StudentCourseModel;

import java.util.List;

public interface ITestResultService extends IBaseService {
    /**
     * 同步学生作业
     * @param userId
     * @param courseId
     * @param studentId
     * @param studentName
     * @return
     */
    BaseModle syncTestPaper(Integer userId,Integer courseId,Integer studentId,String studentName);

    /**
     * 关联作业后的回调
     * @param courseId
     * @param testPaperId
     * @return
     */
    BaseModle addTestCallBack(Integer courseId,Integer testPaperId);

    /**
     * 学生作业测试列表查询
     * @param useType
     * @param studentId
     * @param courseId
     * @return
     */
    List<StudentCourseModel> getStudentCourseLst(Integer useType,Integer studentId,Integer courseId)throws Exception;

    /**
     *用于检测试卷的定时任务
     */
    void checkTestResultStatus();
}