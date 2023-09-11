package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Relation_pa implements Serializable {
    private Integer paid;

    private String pid;

    private Integer pmid;

    private static final long serialVersionUID = 1L;

}