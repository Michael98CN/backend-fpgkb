package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Title Relation_Have
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2023/4/11 15:40
 * @Description TODO
 **/
@RelationshipProperties
@Data
@Builder
public class Relation_Have {
    @RelationshipId
    private Long id;

    @TargetNode
    private Gene1 gene1;
}
