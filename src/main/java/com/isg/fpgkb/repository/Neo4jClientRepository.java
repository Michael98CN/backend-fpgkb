package com.isg.fpgkb.repository;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

@Repository
public class Neo4jClientRepository {
    @Resource
    Neo4jClient client;

    public Collection<Map<String, Object>> getphenptypeofDisease(Integer did) {
        return client
            .query("match p = (d:Disease{did:$did})-[r:Has_Phenotype]->(n)\n" +
                "return n , count(*) as num")
            .bind(did).to("did")
            .fetch()
            .all();
    }
}
