package com.bmw.seckill.controller;

import com.bmw.seckill.model.Demo;
import com.bmw.seckill.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/demo")
@Slf4j
@ComponentScan("com.bmw.seckill.service")
public class DemoController {

    @Resource
    private DemoService demoService;


    @RequestMapping(value = "/test")
    public List<Demo> test() {
        return demoService.list();
    }
}
