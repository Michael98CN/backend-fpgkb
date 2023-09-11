package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Sample implements Serializable {
    private String sid;

    private Integer pmid;

    private Integer sampleNumber;

    private String gender;

    private String year;

    private String region;

    private String race;

    private String source;

    private String method;

    private static final long serialVersionUID = 1L;

}
