package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Title Relation_GP
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:55
 * @Description TODO
 **/
@RelationshipProperties
@Data
@Builder
public class Relation_GP1_Affect {

    @RelationshipId
    private Long id;

    private Integer pmid;

    private String type;

    private String evidence;

    @TargetNode
    private Gene1 gene1;

}
