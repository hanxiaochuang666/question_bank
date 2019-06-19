package cn.eblcu.questionbank.ui.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class TestPaperCreateModel {
    private int testPaperId;
    @NotBlank(message = "试卷名称name不能为空")
    private String name;
    @Min(value = 1,message = "类目1 categoryOne 非法")
    private int categoryOne;
    @Min(value = 1,message = "类目2 categoryTwo 非法")
    private int categoryTwo;
    @Min(value = 1,message = "课程 courseId 非法")
    private int courseId;
    @NotBlank(message = "课程名称courseName不能为空")
    private String courseName;
    @Range(min = 0,max=1,message = "使用类型useType非法")
    private int useType;

    private int time;
    @NotBlank(message = "开始时间startTime不能为空")
    private String startTimeStr;
    @NotBlank(message = "结束时间endTime不能为空")
    private String endTimeStr;
    @Range(min = 0,max=1,message = "是否计分isScore非法")
    private int isScore;
    @Range(min = 0,max=1,message = "组卷类型formType非法")
    private int formType;
    @Min(value = 1,message = "机构Id orgId 非法")
    private int orgId;
}
