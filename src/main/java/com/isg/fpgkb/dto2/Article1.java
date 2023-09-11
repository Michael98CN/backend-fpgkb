package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * @Title Article
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:55
 * @Description TODO
 **/
@Node("Article")
@Data
@Builder
public class Article1 {

    @Id
    @GeneratedValue
    private Long id;

    private Integer pmid;

    private String articleName;

    private String magazineName;

    private String publishTime;

    private String author;




    @Relationship(type = "Exist",direction = Relationship.Direction.INCOMING)
    private Set<Relation_AS1_Exist> contain;
}
