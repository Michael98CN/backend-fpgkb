package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Title Relation_PS
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:57
 * @Description TODO
 **/
@RelationshipProperties
@Data
@Builder
public class Relation_AS1_Exist {
    @RelationshipId
    private Long id;

    @TargetNode
    private Sample1 sample1;
}
