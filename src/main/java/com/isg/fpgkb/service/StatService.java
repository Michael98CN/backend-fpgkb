package com.isg.fpgkb.service;

import com.isg.fpgkb.dto.*;
import com.isg.fpgkb.dto_vis.*;
import com.isg.fpgkb.mapper.GeneMapper;
import com.isg.fpgkb.mapper.MenuMapper;
import com.isg.fpgkb.mapper.Relation_gpMapper;
import com.isg.fpgkb.mapper.SampleMapper;
import com.isg.fpgkb.repository.PhenotypeRepository;
import com.isg.fpgkb.util.Neo4jUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Title StatSevice
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/9 8:51
 * @Description TODO
 **/
@Service
public class StatService {
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    GeneMapper geneMapper;

    @Autowired
    SampleMapper sampleMapper;

    @Autowired
    private PhenotypeRepository phenotypeRepository;


    @Autowired
    private Relation_gpMapper relationGpMapper;






    @Autowired
    public  StatService(MenuMapper menuMapper,GeneMapper geneMapper,SampleMapper sampleMapper
    ,PhenotypeRepository phenotypeRepository,Relation_gpMapper relationGpMapper){
        this.menuMapper = menuMapper;
        this.geneMapper = geneMapper;
        this.sampleMapper = sampleMapper;
        this.phenotypeRepository = phenotypeRepository;
        this.relationGpMapper = relationGpMapper;
    }
    public Object Phenotype_vis(){
        List<Phenotype_vis> list = menuMapper.selectAll_vis();



        List<Phenotype_vis> result = new ArrayList<Phenotype_vis>();

        Map<Object,Object> treeMap = new HashMap();
        Object itemTree;

        for( int i = 0;i<list.size()&&!list.isEmpty();i++){
            treeMap.put(list.get(i).getId(),list.get(i));
            if(list.get(i).getPid().equals("1")){
                result.add(list.get(i));
            }
        }
        for (int i = 0;i<list.size();i++){
            Phenotype_vis item = list.get(i);
            Phenotype_vis res = (Phenotype_vis)treeMap.get(list.get(i).getPid());
            if(res != null){
                if(res.getChildren() == null){
                    // 判断一个集合是否被创建用null：表示结合还没有被分配内存空间(即还没有被创建)，内存大小自然为null
                    // 用集合的size判断集合中是否有元素，为0，没有元素（集合已经被创建），
                    res.setChildren(new ArrayList<Menu_Entity>());
                }
                res.getChildren().add(list.get(i)); // 添加到父节点的ChildList集合下
            }

        }

        return result ;

    }

    public Object Gene_vis(){
        List<Gene> list = geneMapper.selectAll_less();
        List<Gene_vis> list2 = geneMapper.Chrom_count();
//        Set<Gene> set = new HashSet<>(list);
//        Set<Gene_vis> set2 = new HashSet<>(list2);

        List<Gene_vis1> result = new ArrayList<>();
        Map<Object,Object> map = new HashMap<>();


            for (int i = 0; i < list2.size(); i++) {
                Gene_vis1 item = new Gene_vis1();
                item.setName(list2.get(i).getChromosomesName());
                item.setCount(list2.get(i).getCount());
                item.setChildren(new ArrayList<Gene_child>());
                for (int j = 0; j < list.size() ; j++) {
                    Gene_child item2 = new Gene_child();
                    item2.setGene_id(list.get(j).getGeneId());
                    item2.setName(list.get(j).getGeneName());
                    item2.setValue(1);
                    item2.setGene_loci(list.get(j).getChromosomalLoci());
                    if(list.get(j).getChromosomesName().equals(list2.get(i).getChromosomesName())){
                        item.getChildren().add(item2);
                    }

                }

                result.add(item);
            }





//        System.out.println("Gene:"+list);
//        System.out.println("Gene_vis"+list2);
        return result;
    }

    public Object Sample_vis(){
        List<Sample_vis> list = sampleMapper.selectAll_vis();

        List<Map<String,Object>> result = new ArrayList<>();
        list.forEach((sample)->{
            Map<String,Object> resultmap = new HashMap<>();
            resultmap.put("name",sample.getRegion());
            resultmap.put("value",sample.getSample_all_num());
            result.add(resultmap);

        });




        return result;
    }

    public Object Gene_Phenotype_re_vis(String type,String content){
        if(type.equals("1")){
            List<Object> q = new ArrayList<>();
            List<Object> p = phenotypeRepository.findReGenePhenotypebychr(content);
            List<Map<String,Object>> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList_min(p,q);
            return nodePhenotypelist;
        } else if (type.equals("2")) {
            List<Object> q = new ArrayList<>();
            List<Object> p = phenotypeRepository.findReGenePhenotypebygene(content);
            List<Map<String,Object>> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList_min(p,q);
            return nodePhenotypelist;

        }else{
            List<Object> q = new ArrayList<>();
            List<Object> p = phenotypeRepository.findReGenePhenotypebyPhenotype(content);
            List<Map<String,Object>> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList_min(p,q);
            return nodePhenotypelist;

        }


//        List<Object> q = new ArrayList<>();
////        List<Object> p = phenotypeRepository.findReGenePhenotype();
//        List<Map<String,Object>> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList_min(p,q);
//
//        return nodePhenotypelist;
    }

    public Object getlistofitem(String type){
        if(type.equals("1")){
            return relationGpMapper.getchr();
        } else if (type.equals("2")) {
            return relationGpMapper.getgene();
        }else{
            return relationGpMapper.getphenotype();
        }

    }







}
