package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.persistence.entity.dto.TestPaperQuestion;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.TestPaperQuestionResModel;

import java.util.List;

public interface ITestPaperQuestionService extends IBaseService {
    /**
     * 试卷内容查询
     * @param testPaperId
     * @return
     * @throws Exception
     */
    List<TestPaperQuestionResModel> queryTestPaper(int testPaperId)throws Exception;

    /**
     * 保存试卷内容
     * @param testPaperQuestionLst
     * @return
     */
    BaseModle saveTestPaperQuestion(List<TestPaperQuestion> testPaperQuestionLst)throws Exception;

    /**
     * 智能组卷
     * @param testPaperId
     * @param knowledges
     */
    BaseModle intellectPaper(int testPaperId,String knowledges)throws Exception;

    /**
     * 根据试卷id生成试卷word
     * @param testPaperId
     * @return String
     */
    String createNewWord(int testPaperId)throws Exception;
}