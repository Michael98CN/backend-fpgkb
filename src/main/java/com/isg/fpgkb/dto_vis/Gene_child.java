package com.isg.fpgkb.dto_vis;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title Gene_child
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/12 10:14
 * @Description TODO
 **/
@Component
@Data
public class Gene_child {
    private String name;

    private  Integer value;

    private  Integer gene_id;

    private String gene_loci;
}
