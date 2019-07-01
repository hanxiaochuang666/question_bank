package cn.eblcu.questionbank.ui.model;


import lombok.Data;

@Data
public class StudentCourseInfoModel {
    private int     testPaperId;
    private String  testPaperName;
    private String  dateTime;
    private int     status;
}
