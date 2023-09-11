package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Relation_gd implements Serializable {
    private Integer gdid;

    private Integer gid;

    private Integer did;

    private Integer pmid;

    private String category;

    private String details;

    private String sid;

    private static final long serialVersionUID = 1L;
}
