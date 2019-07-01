package cn.eblcu.questionbank.persistence.entity.dto;

public class QuestionType {
    /**
	 *INTEGER
	 *试题类型id
	 */
    private Integer questionTypeId;

    /**
	 *VARCHAR
	 *类型名称
	 */
    private String name;

    /**
	 *TINYINT
	 *是否是客观题（0:是;1:非）
	 */
    private boolean isObjective;

    /**
	 *VARCHAR
	 *编码
	 */
    private String code;

    /**
	 *TINYINT
	 *状态：0：启用；1：禁用
	 */
    private boolean status;

    public Integer getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Integer questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public boolean getIsObjective() {
        return isObjective;
    }

    public void setIsObjective(boolean isObjective) {
        this.isObjective = isObjective;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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