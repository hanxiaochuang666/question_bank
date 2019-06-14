package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestPaperFormat implements Serializable {
    private static final long serialVersionUID = 2669070322964440478L;
    /**
	 *id
	 */
    private Integer testPaperFormatId;

    /**
	 *试卷id
	 */
    private Integer testPaperId;

    /**
	 *试题数量
	 */
    private Integer questionNum;

    /**
	 *试题类型id
	 */
    private Integer questionType;

    /**
	 *试题分
	 */
    private Integer questionSpec;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", testPaperFormatId=").append(testPaperFormatId);
        sb.append(", testPaperId=").append(testPaperId);
        sb.append(", questionNum=").append(questionNum);
        sb.append(", questionType=").append(questionType);
        sb.append(", questionSpec=").append(questionSpec);
        sb.append("]");
        return sb.toString();
    }
}