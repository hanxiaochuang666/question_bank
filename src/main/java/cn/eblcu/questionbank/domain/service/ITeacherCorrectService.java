package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.TeacherPaperResultModel;
import cn.eblcu.questionbank.ui.model.TestPaperQuestionResModel;

import java.util.List;

public interface ITeacherCorrectService {

    BaseModle getTestListByPaperId(String paperIds) throws Exception;

    BaseModle getCorrectList(String paperId,String type) throws Exception;

    List<TestPaperQuestionResModel> getTestPaperInfo(Integer paperId, Integer studentId) throws Exception;

    BaseModle saveTeacherCorrect(TeacherPaperResultModel model) throws Exception;
}
