package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Relation_pa;
import java.util.List;

public interface Relation_paMapper {
    int deleteByPrimaryKey(Integer paid);

    int insert(Relation_pa record);

    Relation_pa selectByPrimaryKey(Integer paid);

    List<Relation_pa> selectAll();

    int updateByPrimaryKey(Relation_pa record);
}