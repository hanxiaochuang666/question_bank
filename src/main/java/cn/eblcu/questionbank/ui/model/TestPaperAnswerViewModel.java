package cn.eblcu.questionbank.ui.model;

import lombok.Data;

import java.util.List;

@Data
public class TestPaperAnswerViewModel {
    private Integer testPaperId;
    private Integer optFlag;    //操作选项 0 保存；1 提交
    private List<TestPaperAnswerModel> TestPaperAnswerLst;
}
