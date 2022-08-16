package com.bmw.seckill.service;

import com.bmw.seckill.common.base.BaseResponse;
import com.bmw.seckill.model.http.SeckillReq;

public interface SeckillService {

    //JVM  避免超卖
    BaseResponse sOrder(SeckillReq req);

    //悲观锁  避免超卖
    BaseResponse pOrder(SeckillReq req);

    //乐观锁  避免超卖
    BaseResponse oOrder(SeckillReq req) throws Exception;

}
