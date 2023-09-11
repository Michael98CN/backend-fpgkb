package com.isg.fpgkb.repository;

import com.isg.fpgkb.dto2.Phenotype1;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.core.Neo4jOperations.ExecutableQuery;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface PhenotypeRepository  extends Neo4jRepository<Phenotype1,Long> {

    //Phenotype1 findPhenotypeByPid(String pid);

    @Query(value = "match (n:FacePhenotype) return n")
    List<Phenotype1> findAll();

    //match p =()-[]->(n:FacePhenotype) where n.pid="HP:0000431" return p union match p = (n:FacePhenotype)-[*..2]->()where n.pid="HP:0000431" return p
    //查询某个表型相关联的信息
    //@Query(value = "match p =()-[]->(n:FacePhenotype{pid:$pid}) return p union  match p = (n:FacePhenotype{pid:$pid})-[*..2]->() return p")
    //@Query(value = "match p =()-[]->(n:FacePhenotype{pid:$pid}) return p union  match p = (n:FacePhenotype{pid:$pid})-[*..2]->() return p union match  p =(g:Genotype)-[]->(:Disease) match q= (g:Genotype)-[]->(:FacePhenotype{pid:$pid}) where LENGTH(q) > 0  return p")
//    @Query(value = "match p =()-[]->(n:FacePhenotype{pid:$pid}) return distinct p union  match p = (n:FacePhenotype{pid:$pid})-[*..3]->(a)  where NOT (a:FacePhenotype) return distinct p")
    @Query(value = "MATCH (n:FacePhenotype{pid:$pid})-[:Mention_FP]->(a:Sample)\n" +
        "WITH a\n" +
        "LIMIT 10\n" +
        "MATCH p = (n:FacePhenotype{pid:$pid})-[:Mention_FP]->(a)-[:Mention_Var]->()-[:Have|:Cause]->()-[:Affect|:Has_Phenotype]->(n)\n" +
        "return p\n" +
        "union\n" +
        "match p = (n:FacePhenotype{pid:$pid})-[:Mention_FP]->(a)-[:Exist]->()\n" +
        "RETURN distinct p")
    List<Object>findPhenotype1ByPid(@Param("pid") String pid);

//match p =()-[]->(n:FacePhenotype{pid:"HP:0000445"}) return p union  match p = (n:FacePhenotype{pid:"HP:0000445"})-[*..2]->() return p union match  p =(g:Genotype)-[]->(:Disease) where (g:Genotype)-[]->(:FacePhenotype{pid:"HP:0000445"}) return p
//    PROFILE WITH ["HP:0000431","HP:0000574","HP:0000256","HP:0012724"] AS id_list
//    match (n:FacePhenotype) where n.pid In id_list
//    with collect(n) As nodes
//    unwind nodes as source
//    unwind nodes as target
//    match paths = (source)--()--(target)
//    return paths,nodes
    //查询多个表型之间的关系节点

    @Query(value = "match (n:FacePhenotype) where n.pid In $list\n" +
        "        with collect(n) As nodes\n" +
        "       unwind nodes as source\n" +
        "            unwind nodes as target\n" +
        "            match paths = (source)--(m)--(target)\n" +
        "            return paths\n" +
        "union\n" +
        "match (n:FacePhenotype) where n.pid In $list\n" +
        "        with collect(n) As nodes\n" +
        "       unwind nodes as source\n" +
        "            unwind nodes as target\n" +
        "            match paths = (source)--(m)--(target)\n" +
        "            with collect(m) As s\n" +
        "            unwind s as source1\n" +
        "            match paths = ()<-[:Exist]-(source1)-[:Mention_Var]->()-[:Have|:Cause]->()-[:Affect|:Has_Phenotype]->(n)\n" +
        "            where n.pid In $list\n" +
        "            return paths")
   //@Query(value = "match p = (n:FacePhenotype)-[]->() where n.pid In $list return p union match p=()-[]->(n:FacePhenotype) where n.pid In $list return p")
    //@Query(value = "match p = (n:FacePhenotype)-[]->() where n.pid In $list return p union match p=()-[]->(n:FacePhenotype) where n.pid In $list return p union match p =(n:FacePhenotype)-[*..2]->() where n.pid In $list return p union match p = (g:Genotype)-[*..2]->(n:FacePhenotype) where n.pid In $list return p")
    List<Object>findReByPid(@Param("list") List list);

    @Query(value = "match (n:FacePhenotype) where n.pid In $list " +
            "return n")
    List<Object>findNodeByPid(@Param("list") List list);

    //查询同一染色体下，基因和表型的关系
    @Query(value = "match paths = (n:Genotype)-[]->(m:FacePhenotype) where n.chromosomesName=$chr return paths")
    List<Object>findReGenePhenotypebychr(@Param("chr") String chr);

    //查询某个基因与表型的关系
    @Query(value = "match paths = (n:Genotype)-[]->(m:FacePhenotype) where n.geneName=$genename return paths")
    List<Object>findReGenePhenotypebygene(@Param("genename") String genename);


    //查询某个表型与哪些基因的关系
    @Query(value = "match paths = (n:Genotype)-[]->(m:FacePhenotype) where m.phenotypeName=$phenotype return paths")
    List<Object>findReGenePhenotypebyPhenotype(@Param("phenotype") String phenotype);



    //获取某个样本相关联的所有节点
    @Query(value = "match p=(N:FacePhenotype)-[]->(s:Sample{sid:$sid})-[:Mention_Var]->(m)-[:Have|:Cause]->()\n" +
        "        return distinct p\n" +
        "        union\n" +
        "        match p=(s:Sample{sid:$sid})-[:Exist]->()\n" +
        "        return distinct p\n" +
        "        union\n" +
        "        match p= (s:Sample{sid:$sid})-[:Mention_Var]->(m)\n" +
        " return distinct p\n" +
        " union\n" +
        " match p=(N:FacePhenotype)-[]->(s:Sample{sid:$sid})-[:Mention_Var]->(m)-[:Have|:Cause]->()-[:Affect|:Has_Phenotype]->(N:FacePhenotype)\n" +
        " return distinct p")
    List<Object>findInfoBySample(@Param("sid") String sid);

    @Query(value = "match (n)-[r:Mention_FP]->(s:Sample{sid:$sid}) return n")
    List<Object> findBySample_Phenotype(@Param("sid") String sid);
    @Query(value = "match p = (d:Disease{did:$did})-[r:Has_Phenotype]->(n)\n" +
        "return n")
    List<Object>Disease_related_phenotype(@Param("did") Integer did);

    @Query(value = "match p = (d:Disease{did:$did})-[r:Has_Phenotype]->(n)\n" +
        "return n,count(*)")
    Object test(@Param("did") Integer did);
    //获取知识图谱中人脸表型、基因、疾病的数量
    @Query(value = "match (n:FacePhenotype) return count(n)")
    Integer SumPhenotype();

    @Query(value = "match (n:Genotype) return count(n)")
    Integer Sumgenotype();

    @Query(value = "match (n:Disease) return count(n)")
    Integer Sumdisease();


/******************************************************************************************************/
//先检查用户输入的表型在已有的图谱中是否存在


@Query(value = "match (source)-[r:Mention_FP]->(s) \n" +
    "where source.pid IN \n" +
    " $list \n" +
    "with s,count(DISTINCT source.pid) as cnt\n" +
    "where cnt=$size\n" +
    "return s")
List<Object> check_already_exist(@Param("list") List list,@Param("size") Integer size);


    @Query(value = "match p=(N:FacePhenotype)-[]->(s:Sample)-[:Mention_Var]->(m)-[:Have|:Cause]->() where s.sid IN $list\n" +
        "return distinct p\n" +
        "union\n" +
        "match p=(N:FacePhenotype)-[]->(s:Sample)-[:Exist]->() where s.sid IN $list\n" +
        "return distinct p")
    List<Object>findInfoBySamplelist(@Param("list") List list);
/*
    * 相似度计算的一些方法
    *
    * */
    //创建用户节点和连接关系
    @Query(value = "Merge (s1:Sample{sid:$sid,year:$age,race:$race,region:$region})\n" +
        "with s1 as user_node\n" +
        "Match (n:FacePhenotype) where n.pid In $list\n" +
        "        with collect(n) As nodes\n" +
        "       unwind nodes as source\n" +
        "match(s:Sample{sid:$sid})\n" +
        "Merge p= (source)-[:Mention_FP]->(s)\n" +
        "return p")
    List<Object> merge_user_node(@Param("sid") String sid,@Param("list") List list,@Param("age") String age,@Param("race") String race,@Param("region") String region);



/*****************************************************************************************/
    //Grph embedding
    @Query(value = "CALL gds.graph.project('Micheal-test-1',\n" +
        "'*',\n" +
        "    {\n" +
        "    relType: {\n" +
        "      type: '*',\n" +
        "      orientation: 'UNDIRECTED',\n" +
        "      properties: {}\n" +
        "    }\n" +
        "  },\n" +
        "  {}\n" +
        "    )" +
        "YIELD graphName\n" +
        "RETURN graphName")
    String build_graph_project();

    //使用node2vec

    @Query("CALL gds.beta.node2vec.write(\"Micheal-test-1\",{\n" +
        "  relationshipWeightProperty: null,\n" +
        "  iterations: 1,\n" +
        "  embeddingDimension: 10,\n" +
        "  walkLength: 80,\n" +
        "  inOutFactor: 1,\n" +
        "  returnFactor: 1,\n" +
        "  writeProperty: 'node2Vec'\n" +
        "})" +
        "YIELD computeMillis\n" +
        "RETURN computeMillis")
    Integer use_node2vec();


    //删除graph_project
    @Query(value = "CALL gds.graph.drop('Micheal-test-1')" +
        "YIELD graphName\n" +
        "RETURN graphName")
    String delete_graph_project();
/*****************************************************************************************************/

//创建KNN需要的graph_project
    @Query(value = "CALL gds.graph.project('Micheal-test-1',\n" +
        "'Sample',\n" +
        "    {\n" +
        "    relType: {\n" +
        "      type: '*',\n" +
        "      orientation: 'UNDIRECTED',\n" +
        "      properties: {}\n" +
        "    }\n" +
        "  },\n" +
        "  {nodeProperties: [\"node2Vec\"]}\n" +
        "    )" +
        "YIELD graphName\n" +
        "RETURN graphName")
    String build_knn_graph();

    //使用KNN算法
    @Query(value = "CALL gds.knn.write('Micheal-test-1',\n" +
        "{\n" +
        "  topK: 10,\n" +
        "  randomJoins: 10,\n" +
        "  sampleRate: 0.5,\n" +
        "  deltaThreshold: 0.001,\n" +
        "  nodeProperties: {\n" +
        "    node2Vec: 'COSINE'\n" +
        "  },\n" +
        "  writeProperty: 'score',\n" +
        "  writeRelationshipType: 'SIMILAR_KNN'\n" +
        "}\n" +
        ")" +
        "YIELD writeMillis\n" +
        "RETURN writeMillis")
    Integer use_knn();

/********************************************************************************************************/
//查询相似度在0.93以上的sample节点及其信息


    @Query(value = "match p=(s:Sample{sid:$sid})-[r:SIMILAR_KNN]-(s2) where r.score>=0.93 \n" +
        "with  collect(distinct s2.sid) As nodes\n" +
        "match p=(N:FacePhenotype)-[]->(s:Sample)-[:Mention_Var]->(m)-[:Have|:Cause]->()-[:Affect|:Has_Phenotype]->(N:FacePhenotype) where s.sid IN nodes\n" +
        "return distinct p\n" +
        "union\n" +
        "match p=(s:Sample{sid:$sid})-[r:SIMILAR_KNN]-(s2) where r.score>=0.93 \n" +
        "with  collect(distinct s2.sid) As nodes\n" +
        "match p=(N:FacePhenotype)-[]->(s:Sample)-[:Exist]->() where s.sid IN nodes\n" +
        "return distinct p\n" +
        "union\n" +
        "match p=()-[:Mention_FP]->(s:Sample{sid:$sid})\n" +
        "return p\n" +
        " union \n" +
        "        match p=(s:Sample{sid:$sid})-[r:SIMILAR_KNN]-(s2) where r.score>=0.93\n" +
        "        return p")
    List<Object> get_info_similar_s(@Param("sid") String sid);





/******************************************************************************************************/
//重置操作
@Query(value = "MATCH ()-[r:SIMILAR_KNN]->() delete r")
List<Object> reset_sim_knn();

    @Query(value = "match (n) remove n.node2Vec")
    List<Object> reset_node2vec();

    @Query(value = "match (s:Sample{sid:$sid}) delete s")
    List<Object> reset_user_node(@Param("sid") String sid);

    @Query(value = "match ()-[r:Mention_FP]-> (s:Sample{sid:$sid}) delete r")
    List<Object> reset_relation_user(@Param("sid") String sid);

}


