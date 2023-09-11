package com.isg.fpgkb.util;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.internal.value.NodeValue;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Title Neo4jUtil
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/11 20:26
 * @Description TODO
 **/
@SuppressWarnings("DuplicatedCode")
@Slf4j
public class Neo4jUtil2 {

    //构建节点的入度和出度
    public static <T> Map<String,List> getNodeAndRelationList(List<Object> pathValueList, List<Object> NodeValueList) {
//        List<Map<String, Object>> mapList = new ArrayList<>();
//        Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
//        Map<String, List<String>> idListMap = new HashMap<>();
//        List<String> nameFieldList = new ArrayList<>();
//        for (Field declaredField : tClass.getDeclaredFields()) {
//            nameFieldList.add(declaredField.getName());
//        }
//        log.info("nameField",nameFieldList);
        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> result_re = new ArrayList<>();
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
//            Node Start_Node = segments.start();
//            Node End_Node = segments.end();

            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                //用于存储节点对应信息
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("id",node.id());
                String  label = node.labels().iterator().next();
                if (label.equals("Disease")){
                    String id = String.valueOf(node.asMap().get("dname"));
                    resultmap.put("showname",id);
                }
                if (label.equals("FacePhenotype")){
                    String id = String.valueOf(node.asMap().get("phenotypeName"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Genotype")){
                    String id = String.valueOf(node.asMap().get("geneName"));
                    if(id.equals("null")){
                        String id2 = String.valueOf(node.asMap().get("chromosomesName"));
                        resultmap.put("showname",id2);
                    }else {
                        resultmap.put("showname",id);
                    }

                }
                if (label.equals("Article")){
                    String id = String.valueOf(node.asMap().get("pmid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Sample")){
                    String id = String.valueOf(node.asMap().get("sid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Variation")){
                    String id = "Var_"+node.asMap().get("gdid");
                    resultmap.put("showname",id);
                }


                Map<String,Object> properties = node.asMap();

                resultmap.put("label",label);
                resultmap.put("properties",properties);
                result.add(resultmap);
            });

            Iterable<Relationship> relationships = segments.relationships();
            relationships.forEach((relationship) -> {
                Map<String,Object> resultmap_re = new HashMap<>();
                resultmap_re.put("source",relationship.startNodeId());
                resultmap_re.put("target",relationship.endNodeId());
                resultmap_re.put("type",relationship.type());
                resultmap_re.put("properties",relationship.asMap());
                if(relationship.asMap().containsKey("evidence")){
                    resultmap_re.put("direct",1);
                }else{
                    resultmap_re.put("direct",0);
                }
                resultmap_re.put("id",relationship.id());
                resultmap_re.put("lineNumber",null);
                result_re.add(resultmap_re);
//                System.out.println(resultmap_re);
            });
        });

        NodeValueList.forEach((node) -> {
            Map<String,Object> resultmap = new HashMap<>();
            NodeValue nodeValue = (NodeValue) node;

            resultmap.put("id",nodeValue.asNode().id());
            String  label = nodeValue.asNode().labels().iterator().next();
            String id = String.valueOf(nodeValue.asNode().asMap().get("phenotypeName"));
            resultmap.put("showname",id);
            Map<String,Object> properties = nodeValue.asNode().asMap();

            resultmap.put("label",label);
            resultmap.put("properties",properties);
            result.add(resultmap);

        });



        HashSet<Map<String,Object>> set = new HashSet<>(result);
        result.clear();
        result.addAll(set);
        HashSet<Map<String,Object>> set_re = new HashSet<>(result_re);
        result_re.clear();
        result_re.addAll(set_re);

//        List list_for_similar_sample = new ArrayList<>();
////        for(Map<String,Object> set_relation:set_re){
////            if(){
////
////            }
////            set_relation.get("type").equals("SIMILAR_KNN")
////        }
//        for(Map<String,Object> set_node:set){
//            Map<String,Object> map = (Map<String, Object>) set_node.get("properties");
//            String sid = (String) map.get("sid");
//            if(set_node.get("label").equals("Sample")&&sid.startsWith("s")){
//                list_for_similar_sample.add(set_node.get("showname"));
//            }
//            if(list_for_similar_sample.size()==14){
//                break;
//            }
//        }


        List<Map<String,Object>> list_for_similar_sample = new ArrayList<>();
//        Long user_id = null;
        for(Map<String,Object> set_node:set){

            Map<String,Object> map = (Map<String, Object>) set_node.get("properties");
            String sid = (String) map.get("sid");
//            if(set_node.get("label").equals("Sample")&&sid.startsWith("u")){
//                user_id = (Long) set_node.get("id");
//            }
            if(set_node.get("label").equals("Sample")&&sid.startsWith("s")){

                Map<String,Object> sample_node = new HashMap<>();
                sample_node.put("name",set_node.get("showname"));
                sample_node.put("similar",null);
                sample_node.put("id",set_node.get("id"));
                list_for_similar_sample.add(sample_node);
            }
            if(list_for_similar_sample.size()==14){
                break;
            }
        }
//        for (Map<String,Object> set_relation:set_re){
//            if(set_relation.get("source").equals(user_id)){
//                Map<String,Object> map1 = (Map<String, Object>) set_relation.get("properties");
//                Double similar = (Double) map1.get("score");
//                for(Map<String, Object> list:list_for_similar_sample){
//                    if(list.get("id").equals(set_relation.get("target"))){
//                        list.put("similar",similar);
//                    }
//                }
//            }
//        }





//        System.out.println(result);
//        System.out.println(result_re);
        List<Map<String,Object>> all_result = new ArrayList<>();
        Map<String,Object> all_result_map = new HashMap<>();
        all_result_map.put("nodes",result);
        all_result_map.put("links",result_re);
        all_result.add(all_result_map);


        Map<String,List> result_map = new HashMap<>();

        result_map.put("result1",all_result);
        result_map.put("result2",list_for_similar_sample);

        return result_map ;
    }


