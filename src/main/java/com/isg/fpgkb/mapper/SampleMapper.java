package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Sample;
import com.isg.fpgkb.dto_vis.Sample_vis;

import java.util.List;

public interface SampleMapper {
    int deleteByPrimaryKey(String sid);

    int insert(Sample record);

    Sample selectByPrimaryKey(String sid);

    List<Sample> selectAll();

    int updateByPrimaryKey(Sample record);

    List<Sample_vis>selectAll_vis();


}
