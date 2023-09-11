package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Menu;
import com.isg.fpgkb.dto.Menu_Entity;
import com.isg.fpgkb.dto_vis.Phenotype_vis;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer level);

    int insert(Menu record);

    Menu selectByPrimaryKey(Integer level);

    List<Menu_Entity> selectAll();

    List<Phenotype_vis> selectAll_vis();

    int updateByPrimaryKey(Menu record);
}