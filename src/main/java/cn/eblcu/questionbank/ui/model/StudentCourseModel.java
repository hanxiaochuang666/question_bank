package cn.eblcu.questionbank.ui.model;


import lombok.Data;

import java.util.List;

@Data
public class StudentCourseModel {
    private String courseName;
    List<StudentCourseInfoModel> StudentCourseInfoLst;
}
