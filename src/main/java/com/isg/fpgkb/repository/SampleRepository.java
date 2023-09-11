package com.isg.fpgkb.repository;

import com.isg.fpgkb.dto2.Sample1;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SampleRepository extends Neo4jRepository<Sample1,Long> {
}
