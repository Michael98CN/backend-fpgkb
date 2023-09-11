package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * @Title Variation
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2023/4/11 15:37
 * @Description TODO
 **/
@Node("Variation")
@Data
@Builder
public class Variation
{
    @Id
    @GeneratedValue
    private Long id;

    private Long gdid;

    private String category;

    private String details;

    @Relationship(type = "Mention_Var",direction = Relationship.Direction.INCOMING)
    private Set<Relation_Mention_Var> mention_var;

    @Relationship(type = "Have",direction = Relationship.Direction.INCOMING)
    private Set<Relation_Have> have;
}
