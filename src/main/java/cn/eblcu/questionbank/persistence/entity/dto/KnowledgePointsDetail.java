package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgePointsDetail implements Serializable {
    private static final long serialVersionUID = -7512360252572441033L;
    /**
	 *知识点详情id
	 */
    private Integer knowledgePointsDetailId;

    /**
	 *知识点id
	 */
    private Integer knowledgePointsId;

    /**
	 *父知识点详情id,根节点为0
	 */
    private Integer parentId;

    /**
	 *知识点名称
	 */
    private String name;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", knowledgePointsDetailId=").append(knowledgePointsDetailId);
        sb.append(", knowledgePointsId=").append(knowledgePointsId);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}