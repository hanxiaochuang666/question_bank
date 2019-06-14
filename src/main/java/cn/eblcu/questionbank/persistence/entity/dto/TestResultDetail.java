package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestResultDetail implements Serializable {
    private static final long serialVersionUID = 7404533222911779831L;
    /**
	 *id
	 */
    private Integer testResultDetailId;

    /**
	 *作业id
	 */
    private Integer testResultId;

    /**
	 *试题id
	 */
    private Integer questionId;

    /**
	 *给出的答案
	 */
    private String giveAnswer;

    /**
	 *获得的分数
	 */
    private Integer score;

    /**
	 *评语
	 */
    private String comment;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", testResultDetailId=").append(testResultDetailId);
        sb.append(", testResultId=").append(testResultId);
        sb.append(", questionId=").append(questionId);
        sb.append(", giveAnswer=").append(giveAnswer);
        sb.append(", score=").append(score);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}