    public static  <T> Map<String, List> getNodeAndRelationList_correlation(List<Object> pathValueList,List<Object> NodeValueList,List list) {
//        List<Map<String, Object>> mapList = new ArrayList<>();
//        Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
//        Map<String, List<String>> idListMap = new HashMap<>();
//        List<String> nameFieldList = new ArrayList<>();
//        for (Field declaredField : tClass.getDeclaredFields()) {
//            nameFieldList.add(declaredField.getName());
//        }
//        log.info("nameField",nameFieldList);
        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> result_re = new ArrayList<>();
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
//            Node Start_Node = segments.start();
//            Node End_Node = segments.end();

            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                //用于存储节点对应信息
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("id",node.id());
                String  label = node.labels().iterator().next();
                if (label.equals("Disease")){
                    String id = String.valueOf(node.asMap().get("dname"));
                    resultmap.put("showname",id);
                }
                if (label.equals("FacePhenotype")){
                    String id = String.valueOf(node.asMap().get("phenotypeName"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Genotype")){
                    String id = String.valueOf(node.asMap().get("geneName"));
                    if(id.equals("null")){
                        String id2 = String.valueOf(node.asMap().get("chromosomesName"));
                        resultmap.put("showname",id2);
                    }else {
                        resultmap.put("showname",id);
                    }

                }
                if (label.equals("Article")){
                    String id = String.valueOf(node.asMap().get("pmid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Sample")){
                    String id = String.valueOf(node.asMap().get("sid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Variation")){
                    String id = "Var_"+node.asMap().get("gdid");
                    resultmap.put("showname",id);
                }


                Map<String,Object> properties = node.asMap();

                resultmap.put("label",label);
                resultmap.put("properties",properties);
                resultmap.put("count",0);
//                resultmap.put("indegree",0);
//                resultmap.put("outdegree",0);
                resultmap.put("LEVEL",0);
                result.add(resultmap);
            });

            Iterable<Relationship> relationships = segments.relationships();
            relationships.forEach((relationship) -> {
                Map<String,Object> resultmap_re = new HashMap<>();
                resultmap_re.put("source",relationship.startNodeId());
                resultmap_re.put("target",relationship.endNodeId());
                resultmap_re.put("type",relationship.type());
                resultmap_re.put("properties",relationship.asMap());
                resultmap_re.put("id",relationship.id());
                resultmap_re.put("lineNumber",null);
                result_re.add(resultmap_re);
//                System.out.println(resultmap_re);
            });
        });

        NodeValueList.forEach((node) -> {
            Map<String,Object> resultmap = new HashMap<>();
            NodeValue nodeValue = (NodeValue) node;

            resultmap.put("id",nodeValue.asNode().id());
            String  label = nodeValue.asNode().labels().iterator().next();
            String id = String.valueOf(nodeValue.asNode().asMap().get("phenotypeName"));
            resultmap.put("showname",id);
            Map<String,Object> properties = nodeValue.asNode().asMap();

            resultmap.put("label",label);
            resultmap.put("properties",properties);
            resultmap.put("count",0);
//            resultmap.put("indegree",0);
//            resultmap.put("outdegree",0);
            resultmap.put("LEVEL",0);
            result.add(resultmap);

        });





        Map<Long, Integer> map = new HashMap<>();

        result.forEach(x-> {
            Integer count = map.get((Long) x.get("id"));
                    map.put((Long) x.get("id"),count == null ? 1 : ++count);
        });
        System.out.println(map);
        //去重
        HashSet<Map<String,Object>> set = new HashSet<>(result);
        HashSet<Map<String,Object>> set_re = new HashSet<>(result_re);

        //计算出入度
//        for(Map<String,Object> node:set){
//            //构建一个新的map,来存储各节点的出入度
//            Long id = (Long) node.get("id");
//            for(Map<String,Object> re:set_re){
//                Long source = (Long) re.get("source");
//                Long target = (Long) re.get("target");
//                if(Objects.equals(id, source)){
//                    Integer outdegree = (Integer)node.get("outdegree");
//                    node.put("outdegree",outdegree+1);
//                }
//                if(Objects.equals(id, target)){
//                    Integer indegree = (Integer)node.get("indegree");
//                    node.put("indegree",indegree+1);
//                }
//
//            }
//
//        }



 /**************************************************************************************************************************************/
 /*
 * 根据节点的连接情况，给节点分级
 * */
//        //去除Affect、HasPhenotype中相同的关系
//        HashSet<Map<String,Object>> set_re_minize = new HashSet<>();
//        for(Map<String,Object> re:set_re){
//            re.remove("properties");
//            re.remove("id");
//            set_re_minize.add(re);
//        }
//        //将节点分级，以确定共同关系
//        /*
//        * 通过关系Mention_FP、Affect、Has_phenotype的连接数量来确定
//        * */
//        //计算特定关系下节点的连接数量
//        Set set1 = new HashSet<Map<Integer,Integer>>();
//        Map<Long,Long> map1 = new HashMap<>();
//        for(Map<String,Object> node:set){
//            //构建一个新的map,来存储各节点的出入度
//            Long id = (Long) node.get("id");
//            for(Map<String,Object> re:set_re_minize){
//                Long source = (Long) re.get("source");
//                Long target = (Long) re.get("target");
//                if(Objects.equals(re.get("type"),"Mention_FP")){
//                    if(Objects.equals(id, target)) {
//                        Integer count = (Integer) node.get("count");
//                            node.put("count", count + 1);
//
//                    }
//                }
//                if(Objects.equals(re.get("type"),"Affect") || Objects.equals(re.get("type"),"Has_Phenotype")){
//                    if(Objects.equals(id, source)) {
//                            Integer count = (Integer) node.get("count");
//                            node.put("count", count + 1);
//
//                    }
//                }
//            }
//
//        }
//        //
//        Set list_for_level_1 = new HashSet<Object>();
//        Set list_for_level_2 = new HashSet<Object>();
//        for(Map<String,Object> node:set){
//            Integer count = (Integer) node.get("count");
//            Long id = (Long)node.get("id");
//            if(count>=list.size()){
//                node.put("LEVEL",1);
//                System.out.println("节点："+node.get("showname"));
//                for(Map<String,Object> re:set_re){
//                    if(((Objects.equals(re.get("type"),"Exist")||Objects.equals(re.get("type"),"Mention_Var")) && Objects.equals(re.get("source"),id))){
//                        list_for_level_1.add((Long) re.get("target"));
//                    }
//                    if(((Objects.equals(re.get("type"),"Have")||Objects.equals(re.get("type"),"Cause")||Objects.equals(re.get("type"),"Mention_Var"))&&(Objects.equals(re.get("target"),id)||Objects.equals(re.get("source"),id)))){
//                        list_for_level_1.add((Long) re.get("source"));
//                        list_for_level_1.add((Long) re.get("target"));
//                    }
//
//                }
//            }
//            else if(count<=list.size()-1&&count>1){
//                node.put("LEVEL",2);
//                for(Map<String,Object> re:set_re){
//                    if(!list_for_level_1.contains(id)){
//                        if(((Objects.equals(re.get("type"),"Exist")||Objects.equals(re.get("type"),"Mention_Var")) && Objects.equals(re.get("source"),id))){
//                            list_for_level_2.add((Long) re.get("target"));
//                        }
//                        if(((Objects.equals(re.get("type"),"Have")||Objects.equals(re.get("type"),"Cause")||Objects.equals(re.get("type"),"Mention_Var"))&&(Objects.equals(re.get("target"),id)||Objects.equals(re.get("source"),id)))){
//                            list_for_level_2.add((Long) re.get("source"));
//                            list_for_level_2.add((Long) re.get("target"));
//                        }
//                    }
//
//
//                }
//            }else{
//                node.put("LEVEL",3);
//            }
//        }
//        for(Map<String,Object> node:set){
//            Long id = (Long)node.get("id");
//            if(list_for_level_1.contains(id)){
//                node.put("LEVEL",1);
//            }
//            if(list_for_level_2.contains(id)){
//                node.put("LEVEL",2);
//            }
//
//        }
/**************************************************************************************************************************************/

        //根据出入度筛选出共同关系较大的节点
//        for(Map<String,Object> node:set){
//            if((Integer)node.get("indegree")<size||(Integer)node.get("outdegree")<size){
//                Long id = (Long) node.get("id");
//                set_re.removeIf(re -> Objects.equals(id, re.get("source")) || Objects.equals(id, re.get("target")));
//                set.remove(node);
//            }
//
//        }
//        Iterator<Map<String, Object>> iterator = set.iterator();
//        while (iterator.hasNext()) {
//            Map<String, Object> node = iterator.next();
//            if (node.containsKey("indegree") && node.containsKey("outdegree")) {
//                Integer indegree = (Integer) node.get("indegree");
//                Integer outdegree = (Integer) node.get("outdegree");
//                if (indegree >= list.size() || outdegree >= list.size()){}
//                else{
//                    Map<String, Object> map = (Map<String, Object>) node.get("properties");
//                    if(Objects.equals(node.get("label"), "FacePhenotype") /*&& list.contains(map.get("pid"))*/) {}
//                    else{
//                        Long id = (Long) node.get("id");
//                        set_re.removeIf(re -> Objects.equals(id, re.get("source")) || Objects.equals(id, re.get("target")));
//                        iterator.remove(); // 使用迭代器的 remove() 方法删除元素
//                    }
//
//                }
//            }
//        }


        result.clear();
        result.addAll(set);

        result_re.clear();
        result_re.addAll(set_re);

        //筛选共同关系

//        List list_for_similar_sample = new ArrayList();
//        for(Map<String,Object> set_node:set) {
//            Map<String,Object> map2 = (Map<String, Object>) set_node.get("properties");
//            String sid = (String) map2.get("sid");
//            if (set_node.get("label").equals("Sample")&& sid.startsWith("s") && (set_node.get("LEVEL").equals(1) || set_node.get("LEVEL").equals(2))) {
//                list_for_similar_sample.add(set_node.get("showname"));
//            }
//            if (list_for_similar_sample.size() == 14) {
//                break;
//            }
//        }
        List<Map<String,Object>> list_for_similar_sample = new ArrayList<>();
//        Long user_id = null;
        for(Map<String,Object> set_node:set){

            Map<String,Object> map3 = (Map<String, Object>) set_node.get("properties");
            String sid = (String) map3.get("sid");
//            if(set_node.get("label").equals("Sample")&&sid.startsWith("u")){
//                user_id = (Long) set_node.get("id");
//            }
            if(set_node.get("label").equals("Sample")&&sid.startsWith("s")){

                Map<String,Object> sample_node = new HashMap<>();
                sample_node.put("name",set_node.get("showname"));
                sample_node.put("similar",null);
                sample_node.put("id",set_node.get("id"));
                list_for_similar_sample.add(sample_node);
            }
            if(list_for_similar_sample.size()==14){
                break;
            }
        }



            List<Map<String,Object>> all_result = new ArrayList<>();
        Map<String,Object> all_result_map = new HashMap<>();
        all_result_map.put("nodes",result);
        all_result_map.put("links",result_re);
        all_result.add(all_result_map);
        System.out.println(all_result);

        Map<String,List> result_map = new HashMap<>();

        result_map.put("result1",all_result);
        result_map.put("result2",list_for_similar_sample);

        return result_map ;
    }


