package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Title Relation_AP1
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/10 8:51
 * @Description TODO
 **/
@RelationshipProperties
@Data
@Builder
public class Relation_SP1_Mention_FP {
    @RelationshipId
    private Long id;

    @TargetNode
    private Phenotype1 phenotype1;
}
