package cn.eblcu.questionbank.ui.model;

import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class TestPaperQuestionResModel {
    private int     questionType;
    private String  questionTypeName;
    private int     score;
    private List<Map<String, Object>> questionLst;
}
