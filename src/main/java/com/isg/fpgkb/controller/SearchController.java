package com.isg.fpgkb.controller;


import com.isg.fpgkb.dto.*;
import com.isg.fpgkb.service.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

/**
 * @Title SearchController
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/8/4 14:21
 * @Description TODO
 **/



@Api(tags = "数据检索")
@RequestMapping("/search")
@CrossOrigin
@RestController
@ResponseBody
public class SearchController {


    SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }


    @ApiOperation(value = "获取详细表型数据")
    @PostMapping("/phenotype")
    public Object Get_Phenotype(String pid){
//        System.out.println(pid);
        Object res = searchService.Get_Phenotype(pid);
//        System.out.println(res);
        return res;
    }
    @ApiOperation(value = "获取详细基因数据")
    @PostMapping("/gene")
    public Object Get_Gene(Integer gene_id){
        System.out.println(gene_id);
        Object res = searchService.Get_Gene(gene_id);
        //System.out.println(res);
        return res;
    }
    @ApiOperation(value = "获取详细疾病数据")
    @PostMapping("/disease")
    public Object Get_Disease(Integer did){
        System.out.println(did);
        Object res = searchService.Get_Disease(did);
//        System.out.println(res);
        return res;
    }
    @ApiOperation(value = "获取详细文章数据")
    @PostMapping("/artcile")
    public Object Get_Article(Integer pmid){
//        System.out.println(pmid);
        Object res = searchService.Get_Article(pmid);
//        System.out.println(res);
        return res;
    }
    @ApiOperation(value = "获取详细样本数据")
    @PostMapping("/sample")
    public Object Get_Sample(String sid){
//        System.out.println(sid);
        Object res = searchService.Get_Sample(sid);
//        System.out.println(res);
        return res;
    }

    @ApiOperation(value = "获取所有数据库中数据")
    @GetMapping("/tablet")
    public Object Get_Tablet(){
        Object res = searchService.Get_Tablet();

        return res;
    }

    @ApiOperation(value = "获取疾病相关的表型数据")
    @PostMapping("/disease_related_phenotype")
    public Object disease_related_phenotype(String did){
        System.out.println(did);
        Integer did1 = Integer.parseInt(did);
        Object res = searchService.disease_related_phenotype(did1);
        return res;
    }

    @ApiOperation(value = "获取样本相关的表型数据")
    @PostMapping("/findBySample_Phenotype")
    public Object findBySample_Phenotype(String sid){
       System.out.println(sid);
//        Integer did1 = Integer.parseInt(did);
        Object res = searchService.findBySample_Phenotype(sid);
        return res;
    }

    @ApiOperation(value = "test")
    @PostMapping("/132")
    public Object test(){
        String did = "148050";
        Integer did1 = Integer.parseInt(did);
//        Integer did1 = Integer.parseInt(did);
        Object res = searchService.test(did1);
        return res;
    }

}
