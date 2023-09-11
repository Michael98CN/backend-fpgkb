package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Relation_sp;
import java.util.List;

public interface Relation_spMapper {
    int deleteByPrimaryKey(String spid);

    int insert(Relation_sp record);

    Relation_sp selectByPrimaryKey(String spid);

    List<Relation_sp> selectAll();

    int updateByPrimaryKey(Relation_sp record);
}