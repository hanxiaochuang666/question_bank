package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.QuestionModel;

import javax.servlet.http.HttpServletRequest;

public interface IQuestionService extends IBaseService {

    void insertQuestion(QuestionModel model, HttpServletRequest httpServletRequest) throws Exception;
}