package cn.eblcu.questionbank.ui.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherCorrectResultModel implements Serializable {

    private static final long serialVersionUID = 7493079214269570181L;

    @ApiModelProperty(value = "试题ID")
    private Integer questionId;

    @ApiModelProperty(value = "评阅分数")
    private Integer score;

    @ApiModelProperty(value = "教师评语")
    private String comment;

}
