package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
@Data
public class Relation_sp implements Serializable {
    private String spid;

    private String sid;

    private String pid;

    private static final long serialVersionUID = 1L;

}
