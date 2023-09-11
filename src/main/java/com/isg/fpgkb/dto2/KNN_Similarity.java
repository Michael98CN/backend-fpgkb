package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @Title KNN_Similarity
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2023/4/26 15:06
 * @Description TODO
 **/

@RelationshipProperties
@Data
@Builder
public class KNN_Similarity {

    @RelationshipId
    private Long id;

    private Long score;

    @TargetNode
    private Sample1 sample1;
}
