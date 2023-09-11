package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Phenotype implements Serializable {
    private String pid;

    private String phenotypeName;

    private String synonyms;

    private String description;

    private static final long serialVersionUID = 1L;

}