package com.isg.fpgkb.service;

import com.isg.fpgkb.dto.*;
import com.isg.fpgkb.mapper.*;
import com.isg.fpgkb.repository.Neo4jClientRepository;
import com.isg.fpgkb.repository.PhenotypeRepository;
import com.isg.fpgkb.result.Result;
import com.isg.fpgkb.util.Neo4jUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title SearchService
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 14:31
 * @Description TODO
 **/

@Service
public class SearchService {

    private Phenotype phenotype;

    private Gene gene;

    private Sample sample;

    private Diseases diseases;

    private Article article;

    @Autowired
    PhenotypeMapper phenotypeMapper;
    @Autowired
    GeneMapper geneMapper;
    @Autowired
    SampleMapper sampleMapper;
    @Autowired
    DiseasesMapper diseasesMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    Relation_gpMapper relation_gpMapper;

    @Autowired
    private PhenotypeRepository phenotypeRepository;

    @Autowired
    private Neo4jClientRepository neo4jClientRepository;

    @Autowired
    public SearchService(Phenotype phenotype,Gene gene,Sample sample,Diseases diseases,Article article
    ,PhenotypeMapper phenotypeMapper,GeneMapper geneMapper,SampleMapper sampleMapper,DiseasesMapper diseasesMapper,ArticleMapper articleMapper
    ,Relation_gpMapper relation_gpMapper,PhenotypeRepository phenotypeRepository,Neo4jClientRepository neo4jClientRepository){
        this.article = article;
        this.diseases = diseases;
        this.gene =gene;
        this.sample = sample;
        this.phenotype = phenotype;
        this.phenotypeMapper = phenotypeMapper;
        this.geneMapper = geneMapper;
        this.sampleMapper = sampleMapper;
        this.diseasesMapper = diseasesMapper;
        this.articleMapper = articleMapper;
        this.relation_gpMapper = relation_gpMapper;
        this.phenotypeRepository = phenotypeRepository;
        this.neo4jClientRepository = neo4jClientRepository;
    }

    public Object Get_Phenotype(String pid){
        Phenotype p = phenotypeMapper.selectByPrimaryKey(pid);
        return Result.successData(p);
    }

    public Object Get_Gene(Integer gene_id){
        Gene g = geneMapper.selectByGeneid(gene_id);
        return Result.successData(g);
    }

    public Object Get_Sample(String sid){
        Sample s = sampleMapper.selectByPrimaryKey(sid);
        return Result.successData(s);
    }

    public Object Get_Disease(Integer did){
        Diseases d = diseasesMapper.selectByPrimaryKey(did);
        return Result.successData(d);
    }

    public Object Get_Article(Integer pmid){
        Article a = articleMapper.selectByPrimaryKey(pmid);
        return Result.successData(a);
    }

    public Object Get_Tablet(){
        List<Tablet> t = relation_gpMapper.selectTablet();
        List<List_disease_top10> d = relation_gpMapper.selectListDiseaseTop10();

        Map<String, List> result_map = new HashMap<>();
        result_map.put("result1",t);
        result_map.put("result2",d);
        return Result.successData(result_map);
    }

    public Object disease_related_phenotype(Integer did){
        List<Object> p = phenotypeRepository.Disease_related_phenotype(did);
        Map<String, Set> set =  Neo4jUtil2.disease_related_phenotype(p);
        return set;
    }

    public Object findBySample_Phenotype (String sid){
        List<Object> p = phenotypeRepository.findBySample_Phenotype(sid);
        Map<String, Set> set =  Neo4jUtil2.findBySample_Phenotype(p);
        return set;
    }

    public Object test(Integer did){

        System.out.println(neo4jClientRepository.getphenptypeofDisease(did));
//        Map<String, Set> set =  Neo4jUtil2.disease_related_phenotype(p);
        return null;
    }



}
