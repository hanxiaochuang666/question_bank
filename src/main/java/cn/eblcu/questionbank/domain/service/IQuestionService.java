package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.persistence.entity.dto.Question;
import cn.eblcu.questionbank.ui.model.QuestionModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IQuestionService extends IBaseService {

    void insertQuestion(QuestionModel model, HttpServletRequest httpServletRequest) throws Exception;

    void editQuestion(QuestionModel model, HttpServletRequest httpServletRequest) throws Exception;

    List<Map<String,Object>> selectQuestionListCount(HttpServletRequest httpServletRequest,Map<String,Object> map) throws Exception;

    List<Question> selectQuestionList(HttpServletRequest httpServletRequest, Map<String,Object> map) throws Exception;

    List<Question> selectQuestionListByName(HttpServletRequest httpServletRequest, Map<String,Object> map) throws Exception;
}