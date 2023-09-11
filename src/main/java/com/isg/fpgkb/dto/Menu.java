package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Menu implements Serializable {
    private Integer level;

    private String id;

    private String pid;

    private String name;

    private String synonyms;

    private static final long serialVersionUID = 1L;

}