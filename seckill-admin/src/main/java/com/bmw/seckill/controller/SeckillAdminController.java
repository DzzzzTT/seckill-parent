package com.bmw.seckill.controller;


import com.bmw.seckill.model.SeckillAdmin;
import com.bmw.seckill.service.ISeckillAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/admin")
@Controller
@Slf4j
public class SeckillAdminController {
    @Autowired
    private ISeckillAdminService seckillAdminService;

    @RequestMapping(value = "/listAdminPage")
    public String listAdminPage(Model model){
        List<SeckillAdmin> list = seckillAdminService.listAdmin();
        model.addAttribute("list",list);
        return "admin/listAdminPage";
    }
}
