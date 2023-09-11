package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title List_disease_top10
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2023/4/22 12:52
 * @Description TODO
 **/
@Component
@Data
public class List_disease_top10 {
    private Integer did;

    private String dname;
}
