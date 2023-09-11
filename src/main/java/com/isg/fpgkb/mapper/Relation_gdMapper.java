package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Relation_gd;
import java.util.List;

public interface Relation_gdMapper {
    int deleteByPrimaryKey(Integer gdid);

    int insert(Relation_gd record);

    Relation_gd selectByPrimaryKey(Integer gdid);

    List<Relation_gd> selectAll();

    int updateByPrimaryKey(Relation_gd record);
}