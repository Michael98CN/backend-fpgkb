package com.isg.fpgkb.dto2;

/**
 * @Title Phenotype
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 20:00
 * @Description TODO
 **/


import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node("FacePhenotype")
@Data
@Builder
public class Phenotype1 {

    @Id
    @GeneratedValue
    private Long id;

    private String pid;

    private String phenotypeName;

    private String synonyms;

    private String description;

    @Relationship(type = "Affect",direction = Relationship.Direction.INCOMING)
    private Set<Relation_GP1_Affect> affect;

    @Relationship(type = "Has_Phenotype",direction = Relationship.Direction.INCOMING)
    private Set<Relation_DP1_Has_Phenotype> hasPhenotype;


}
