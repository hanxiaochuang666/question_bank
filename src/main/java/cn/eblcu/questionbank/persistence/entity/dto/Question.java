package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Question {
    /**
	 *试题id
	 */
    private Integer questionId;

    /**
	 *类目一级id
	 */
    private Integer categoryOne;

    /**
	 *类目二级id
	 */
    private Integer categoryTwo;

    /**
	 *课程id
	 */
    private Integer courseId;

    /**
	 *知识点id,多个使用;分割
	 */
    private String knowledgePoints;

    /**
	 *难度等级(0：无;1:易；2：中；3：难)
	 */
    private Integer difficultyLevel;

    /**
	 *试题类型id
	 */
    private Integer questionType;

    /**
	 *题干
	 */
    private String questionBody;

    /**
	 *音频fileId
	 */
    private String questionSound;

    /**
	 *选项(复用与综合体，表示子题id，使用;分割)
	 */
    private String questionOpt;

    /**
	 *答案
	 */
    private String questionAnswer;

    /**
	 *解析
	 */
    private String questionResolve;

    /**
	 *机构id
	 */
    private Integer orgId;

    /**
	 *创建者id
	 */
    private Integer createUser;

    /**
	 *创建时间
	 */
    private Date createTime;

    /**
	 *更新者id
	 */
    private Integer updateUser;

    /**
	 *更新时间
	 */
    private Date updateTime;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", questionId=").append(questionId);
        sb.append(", categoryOne=").append(categoryOne);
        sb.append(", categoryTwo=").append(categoryTwo);
        sb.append(", courseId=").append(courseId);
        sb.append(", knowledgePoints=").append(knowledgePoints);
        sb.append(", difficultyLevel=").append(difficultyLevel);
        sb.append(", questionType=").append(questionType);
        sb.append(", questionBody=").append(questionBody);
        sb.append(", questionSound=").append(questionSound);
        sb.append(", questionOpt=").append(questionOpt);
        sb.append(", questionAnswer=").append(questionAnswer);
        sb.append(", questionResolve=").append(questionResolve);
        sb.append(", orgId=").append(orgId);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}