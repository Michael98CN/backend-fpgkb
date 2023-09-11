package com.isg.fpgkb.dto_vis;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title Gene_vis
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/11 16:59
 * @Description TODO
 **/
@Component
@Data
public class Gene_vis {

    private String chromosomesName;

    private Integer count;
}
