package com.isg.fpgkb.service;

import com.isg.fpgkb.dto.Menu;
import com.isg.fpgkb.dto.Menu_Entity;
import com.isg.fpgkb.dto.Phenotype;
import com.isg.fpgkb.dto2.*;
import com.isg.fpgkb.mapper.MenuMapper;
import com.isg.fpgkb.mapper.PhenotypeMapper;
import com.isg.fpgkb.repository.MentionFPRepository;
import com.isg.fpgkb.repository.PhenotypeRepository;
import com.isg.fpgkb.repository.SampleRepository;
import com.isg.fpgkb.result.Result;
import com.isg.fpgkb.util.Neo4jUtil2;
import org.neo4j.driver.internal.value.NodeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title VisService
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 15:58
 * @Description TODO
 **/
@Service
public class VisService {

    private Menu menu;

    @Autowired
    private PhenotypeRepository phenotypeRepository;
    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private MentionFPRepository mentionFPRepository;

    @Autowired
    private Neo4jClient neo4jClient;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    PhenotypeMapper phenotypeMapper;


    @Autowired
    public  VisService(Menu menu,MenuMapper menuMapper,PhenotypeMapper phenotypeMapper){
        this.menu = menu;
        this.menuMapper = menuMapper;
        this.phenotypeMapper = phenotypeMapper;
    }

    public Object Phenotype_Menu(){
        List<Menu_Entity> list = menuMapper.selectAll();


        List<Menu_Entity> result = new ArrayList<Menu_Entity>();

        Map<Object,Object> treeMap = new HashMap();
        Object itemTree;

        for( int i = 0;i<list.size()&&!list.isEmpty();i++){
            treeMap.put(list.get(i).getId(),list.get(i));
            if(list.get(i).getPid().equals("0")){
                result.add(list.get(i));
            }
        }
        for (int i = 0;i<list.size();i++){
            Menu_Entity item = list.get(i);
            Menu_Entity res = (Menu_Entity)treeMap.get(list.get(i).getPid());
            if(res != null){
                if(res.getChildList() == null){
                    // 判断一个集合是否被创建用null：表示结合还没有被分配内存空间(即还没有被创建)，内存大小自然为null
                    // 用集合的size判断集合中是否有元素，为0，没有元素（集合已经被创建），
                    res.setChildList(new ArrayList<Menu_Entity>());
                }
                res.getChildList().add(list.get(i)); // 添加到父节点的ChildList集合下
            }

            }

        return result ;

    }

    public Object get_Neo4j_vis_simple(List<Menu_Entity> a){
        if(a.size() == 1){
            List<Object> q = new ArrayList<>();
            List<Object> p = phenotypeRepository.findPhenotype1ByPid(a.get(0).getId());
            Map<String,List> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList(p,q);
            return nodePhenotypelist;
        }
        else {
            ArrayList list = new ArrayList();
            for (int i = 0;i<a.size();i++){
                list.add(a.get(i).getId());
            }
            List<Object> p = phenotypeRepository.findReByPid(list);

            List<Object> q = phenotypeRepository.findNodeByPid(list);
            Map<String,List> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList_correlation(p,q,list);
            //Map<String,List> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList(p,q);
            return nodePhenotypelist;

        }
    }


//    @Transactional(rollbackFor = Exception.class)
    public Object     get_Neo4j_vis_complex(Map s){
        ArrayList a;
        ArrayList option;
        Map m;

        a = (ArrayList) s.get("list");
        option = (ArrayList) s.get("option");
//        System.out.println(a.get(0));
//       System.out.println(n.get("pid"));
//        System.out.println(n.get("name"));
//        System.out.println(option);
        m = (Map) option.get(0);
        String age = m.get("age") +(String)m.get("select");
        String race = (String) m.get("race");
        String region = (String) m.get("region");
        //System.out.println(age);


           ArrayList list = new ArrayList();
            for (int i = 0;i<a.size();i++){
                Map n2;
                n2 = (Map) a.get(i);
                list.add(n2.get("id"));
            }
//            list.add("HP:0400004");
//            list.add("HP:0003196");
//            list.add("HP:0000574");
//            list.add("HP:0010751");
//            list.add("HP:0000320");
            Integer size = list.size();
            //首先看用户输入的表型，是否已经在已有的样本中出现过

            List<Object> l = phenotypeRepository.check_already_exist(list,size);
            ArrayList list_sample =  new ArrayList();

            if(l.size()!=0){
                l.forEach((node)->{
                    NodeValue nodeValue = (NodeValue) node;
                    list_sample.add(nodeValue.asNode().asMap().get("sid"));
                });
                List<Object> q = new ArrayList<>();
                List<Object> p = phenotypeRepository.findInfoBySamplelist(list_sample);
                Map<String,List> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList(p,q);
                return nodePhenotypelist;

            }else{
                String sid = "user_"+new Random().nextInt(100);
                phenotypeRepository.merge_user_node(sid,list,age,race,region);
                System.out.println("创建完节点和关系了");
                String tip = (String) use_node2vec_KNN();
                System.out.println(tip);
                List<Object> q = new ArrayList<>();
                List<Object>p = phenotypeRepository.get_info_similar_s(sid);
                Map<String,List> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationListandsamplelist(p,q);
                System.out.println(nodePhenotypelist);
                String tip_delete = (String) reset_kg(sid);
                System.out.println(tip_delete);
                return nodePhenotypelist;
            }


//            List<Object> p = phenotypeRepository.findReByPid(list);
//            List<Object> q = phenotypeRepository.findNodeByPid(list);
//            Map<String,List> nodePhenotypelist = Neo4jUtil2.getNodeAndRelationList_correlation(p,q,list);


//

//
//return null;

    }

    public Object get_info_Sample(String sid){
        List<Object> p = phenotypeRepository.findInfoBySample(sid);
        Map<String,List> set =  Neo4jUtil2.get_info_node_about_sample(p);
        return set;
    }


    public Object get_details_Phenotype(String pid){
        Phenotype p = phenotypeMapper.selectByPrimaryKey(pid);
        ArrayList list = new ArrayList();
        list.add(p);
//        System.out.println(list);

        return Result.successData(list);
    }

    //@Transactional(rollbackFor = Exception.class)
    public Object use_node2vec_KNN(){
        String name_node2vec = phenotypeRepository.build_graph_project();
        System.out.println("创建完graph");
        Integer count = phenotypeRepository.use_node2vec();
        System.out.println("计算时间："+count);
        String name_node2vec2 = phenotypeRepository.delete_graph_project();

        String name_knn =phenotypeRepository.build_knn_graph();
        Integer time = phenotypeRepository.use_knn();
        String name_knn2 =phenotypeRepository.delete_graph_project();
        return "successfully create node2vec and use KNN ";
    }

    public Object reset_kg(String sid){
        phenotypeRepository.reset_relation_user(sid);
        phenotypeRepository.reset_sim_knn();
        phenotypeRepository.reset_node2vec();
        phenotypeRepository.reset_user_node(sid);

        return "reset successfully";
    }


}
