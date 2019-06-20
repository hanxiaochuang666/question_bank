package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.IQuestionTypeService;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.IQuestionTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionTypeService")
public class QuestionTypeServiceImpl extends BaseServiceImpl implements IQuestionTypeService {
    @Autowired
    private IQuestionTypeDao questionTypeDao;

    @Override
    IBaseDao getDao() {
        return this.questionTypeDao;
    }
}