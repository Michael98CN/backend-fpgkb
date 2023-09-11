package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * @Title Disease
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:55
 * @Description TODO
 **/
@Node("Disease")
@Data
@Builder
public class Disease1 {
    @Id
    @GeneratedValue
    private Long id;

    private Integer did;

    private String dname;

    private String others;

    @Relationship(type = "Cause",direction = Relationship.Direction.INCOMING)
    private Set<Relation_Cause> Disease_Causing;
}
