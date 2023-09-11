package com.isg.fpgkb.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title Menu_Entity
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 16:12
 * @Description TODO
 **/
@Component
@Data
public class Menu_Entity<T> {
    private Integer level;

    private String id;

    private String pid;

    private String name;

    private String synonyms;

    private List<Menu_Entity> childList;

}
