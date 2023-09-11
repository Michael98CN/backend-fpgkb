package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Gene implements Serializable {
    private Integer gid;

    private Integer geneId;

    private String geneName;

    private String fullName;

    private String chromosomesName;

    private String chromosomalLoci;

    private Integer exonCount;

    private static final long serialVersionUID = 1L;

}
