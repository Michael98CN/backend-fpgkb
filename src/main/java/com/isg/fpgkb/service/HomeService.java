package com.isg.fpgkb.service;

import com.isg.fpgkb.repository.PhenotypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title HomeService
 * @ProjectName fpgkb
 * @Author Admin
 * @Date 2022/10/21 15:33
 * @Description TODO
 **/
@Service
@Slf4j
public class HomeService {
    @Autowired
    private PhenotypeRepository phenotypeRepository;

    public Integer getSumPhenotype(){
        Integer sum = phenotypeRepository.SumPhenotype();
        return sum;
    }

    public Integer getSumGenotype(){
        Integer sum = phenotypeRepository.Sumgenotype();
        return sum;
    }

    public Integer getSumDisease(){
        Integer sum = phenotypeRepository.Sumdisease();
        return sum;
    }
}
