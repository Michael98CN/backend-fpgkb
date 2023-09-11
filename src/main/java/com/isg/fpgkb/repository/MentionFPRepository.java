package com.isg.fpgkb.repository;

import com.isg.fpgkb.dto2.Relation_SP1_Mention_FP;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MentionFPRepository extends Neo4jRepository<Relation_SP1_Mention_FP,Long> {
}
