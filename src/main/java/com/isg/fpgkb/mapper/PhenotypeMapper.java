package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Phenotype;
import java.util.List;

public interface PhenotypeMapper {
    int deleteByPrimaryKey(String pid);

    int insert(Phenotype record);

    Phenotype selectByPrimaryKey(String pid);

    List<Phenotype> selectAll();

    int updateByPrimaryKey(Phenotype record);
}