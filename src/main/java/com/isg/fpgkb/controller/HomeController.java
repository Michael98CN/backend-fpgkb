package com.isg.fpgkb.controller;

import com.isg.fpgkb.service.HomeService;
import com.isg.fpgkb.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title HomeController
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/21 15:30
 * @Description TODO
 **/
@Api(tags = "首页")
@RequestMapping("/home")
@CrossOrigin
@RestController
@ResponseBody
public class HomeController {

    HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService){
        this.homeService = homeService;
    }

    @ApiOperation(value = "获取表型数量")
    @GetMapping("/sumPhenotype")
    public Object getSumPhenotype(){
        Object res = homeService.getSumPhenotype();

        return res;
    }

    @ApiOperation(value = "获取基因数量")
    @GetMapping("/sumGenotype")
    public Object getSumgenotype(){
        Object res = homeService.getSumGenotype();

        return res;
    }


    @ApiOperation(value = "获取疾病数量")
    @GetMapping("/sumDisease")
    public Object getSumDisease(){
        Object res = homeService.getSumDisease();

        return res;
    }


}
