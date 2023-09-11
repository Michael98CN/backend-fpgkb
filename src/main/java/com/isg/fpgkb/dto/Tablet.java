package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title Tablet
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 15:22
 * @Description TODO
 **/
@Component
@Data
public class Tablet {
    private Integer pmid;

    private Integer geneId;

    private String geneName;

    private String chromosomesName;

    private String pid;

    private String phenotypeName;

    private Integer did;

    private String dname;

    private String sid;

}
