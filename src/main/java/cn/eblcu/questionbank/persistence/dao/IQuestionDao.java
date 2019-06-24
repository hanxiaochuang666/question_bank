package cn.eblcu.questionbank.persistence.dao;


import cn.eblcu.questionbank.ui.model.QuestionTypeCountModel;

import java.util.List;
import java.util.Map;

public interface IQuestionDao extends IBaseDao {
    List<QuestionTypeCountModel> queryQuestionTypeCount(List<Integer> questionIds);

    List<Map<String,Object>> queryQuestionListCount(Map<String,Object> map);
}