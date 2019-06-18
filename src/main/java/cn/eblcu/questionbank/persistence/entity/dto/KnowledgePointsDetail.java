package cn.eblcu.questionbank.persistence.entity.dto;

import lombok.Data;

@Data
public class KnowledgePointsDetail {
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

    /**
	 *标题助记码
	 */
    private Integer titleNumber;


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
        sb.append(", titleNumber=").append(titleNumber);
        sb.append("]");
        return sb.toString();
    }
}