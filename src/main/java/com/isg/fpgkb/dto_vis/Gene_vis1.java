package com.isg.fpgkb.dto_vis;

import com.isg.fpgkb.dto.Menu_Entity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title Gene_vis1
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/12 10:14
 * @Description TODO
 **/
@Component
@Data
public class Gene_vis1 {
    private String name;

    private Integer count;

    private List<Gene_child> children;
}
