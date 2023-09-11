package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Article implements Serializable {
    private Integer pmid;

    private String articleName;

    private String magazineName;

    private String publishTime;

    private String author;

    private static final long serialVersionUID = 1L;

}