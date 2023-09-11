package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Relation_dp;
import java.util.List;

public interface Relation_dpMapper {
    int deleteByPrimaryKey(Integer dpid);

    int insert(Relation_dp record);

    Relation_dp selectByPrimaryKey(Integer dpid);

    List<Relation_dp> selectAll();

    int updateByPrimaryKey(Relation_dp record);
}