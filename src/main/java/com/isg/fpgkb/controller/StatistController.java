package com.isg.fpgkb.controller;

import com.isg.fpgkb.service.StatService;
import com.isg.fpgkb.service.VisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Title StatistController
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 14:22
 * @Description TODO
 **/
@Api(tags = "数据统计可视化")
@RequestMapping("/statistics")
@CrossOrigin
@RestController
@ResponseBody
public class StatistController {
    @Autowired
    StatService statService;

    @Autowired
    public StatistController(StatService statService){
        this.statService = statService;
    }

    @ApiOperation(value = "表型数据可视化（TreeMap）")
    @GetMapping("/treemap")
    public Object Phenotype_vis(){
        Object res = statService.Phenotype_vis();
        return res;
    }

    @ApiOperation(value = "基因数据可视化（pie）")
    @GetMapping("/pie")
    public Object Gene_vis(){
        Object res = statService.Gene_vis();
        System.out.println(res);
        return res;
    }

    @ApiOperation(value = "样本数据可视化（geo）")
    @GetMapping("/geo")
    public Object Sample_vis(){
        Object res = statService.Sample_vis();
        return res;
    }

    @ApiOperation(value = "基因人脸表型关系数据可视化（sankey）")
    @PostMapping("/sankey")
    public Object Gene_Phenotype_re_vis(@RequestBody HashMap s){
        System.out.println(s);
        String type = (String) s.get("type");
        String content = (String) s.get("content");
        Object res = statService.Gene_Phenotype_re_vis(type,content);
        return res;
    }

    @ApiOperation(value = "基因人脸表型关系数据可视化（sankey）,可进行筛选")
    @PostMapping("/getitem")
    public Object getlistofsankey(String type){
        //System.out.println(type);
        Object res = statService.getlistofitem(type);
        return res;
    }




}