    /**
     * 解析 PathValue 转关系
     */
    public static <T> List<Map<String, Object>> getNodeAndRelationList_min(List<Object> pathValueList,List<Object> NodeValueList) {
        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> result_re = new ArrayList<>();
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
//            Node Start_Node = segments.start();
//            Node End_Node = segments.end();

            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                //用于存储节点对应信息
                Map<String,Object> resultmap = new HashMap<>();
//                resultmap.put("id",node.id());
                String  label = node.labels().iterator().next();

                if (label.equals("FacePhenotype")){
                    String id = String.valueOf(node.asMap().get("phenotypeName"));
                    resultmap.put("name",id);
                    resultmap.put("category","3");
                }
                if (label.equals("Genotype")){
                    String id = String.valueOf(node.asMap().get("geneName"));
                    String chr_name = String.valueOf(node.asMap().get("chromosomesName"));
                    if(id.equals("null")){
                        String id2 = String.valueOf(node.asMap().get("chromosomesName"));
                        String id3 = String.valueOf(node.asMap().get("chromosomalLoci"));
                        resultmap.put("name",id2+" "+id3+"(chromosomal variation)");
                        resultmap.put("category","2");
                    }else {
                        resultmap.put("name",id);
                        resultmap.put("category","2");
                    }


                }

//                Map<String,Object> properties = node.asMap();

//                resultmap.put("label",label);
//                resultmap.put("properties",properties);
                result.add(resultmap);
            });


            nodes.forEach((node) -> {
                //用于存储节点对应信息
                Map<String,Object> resultmap = new HashMap<>();
//                resultmap.put("id",node.id());
                String  label = node.labels().iterator().next();

                if (label.equals("Genotype")){
                    String chr_name = String.valueOf(node.asMap().get("chromosomesName"));
                        resultmap.put("name",chr_name);
                    resultmap.put("category","1");

                }

                result.add(resultmap);
            });


            Path.Segment segment = segments.iterator().next();
            Map<String,Object> resultmap_re = new HashMap<>();
            String label = segment.start().labels().iterator().next();

            if (label.equals("Genotype")){
                String id = String.valueOf(segment.start().asMap().get("geneName"));
                if(id.equals("null")){
                    String id2 = String.valueOf(segment.start().asMap().get("chromosomesName"));
                    String id3 = String.valueOf(segment.start().asMap().get("chromosomalLoci"));
                    resultmap_re.put("target",id2+" "+id3+"(chromosomal variation)");
                }else {
                    resultmap_re.put("target",id);
                }
                resultmap_re.put("source",segment.start().asMap().get("chromosomesName"));
                resultmap_re.put("value",1);

                result_re.add(resultmap_re);

            }


