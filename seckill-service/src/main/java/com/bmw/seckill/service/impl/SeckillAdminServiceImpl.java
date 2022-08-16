package com.bmw.seckill.service.impl;

import cn.hutool.core.lang.Assert;
import com.bmw.seckill.dao.SeckillAdminDao;
import com.bmw.seckill.model.SeckillAdmin;
import com.bmw.seckill.service.ISeckillAdminService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillAdminServiceImpl implements ISeckillAdminService {

    @Autowired
    private SeckillAdminDao seckillAdminDao;

    @Override
    public List<SeckillAdmin> listAdmin() {
        SeckillAdmin item = new SeckillAdmin();
        return seckillAdminDao.list(item);
    }

    @Override
    public SeckillAdmin findByUsername(String username) {
        Assert.notNull(username);

        SeckillAdmin item = new SeckillAdmin();
        item.setLoginName(username);
        List<SeckillAdmin> list = seckillAdminDao.list(item);
        if (!CollectionUtils.isEmpty(list)) {
            Assert.isTrue(list.size() == 1);
            return list.get(0);
        }
        return null;
    }
}
