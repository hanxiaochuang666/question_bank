package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KnowledgePoints implements Serializable {
    private static final long serialVersionUID = 3453965713190323686L;
    /**
	 *知识点id
	 */
    private Integer knowledgePointsId;

    /**
	 *一级类目id
	 */
    private Integer categoryOne;

    /**
	 *二级类目
	 */
    private Integer categoryTwo;

    /**
	 *课程id
	 */
    private Integer courseId;

    /**
	 *VARCHAR
	 *课程名称
	 */
    private String courseName;

    /**
	 *机构id
	 */
    private Integer orgId;

    /**
	 *DATE
	 *创建时间
	 */
    private Date createTime;

    /**
	 *DATE
	 *更新时间
	 */
    private Date updateTime;

    /**
	 *创建者id
	 */
    private Integer createUser;

    /**
	 *修改者id
	 */
    private Integer updateUser;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", knowledgePointsId=").append(knowledgePointsId);
        sb.append(", categoryOne=").append(categoryOne);
        sb.append(", categoryTwo=").append(categoryTwo);
        sb.append(", courseId=").append(courseId);
        sb.append(", courseName=").append(courseName);
        sb.append(", orgId=").append(orgId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append("]");
        return sb.toString();
    }
}