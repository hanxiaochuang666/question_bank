package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.IQuestionService;
import cn.eblcu.questionbank.infrastructure.util.DateUtils;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.IQuestionDao;
import cn.eblcu.questionbank.persistence.entity.dto.Question;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.QuestionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("questionService")
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl implements IQuestionService {
    @Autowired
    private IQuestionDao questionDao;

    @Override
    IBaseDao getDao() {
        return this.questionDao;
    }

    @Override
    public void insertQuestion(QuestionModel model,HttpServletRequest httpServletRequest) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        Question question = new Question();
        question.setCategoryOne(model.getCategoryOne());
        question.setCategoryTwo(model.getCategoryTwo());
        question.setCourseId(model.getCourseId());
        question.setDifficultyLevel(model.getDifficultyLevel());
        question.setCreateTime(DateUtils.now());
        question.setKnowledgePoints(model.getKnowledgePoints());
        question.setOrgId(model.getOrgId());
        question.setCreateUser(userId);
        question.setQuestionBody(model.getQuestionBody());
        question.setQuestionAnswer(model.getQuestionAnswer());
        question.setQuestionOpt(model.getQuestionOpt());
        question.setQuestionResolve(model.getQuestionResolve());
        question.setQuestionSound(model.getQuestionSound());
        question.setQuestionType(model.getQuestionType());
        question.setUpdateUser(userId);
        insertSelective(question);
        log.info("主键id【{}】",question.getQuestionId());

    }
}