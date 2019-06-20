package cn.eblcu.questionbank.ui.model;

import lombok.Data;

import javax.validation.constraints.Min;


@Data
public class PaperFormatInfo {
    private Integer testPaperFormatId;
    @Min(value = 1,message = "试题类型 questionType 非法")
    private int questionType;
    @Min(value = 0,message = "试题数量 questionNum 非法")
    private int questionNum;
    @Min(value = 1,message = "试题分数 questionSpec 非法")
    private int questionSpec;
}
