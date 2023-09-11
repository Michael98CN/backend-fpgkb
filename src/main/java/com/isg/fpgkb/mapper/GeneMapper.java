package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Gene;
import com.isg.fpgkb.dto_vis.Gene_vis;

import java.util.List;

public interface GeneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Gene record);

    Gene selectByPrimaryKey(Integer id);

    List<Gene> selectAll();

    int updateByPrimaryKey(Gene record);

    Gene selectByGeneid(Integer gene_id);

    List<Gene> selectAll_less();

    List<Gene_vis> Chrom_count();


}