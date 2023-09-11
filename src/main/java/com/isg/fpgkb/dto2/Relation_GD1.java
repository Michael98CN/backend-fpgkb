package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Title Relation_GD
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:56
 * @Description TODO
 **/

@RelationshipProperties
@Data
@Builder
public class Relation_GD1 {

    @RelationshipId
    private Long id;

    private Integer pmid;

    private String category;

    private String details;

    @TargetNode
    private Gene1 gene1;
}
