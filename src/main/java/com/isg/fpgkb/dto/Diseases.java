package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class Diseases implements Serializable {
    private Integer did;

    private String dname;

    private String others;

    private static final long serialVersionUID = 1L;
}