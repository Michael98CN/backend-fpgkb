package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.List_disease_top10;
import com.isg.fpgkb.dto.Relation_gp;
import com.isg.fpgkb.dto.Tablet;

import java.util.List;

public interface Relation_gpMapper {
    int deleteByPrimaryKey(Integer gpid);

    int insert(Relation_gp record);

    Relation_gp selectByPrimaryKey(Integer gpid);

    List<Relation_gp> selectAll();

    int updateByPrimaryKey(Relation_gp record);

    List <Tablet> selectTablet();

    List<List_disease_top10> selectListDiseaseTop10();

    List<String> getgene();

    List<String> getchr();

    List<String> getphenotype();
}
