package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionType implements Serializable {
    private static final long serialVersionUID = 5342092834562648065L;
    /**
	 *试题类型id
	 */
    private Integer questionTypeId;

    /**
	 *类型名称
	 */
    private String name;

    /**
	 *是否是客观题（0:是;1:非）
	 */
    private Byte isObjective;

    /**
	 *编码
	 */
    private String code;

    /**
	 *状态：0：启用；1：禁用
	 */
    private Byte status;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", questionTypeId=").append(questionTypeId);
        sb.append(", name=").append(name);
        sb.append(", isObjective=").append(isObjective);
        sb.append(", code=").append(code);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}