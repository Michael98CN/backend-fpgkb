package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Title Relation_DP
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:56
 * @Description TODO
 **/

@RelationshipProperties
@Data
@Builder
public class Relation_DP1_Has_Phenotype {

    @RelationshipId
    private Long id;

    private Integer pmid;

    @TargetNode
    private Disease1 disease1;
}
