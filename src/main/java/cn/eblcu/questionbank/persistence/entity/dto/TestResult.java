package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TestResult implements Serializable {
    private static final long serialVersionUID = 5934414790814769210L;
    /**
	 *id
	 */
    private Integer testResultId;

    /**
	 *学生id
	 */
    private Integer studentId;

    /**
	 *学生名称
	 */
    private String studentName;

    /**
	 *试卷id
	 */
    private Integer testPaperId;

    /**
	 *作业所处状态0:未开始答题；1:继续答题，2已提交；3批改中；4批改完成；5已过期
	 */
    private Integer status;

    /**
	 *开始答题时间
	 */
    private Date startTime;

    /**
	 *完成答题时间
	 */
    private Date endTime;

    /**
	 *批改时间
	 */
    private Date markingTime;

    /**
	 *客观题得分
	 */
    private Integer objectiveScore;

    /**
	 *主观题得分
	 */
    private Integer subjectiveScore;

    /**
	 *总得分
	 */
    private Integer totalScore;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", testResultId=").append(testResultId);
        sb.append(", studentId=").append(studentId);
        sb.append(", studentName=").append(studentName);
        sb.append(", testPaperId=").append(testPaperId);
        sb.append(", status=").append(status);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", markingTime=").append(markingTime);
        sb.append(", objectiveScore=").append(objectiveScore);
        sb.append(", subjectiveScore=").append(subjectiveScore);
        sb.append(", totalScore=").append(totalScore);
        sb.append("]");
        return sb.toString();
    }
}