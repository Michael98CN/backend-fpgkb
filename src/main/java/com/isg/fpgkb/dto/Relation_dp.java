package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Relation_dp implements Serializable {
    private Integer dpid;

    private Integer did;

    private String pid;

    private Integer pmid;

    private static final long serialVersionUID = 1L;

}