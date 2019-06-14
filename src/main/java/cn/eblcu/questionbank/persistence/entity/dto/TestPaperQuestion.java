package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestPaperQuestion implements Serializable {
    private static final long serialVersionUID = -6823622624874214434L;
    /**
	 *id
	 */
    private Integer testPaperQuestionId;

    /**
	 *试卷id
	 */
    private Integer testPagerId;

    /**
	 *试题id
	 */
    private Integer questionId;

    /**
	 *排序
	 */
    private Integer sort;

    /**
	 *解析
	 */
    private String resolve;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", testPaperQuestionId=").append(testPaperQuestionId);
        sb.append(", testPagerId=").append(testPagerId);
        sb.append(", questionId=").append(questionId);
        sb.append(", sort=").append(sort);
        sb.append(", resolve=").append(resolve);
        sb.append("]");
        return sb.toString();
    }
}