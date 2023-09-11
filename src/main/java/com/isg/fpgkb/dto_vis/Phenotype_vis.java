package com.isg.fpgkb.dto_vis;

import com.isg.fpgkb.dto.Menu_Entity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title Phenotype_vis
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/9 9:24
 * @Description TODO
 **/
@Component
@Data
public class Phenotype_vis<T> {
    private Integer level;

    private String id;

    private String name;

    private String pid;

    //private String synonyms;

    private List<Menu_Entity> Children;


}
