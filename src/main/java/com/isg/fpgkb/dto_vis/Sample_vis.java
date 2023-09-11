package com.isg.fpgkb.dto_vis;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title Sample_vis
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/11 19:23
 * @Description TODO
 **/
@Component
@Data
public class Sample_vis {
    private String region;

    private Integer article_num;

    private Integer sample_all_num;
}