//            if (label.equals("Genotype")){
//                String id2 = String.valueOf(segment.start().asMap().get("chromosomesName"));
//                resultmap_re.put("source",id2);
//                resultmap_re.put("target",segment.end().asMap().get("phenotypeName"));
//                resultmap_re.put("value",1);
//                result_re.add(resultmap_re);
//
//            }

//            resultmap_re.put("type",segment.relationship().type());
////                resultmap_re.put("properties",relationship.asMap());
//            resultmap_re.put("id",segment.relationship().id());

        });


        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();


            Path.Segment segment = segments.iterator().next();
            Map<String,Object> resultmap_re = new HashMap<>();
            String label = segment.start().labels().iterator().next();

            if (label.equals("Genotype")){

                String id2 = String.valueOf(segment.start().asMap().get("geneName"));
                if(id2.equals("null")){
                    String id3 = String.valueOf(segment.start().asMap().get("chromosomesName"));
                    String id4 = String.valueOf(segment.start().asMap().get("chromosomalLoci"));
                    resultmap_re.put("source",id3+" "+id4+"(chromosomal variation)");
                }else {
                    resultmap_re.put("source",id2);
                }
                resultmap_re.put("target",segment.end().asMap().get("phenotypeName"));
                if(segment.relationship().asMap().containsKey("evidence")){
                    resultmap_re.put("value",3);
                }else{
                    resultmap_re.put("value",1);
                }

                result_re.add(resultmap_re);

            }

        });

        HashSet<Map<String,Object>> set = new HashSet<>(result);
        result.clear();
        result.addAll(set);
        HashSet<Map<String,Object>> set_re = new HashSet<>(result_re);
        result_re.clear();
        result_re.addAll(set_re);

        List<Map<String,Object>> all_result = new ArrayList<>();
        Map<String,Object> all_result_map = new HashMap<>();
        all_result_map.put("nodes",result);
        all_result_map.put("links",result_re);
        all_result.add(all_result_map);
