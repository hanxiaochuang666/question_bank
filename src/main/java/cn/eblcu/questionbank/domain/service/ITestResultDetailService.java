package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.TestPaperAnswerViewModel;
import cn.eblcu.questionbank.ui.model.TestPaperQuestionResModel;

import java.util.List;

public interface ITestResultDetailService extends IBaseService {

    /**
     * 学生试卷操作查询
     * @param studentId
     * @param testPaperId
     * @param optType
     * @return
     */
    List<TestPaperQuestionResModel> selectTestPaperInfo(int studentId,int testPaperId,int optType)throws Exception;

    /**
     * 学生提交试卷
     * @param studentId
     * @param testPaperAnswerViewModel
     * @return
     */
    BaseModle saveTestResultDetailInfo(int studentId,TestPaperAnswerViewModel testPaperAnswerViewModel);
}