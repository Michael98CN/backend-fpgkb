package com.isg.fpgkb.dto2;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * @Title Gene
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 8:55
 * @Description TODO
 **/
@Node("Genotype")
@Data
@Builder
public class Gene1 {
    @Id
    @GeneratedValue
    private Long id;

    private Integer gid;

    private Integer geneId;

    private String geneName;

    private String fullName;

    private String chromosomesName;

    private String chromosomalLoci;

    private Integer exon_count;

}
