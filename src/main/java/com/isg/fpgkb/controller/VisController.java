package com.isg.fpgkb.controller;

import com.isg.fpgkb.dto.Menu_Entity;
import com.isg.fpgkb.service.PhenotypeService;
import com.isg.fpgkb.service.SearchService;
import com.isg.fpgkb.service.VisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title VisController
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 14:21
 * @Description TODO
 **/
@Api(tags = "数据可视化")
@RequestMapping("/vis")
@CrossOrigin
@RestController
@ResponseBody
public class VisController {
    VisService visService;

    @Autowired
    PhenotypeService phenotypeService;

    @Autowired
    public VisController(VisService visService){
        this.visService = visService;
    }


    @ApiOperation(value = "获取所有表型数据")
    @GetMapping("/menu")
    public Object Phenotype_Menu(){
        Object res = visService.Phenotype_Menu();
        return res;
    }

//    @ApiOperation(value = "Test-通过pid从neo4j获取知识图谱数据")
//    @PostMapping("/getPhenotype")
//    public List<Map<String,Object>> getPhenotype(String pid){
//        List<Map<String,Object>> res = phenotypeService.getByPid(pid);
//        System.out.println(res);
//        return res;
//    }

    @ApiOperation(value = "上传用户所选择的表型数据，用于查询知识图谱(表型数大于等于4)")
    @PostMapping("/search_complex")
    public Object Search(@RequestBody HashMap s){
//        System.out.println(s.get("list"));
//        System.out.println(s.get("option"));
        Object res = visService.get_Neo4j_vis_complex(s);
        //System.out.println(res);
        return res;
    }

    @ApiOperation(value = "上传用户所选择的表型数据，用于查询知识图谱(表型数小于4)")
    @PostMapping("/search_simple")
    public Object Search(@RequestBody List<Menu_Entity> list){
//        System.out.println(s.get("list"));
//        System.out.println(s.get("option"));
        Object res = visService.get_Neo4j_vis_simple(list);
        //System.out.println(res);
        return res;
    }

    @ApiOperation(value = "Neo4j获取所有表型数据")
    @GetMapping("/getallPhenotype")
    public Object getallPhenotype(){
        Object res = phenotypeService.findallphenotype();
        return res;
    }

    @ApiOperation(value = "获取某个样本的相关数据")
    @PostMapping("/getAboutSample")
    public Object getAboutSample(@RequestBody String sid){
        System.out.println(sid);
        System.out.println(sid.substring(0,sid.length()-1));
        Object res = visService.get_info_Sample(sid.substring(0,sid.length()-1));
        return res;
    }

//    @ApiOperation(value = "根据表型id获取其详细信息")
//    @PostMapping("/get_pheno_details")
//    public Object get_details_Phenotype(String pid){
//        System.out.println(pid);
//        Object res = visService.get_details_Phenotype(pid);
//        return res;
//    }

}
