package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Diseases;
import java.util.List;

public interface DiseasesMapper {
    int deleteByPrimaryKey(Integer did);

    int insert(Diseases record);

    Diseases selectByPrimaryKey(Integer did);

    List<Diseases> selectAll();

    int updateByPrimaryKey(Diseases record);
}