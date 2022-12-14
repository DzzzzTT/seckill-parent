package com.bmw.seckill.service.impl;

import cn.hutool.core.lang.Assert;
import com.bmw.seckill.common.util.bean.CommonQueryBean;
import com.bmw.seckill.dao.SeckillProductsDao;
import com.bmw.seckill.model.SeckillProducts;
import com.bmw.seckill.service.ISeckillProductsService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("seckillProductsService")
public class SeckillProductsServiceImpl implements ISeckillProductsService {

    @Autowired
    private SeckillProductsDao seckillProductsDao;

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    public SeckillProducts  selectByPrimaryKey ( Long id ){
        return seckillProductsDao.selectByPrimaryKey(id);
    }

    /**
     *
     * 添加
     *
     **/
    public int insert( SeckillProducts record ){
        return seckillProductsDao.insert(record);
    }

    /**
     *
     * 修改 （匹配有值的字段）
     *
     **/
    public int updateByPrimaryKeySelective( SeckillProducts record ){
        return seckillProductsDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * list分页查询
     *
     **/
    public List<SeckillProducts> list4Page( SeckillProducts record, CommonQueryBean query){
        return seckillProductsDao.list4Page(record, query);
    }

    /**
     *
     * count查询
     *
     **/
    public long count(SeckillProducts record){
        return seckillProductsDao.count(record);
    }

    /**
     *
     * list查询
     *
     **/
    public List<SeckillProducts> list(SeckillProducts record){
        return seckillProductsDao.list(record);
    }

    /**
     * 给productPeriodKey添加unique唯一索引
     * @param record
     * @return
     */
    @Override
    public Long uniqueInsert(SeckillProducts record) {
        try{
            record.setCreateTime(new Date());
            record.setIsDeleted(0);
            record.setStatus(SeckillProducts.STATUS_IS_OFFLINE);

            SeckillProducts existItem = findByProductPeriodKey(record.getProductPeriodKey());
            if (existItem != null) return existItem.getId();
            else seckillProductsDao.insert(record);

        }catch (Exception e){
            if (e.getMessage().indexOf("Duplicate entry") >= 0){
                SeckillProducts existItem = findByProductPeriodKey(record.getProductPeriodKey());
                return existItem.getId();
            }else {
                log.error(e.getMessage(),e);
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public SeckillProducts findByProductPeriodKey(String productPeriodKey) {
        Assert.isTrue(!StringUtils.isEmpty(productPeriodKey));

        SeckillProducts item = new SeckillProducts();
        item.setProductPeriodKey(productPeriodKey);

        List<SeckillProducts> list = seckillProductsDao.list(item);
        if (CollectionUtils.isEmpty(list)) return null;
        else return list.get(0);
    }
}
