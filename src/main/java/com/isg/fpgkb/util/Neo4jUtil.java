package com.isg.fpgkb.util;

import lombok.extern.slf4j.Slf4j;
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
public class Neo4jUtil {
    public static <T> List<Map<String, Object>> getNodeList(List<Object> pathValueList, Class<T> tClass) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
        Map<String, List<String>> idListMap = new HashMap<>();
        List<String> nameFieldList = new ArrayList<>();
        //用于存放结果
        Set<Map<String,Object>> resultset = new HashSet<>();
        for (Field declaredField : tClass.getDeclaredFields()) {
            nameFieldList.add(declaredField.getName());
        }
        log.info("nameField",nameFieldList);
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
            Iterable<Node> nodes = segments.nodes();
            nodes.forEach((node) -> {
                List<String> labelList = new ArrayList<>();

                String nodeLabel = String.join(",", labelList);
                List<Map<String, Object>> maps = listMap.get(nodeLabel);
                if (ObjectUtils.isEmpty(maps))
                    maps = new ArrayList<>();
                List<String> idList = idListMap.get(nodeLabel);
                if (ObjectUtils.isEmpty(idList))
                    idList = new ArrayList<>();
                String id = String.valueOf(node.id());
                if (!idList.contains(id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("nodeId", node.id());
                    nameFieldList.forEach((nameField) -> {
                        if (node.containsKey(nameField))
                            map.put(nameField, node.get(nameField).asObject());
                    });
                    map.put("nodeLabel", nodeLabel);
                    maps.add(map);
                    idList.add(id);
                }
                listMap.put(nodeLabel, maps);
                idListMap.put(nodeLabel, idList);
            });
        });
        listMap.forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nodeLabel", key);
            map.put("nodeList", value);
            mapList.add(map);
        });
        return mapList;
    }

    /**
     * 解析 PathValue 转关系
     */
    public static <T> List<Map<String, Object>> getRelationList(List<Object> pathValueList, Class<T> tClass) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
        Map<String, List<String>> idListMap = new HashMap<>();
        List<String> nameFieldList = new ArrayList<>();
        for (Field declaredField : tClass.getDeclaredFields()) {
            nameFieldList.add(declaredField.getName());
        }
        pathValueList.forEach((o) -> {
            PathValue pathValue = (PathValue) o;
            Path segments = pathValue.asPath();
            Iterable<Relationship> relationships = segments.relationships();
            relationships.forEach((relationship) -> {
                String relationshipType = relationship.type();
                List<Map<String, Object>> maps = listMap.get(relationshipType);
                if (ObjectUtils.isEmpty(maps))
                    maps = new ArrayList<>();
                List<String> idList = idListMap.get(relationshipType);
                if (ObjectUtils.isEmpty(idList))
                    idList = new ArrayList<>();
                String id = String.valueOf(relationship.id());
                if (!idList.contains(id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("relationshipId", relationship.id());
                    map.put("relationshipType", relationship.type());
                    map.put("startNodeId", relationship.startNodeId());
                    map.put("endNodeId", relationship.endNodeId());
                    nameFieldList.forEach((nameField) -> {
                        if (relationship.containsKey(nameField))
                            map.put(nameField, relationship.get(nameField).asObject());
                    });
                    maps.add(map);
                    idList.add(id);
                }
                listMap.put(relationshipType, maps);
                idListMap.put(relationshipType, idList);
            });
        });
        listMap.forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("relationshipType", key);
            map.put("relationshipList", value);
            mapList.add(map);
        });
        return mapList;
    }

}