//        System.out.println(all_result);

        return all_result ;

    }


    public static Map<String,List> get_info_node_about_sample(List<Object> pathValueList){
        List<Map<String,Object>> result = new ArrayList<>();
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
//            Node Start_Node = segments.start();
//            Node End_Node = segments.end();

            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                //用于存储节点对应信息
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("id",node.id());
                String  label = node.labels().iterator().next();
                if (label.equals("Disease")){
                    String id = String.valueOf(node.asMap().get("dname"));
                    resultmap.put("showname",id);
                }
                if (label.equals("FacePhenotype")){
                    String id = String.valueOf(node.asMap().get("phenotypeName"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Genotype")){
                    String id = String.valueOf(node.asMap().get("geneName"));
                    if(id.equals("null")){
                        String id2 = String.valueOf(node.asMap().get("chromosomesName"));
                        resultmap.put("showname",id2);
                    }else {
                        resultmap.put("showname",id);
                    }

                }
                if (label.equals("Article")){
                    String id = String.valueOf(node.asMap().get("pmid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Sample")){
                    String id = String.valueOf(node.asMap().get("sid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Variation")){
                    String id = "Var_"+node.asMap().get("gdid");
                    resultmap.put("showname",id);
                }


                Map<String,Object> properties = node.asMap();

                resultmap.put("label",label);
                resultmap.put("properties",properties);
                result.add(resultmap);
            });

        });

        HashSet<Map<String,Object>> set = new HashSet<>(result);
        result.clear();
        result.addAll(set);

        Map<String,List> map = new HashMap<>();
        List<Map<String,Object>> list_phenotype = new ArrayList<>();
        List<Map<String,Object>> list_genotype = new ArrayList<>();
        List<Map<String,Object>> list_disease = new ArrayList<>();
        List<Map<String,Object>> list_article = new ArrayList<>();
        List<Map<String,Object>> list_sample = new ArrayList<>();
        List<Map<String,Object>> list_variation = new ArrayList<>();
        for(Map<String,Object> set_node:set){
            if(set_node.get("label").equals("FacePhenotype")){
                list_phenotype.add(set_node);
            }
            if(set_node.get("label").equals("Genotype")){
                list_genotype.add(set_node);
            }
            if(set_node.get("label").equals("Disease")){
                list_disease.add(set_node);
            }
            if(set_node.get("label").equals("Article")){
                list_article.add(set_node);
            }
            if(set_node.get("label").equals("Sample")){
                list_sample.add(set_node);
            }
            if(set_node.get("label").equals("Variation")){
                list_variation.add(set_node);
            }

        }
        map.put("FacePhenotype",list_phenotype);
        map.put("Genotype",list_genotype);
        map.put("Disease",list_disease);
        map.put("Article",list_article);
        map.put("Sample",list_sample);
        map.put("Variation",list_variation);

        return map;
    }
    public static Map<String,Set> disease_related_phenotype(List<Object> NodeValueList){
        List<Map<String,Object>> result = new ArrayList<>();
        NodeValueList.forEach((node) -> {
            Map<String,Object> resultmap = new HashMap<>();
            NodeValue nodeValue = (NodeValue) node;

            resultmap.put("id",nodeValue.asNode().id());
            String  label = nodeValue.asNode().labels().iterator().next();
            String id = String.valueOf(nodeValue.asNode().asMap().get("phenotypeName"));
            resultmap.put("showname",id);
            Map<String,Object> properties = nodeValue.asNode().asMap();

            resultmap.put("label",label);
            resultmap.put("properties",properties);
            result.add(resultmap);

        });
        HashSet<Map<String,Object>> set = new HashSet<>(result);
        Map<String,Set> map = new HashMap<>();
        map.put("FacePhenotype",set);
        return map;

    }
    public static Map<String,Set> findBySample_Phenotype(List<Object> NodeValueList){
        List<Map<String,Object>> result = new ArrayList<>();
        NodeValueList.forEach((node) -> {
            Map<String,Object> resultmap = new HashMap<>();
            NodeValue nodeValue = (NodeValue) node;

            resultmap.put("id",nodeValue.asNode().id());
            String  label = nodeValue.asNode().labels().iterator().next();
            String id = String.valueOf(nodeValue.asNode().asMap().get("phenotypeName"));
            resultmap.put("showname",id);
            Map<String,Object> properties = nodeValue.asNode().asMap();

            resultmap.put("label",label);
            resultmap.put("properties",properties);
            result.add(resultmap);

        });
        HashSet<Map<String,Object>> set = new HashSet<>(result);
        Map<String,Set> map = new HashMap<>();
        map.put("FacePhenotype",set);
        return map;

    }


    public static <T> Map<String,List> getNodeAndRelationListandsamplelist(List<Object> pathValueList, List<Object> NodeValueList) {

        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> result_re = new ArrayList<>();
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();

            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                //用于存储节点对应信息
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("id",node.id());
                String  label = node.labels().iterator().next();
                if (label.equals("Disease")){
                    String id = String.valueOf(node.asMap().get("dname"));
                    resultmap.put("showname",id);
                }
                if (label.equals("FacePhenotype")){
                    String id = String.valueOf(node.asMap().get("phenotypeName"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Genotype")){
                    String id = String.valueOf(node.asMap().get("geneName"));
                    if(id.equals("null")){
                        String id2 = String.valueOf(node.asMap().get("chromosomesName"));
                        resultmap.put("showname",id2);
                    }else {
                        resultmap.put("showname",id);
                    }

                }
                if (label.equals("Article")){
                    String id = String.valueOf(node.asMap().get("pmid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Sample")){
                    String id = String.valueOf(node.asMap().get("sid"));
                    resultmap.put("showname",id);
                }
                if (label.equals("Variation")){
                    String id = "Var_"+node.asMap().get("gdid");
                    resultmap.put("showname",id);
                }


                Map<String,Object> properties = node.asMap();

                resultmap.put("label",label);
                resultmap.put("properties",properties);
                result.add(resultmap);
            });

            Iterable<Relationship> relationships = segments.relationships();
            relationships.forEach((relationship) -> {
                Map<String,Object> resultmap_re = new HashMap<>();
                resultmap_re.put("source",relationship.startNodeId());
                resultmap_re.put("target",relationship.endNodeId());
                resultmap_re.put("type",relationship.type());
                resultmap_re.put("properties",relationship.asMap());
                resultmap_re.put("id",relationship.id());
                resultmap_re.put("lineNumber",null);
                result_re.add(resultmap_re);
            });
        });

        NodeValueList.forEach((node) -> {
            Map<String,Object> resultmap = new HashMap<>();
            NodeValue nodeValue = (NodeValue) node;

            resultmap.put("id",nodeValue.asNode().id());
            String  label = nodeValue.asNode().labels().iterator().next();
            String id = String.valueOf(nodeValue.asNode().asMap().get("phenotypeName"));
            resultmap.put("showname",id);
            Map<String,Object> properties = nodeValue.asNode().asMap();

            resultmap.put("label",label);
            resultmap.put("properties",properties);
            result.add(resultmap);

        });



        HashSet<Map<String,Object>> set = new HashSet<>(result);
        result.clear();
        result.addAll(set);
        HashSet<Map<String,Object>> set_re = new HashSet<>(result_re);
        result_re.clear();
        result_re.addAll(set_re);

        List<Map<String,Object>> list_for_similar_sample = new ArrayList<>();
        Long user_id = null;
        for(Map<String,Object> set_node:set){

            Map<String,Object> map = (Map<String, Object>) set_node.get("properties");
            String sid = (String) map.get("sid");
            if(set_node.get("label").equals("Sample")&&sid.startsWith("u")){
                user_id = (Long) set_node.get("id");
            }
            if(set_node.get("label").equals("Sample")&&sid.startsWith("s")){

                Map<String,Object> sample_node = new HashMap<>();
                sample_node.put("name",set_node.get("showname"));
                sample_node.put("similar",null);
                sample_node.put("id",set_node.get("id"));
                list_for_similar_sample.add(sample_node);
            }
            if(list_for_similar_sample.size()==14){
                break;
            }
        }
        for (Map<String,Object> set_relation:set_re){
            if(set_relation.get("source").equals(user_id)){
                Map<String,Object> map1 = (Map<String, Object>) set_relation.get("properties");
                Double similar = (Double) map1.get("score");
                for(Map<String, Object> list:list_for_similar_sample){
                    if(list.get("id").equals(set_relation.get("target"))){
                        list.put("similar",similar);
                    }
                }
            }
        }



//        System.out.println(result);
//        System.out.println(result_re);
        List<Map<String,Object>> all_result = new ArrayList<>();
        Map<String,Object> all_result_map = new HashMap<>();
        all_result_map.put("nodes",result);
        all_result_map.put("links",result_re);
        all_result.add(all_result_map);


        Map<String,List> result_map = new HashMap<>();

        result_map.put("result1",all_result);
        result_map.put("result2",list_for_similar_sample);

        return result_map ;
    }

}
