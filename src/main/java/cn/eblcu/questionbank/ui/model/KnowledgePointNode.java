package cn.eblcu.questionbank.ui.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class KnowledgePointNode implements Serializable {

    private static final long serialVersionUID = 9083701279240122528L;
    private Integer titleNumber;

    private String name;

    private Integer id;

    private int knowledgePointId;

    private List<KnowledgePointNode> nodes;

    @Override
    public String toString() {
        return "KnowledgePointNode{" +
                "titleNumber=" + titleNumber +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", knowledgePointId=" + knowledgePointId +
                ", nodes=" + (null == nodes? "null":nodes.toString()) +
                '}';
    }
}
