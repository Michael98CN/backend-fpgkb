package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Relation_gp implements Serializable {
    private Integer gpid;

    private Integer gid;

    private String pid;

    private Integer pmid;

    private String type;

    private String evidence;

    private static final long serialVersionUID = 1L;

}