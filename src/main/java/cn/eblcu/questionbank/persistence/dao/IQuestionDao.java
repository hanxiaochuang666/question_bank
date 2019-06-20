package cn.eblcu.questionbank.persistence.dao;


import cn.eblcu.questionbank.ui.model.QuestionTypeCountModel;

import java.util.List;

public interface IQuestionDao extends IBaseDao {
    List<QuestionTypeCountModel> queryQuestionTypeCount(List<Integer> questionIds);
}