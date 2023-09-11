package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * @Title Sample
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:55
 * @Description TODO
 **/
@Node("Sample")
@Data
@Builder
public class Sample1 {
    @Id
    @GeneratedValue
    private Long id;

    private String sid;

    private Integer pmid;

    private Integer sampleNumber;

    private String gender;

    private String year;

    private String region;

    private String race;

    private String source;

    private String method;


    @Relationship(type = "Mention_FP",direction = Relationship.Direction.INCOMING)
    private Set<Relation_SP1_Mention_FP> mention;

    @Relationship(type = "SIMILAR_KNN",direction = Relationship.Direction.INCOMING)
    private Set<KNN_Similarity> SIMILAR_KNN;

}
