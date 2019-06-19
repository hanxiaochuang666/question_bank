package cn.eblcu.questionbank.ui.model;

import lombok.Data;


@Data
public class TestPaperViewModel extends BaseViewModel{
    private Integer categoryOne;
    private Integer categoryTwo;
    private Integer courseId;
    private Integer useType;
    private String name;
}
