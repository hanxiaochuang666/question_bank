package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TestPaper implements Serializable {
    private static final long serialVersionUID = 1766480520622181707L;
    /**
	 *试卷id
	 */
    private Integer testPaperId;

    /**
	 *试卷名称
	 */
    private String name;

    /**
	 *一级类目id
	 */
    private Integer categoryOne;

    /**
	 *二级类目id
	 */
    private Integer categoryTwo;

    /**
	 *课程id
	 */
    private Integer courseId;

    /**
	 *课程名称
	 */
    private String courseName;

    /**
	 *用途:0 测试，1作业
	 */
    private Byte useType;

    /**
	 *时长(单位  分)
	 */
    private Integer time;

    /**
	 *有效期开始时间
	 */
    private Date startTime;

    /**
	 *有效期结束时间
	 */
    private Date endTime;

    /**
	 *是否计分:0计分，1不计分
	 */
    private Byte isScore;

    /**
	 *总分
	 */
    private Integer totalScore;

    /**
	 *组卷类型（0:人工组卷；1:智能组卷）
	 */
    private Integer formType;

    /**
	 *导出文件path
	 */
    private String exportPath;

    /**
	 *导出时间
	 */
    private Date exportTime;

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
        sb.append(", testPaperId=").append(testPaperId);
        sb.append(", name=").append(name);
        sb.append(", categoryOne=").append(categoryOne);
        sb.append(", categoryTwo=").append(categoryTwo);
        sb.append(", courseId=").append(courseId);
        sb.append(", courseName=").append(courseName);
        sb.append(", useType=").append(useType);
        sb.append(", time=").append(time);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", isScore=").append(isScore);
        sb.append(", totalScore=").append(totalScore);
        sb.append(", formType=").append(formType);
        sb.append(", exportPath=").append(exportPath);
        sb.append(", exportTime=").append(exportTime);
        sb.append(", orgId=").append(orgId);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}