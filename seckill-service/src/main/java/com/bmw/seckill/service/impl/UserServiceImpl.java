package com.bmw.seckill.service.impl;

import cn.hutool.core.lang.Assert;
import com.bmw.seckill.dao.SeckillUserDao;
import com.bmw.seckill.model.SeckillUser;
import com.bmw.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SeckillUserDao seckillUserDao;

    @Override
    public SeckillUser findByPhone(String phone) {
        //首先，判断手机号是否为空
        Assert.notNull(phone,"请输入手机号");

        SeckillUser seckillUser = new SeckillUser();
        seckillUser.setPhone(phone);
        List<SeckillUser> list = seckillUserDao.list(seckillUser);
        if (!CollectionUtils.isEmpty(list)){
            Assert.isTrue(list.size() == 1);
            return list.get(0);
        }
        return null;
    }
}
