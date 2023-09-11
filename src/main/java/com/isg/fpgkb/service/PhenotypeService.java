package com.isg.fpgkb.service;

import com.isg.fpgkb.dto2.*;
import com.isg.fpgkb.repository.PhenotypeRepository;
import com.isg.fpgkb.util.Neo4jUtil;
import com.isg.fpgkb.util.Neo4jUtil2;
import com.isg.fpgkb.vis.VisNode;
import lombok.extern.slf4j.Slf4j;
//import org.neo4j.driver.types.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

/**
 * @Title PhenotypeService
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/5 9:43
 * @Description TODO
 **/
@Service
@Slf4j
public class PhenotypeService {


    @Autowired
    private PhenotypeRepository phenotypeRepository;



    public Map<String,List> getByPid(String pid){
        List<Object> p = phenotypeRepository.findPhenotype1ByPid(pid);
        List<Object> q = new ArrayList<>();
//        System.out.println(p);
//        System.out.println(p.get(0));
//        System.out.println(p.get(0).getClass());
//        List<List<Integer>> result = new ArrayList<>();
//        for(Map<String,Object> row:iterable){
//            List<Integer> tmp_path=new ArrayList<>();
//            tmp_path.add(entity.getStart());
//            Path.Segment[] path= (Path.Segment[]) row.get("p");
//            for(Path.Segment segment:path){
//                Map<String, Object> map=segment.end().asMap();
//                tmp_path.add(Integer.valueOf(map.get("entityId").toString()));
//            }
//            result.add(tmp_path);
//        }
       // Phenotype1 p = phenotypeRepository.findPhenotype1ByPid(pid);

            Map<String,List> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList(p,q);
//        List<Map<String,Object>> nodeGenelist = Neo4jUtil.getNodeList(p, Gene1.class);
//        List<Map<String,Object>> nodeDiseaselist = Neo4jUtil.getNodeList(p, Disease1.class);
//        List<Map<String,Object>> nodeArticlelist = Neo4jUtil.getNodeList(p, Article1.class);
//        List<Map<String,Object>> nodeSamplelist = Neo4jUtil.getNodeList(p,Sample1.class);
//
//        log.info("nodePhenotypelist",JSONObject.toJSONString(nodePhenotypelist));
//        log.info("nodeGenelist",JSONObject.toJSONString(nodeGenelist));
//        log.info("nodeDiseaselist",JSONObject.toJSONString(nodeDiseaselist));
//        log.info("nodeArticlelist",JSONObject.toJSONString(nodeArticlelist));
//        log.info("nodeSamplelist",JSONObject.toJSONString(nodeSamplelist));

        return nodePhenotypelist;
    }


    public Object findallphenotype(){

        List<Phenotype1> list = phenotypeRepository.findAll();
        List<VisNode> visNodes = new ArrayList<>();
        for(Phenotype1 phenotype1 : list){
            visNodes.add(new VisNode(phenotype1.getPhenotypeName(),"phenotype", phenotype1));
        }


        return visNodes;

    }



}
