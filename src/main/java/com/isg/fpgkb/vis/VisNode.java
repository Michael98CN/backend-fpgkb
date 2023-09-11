package com.isg.fpgkb.vis;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title VisNode
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/10 9:01
 * @Description TODO
 **/
/*
* 将neo4j数据转化成d3.js可以识别的数据结构
* *
* */

@Data
public class VisNode {

    //可以是表型的pid，也可以是表型的名称name
    private String id;

    private String label;

    private Object properties;

    public VisNode(String id, String label, Object properties) {
        this.id = id;
        this.label = label;
        this.properties = properties;

    }
}
