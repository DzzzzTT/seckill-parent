package com.bmw.seckill.controller;

import com.bmw.seckill.common.base.BaseRequest;
import com.bmw.seckill.common.base.BaseResponse;
import com.bmw.seckill.common.entity.CommonWebUser;
import com.bmw.seckill.common.exception.ErrorMessage;
import com.bmw.seckill.model.http.SeckillReq;
import com.bmw.seckill.security.WebUserUtil;
import com.bmw.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(value = "/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/simple/order")
    public BaseResponse sOrder(@Valid @RequestBody BaseRequest<SeckillReq> request){
        //验证token，获取user信息
        CommonWebUser loginUser = WebUserUtil.getLoginUser();
        if (Objects.isNull(loginUser)){
            return BaseResponse.error(ErrorMessage.LOGIN_ERROR);
        }
        SeckillReq data = request.getData();
        data.setUserId(loginUser.getId());
        return seckillService.sOrder(data);
    }

    /**
     * 秒杀下单（避免超卖问题——悲观锁）
     */
    @RequestMapping(value = "/pessimistic/order")
    public BaseResponse pOrder(@Valid @RequestBody BaseRequest<SeckillReq> request) {
        CommonWebUser user = WebUserUtil.getLoginUser();
        if (Objects.isNull(user)) {
            return BaseResponse.error(ErrorMessage.LOGIN_ERROR);
        }
        SeckillReq req = request.getData();
        req.setUserId(user.getId());
        return seckillService.pOrder(req);
    }

    /**
     * 秒杀下单（避免超卖问题——乐观锁）
     */
    @RequestMapping(value = "/optimistic/order")
    public BaseResponse oOrder(@Valid @RequestBody BaseRequest<SeckillReq> request) {
        try {
            CommonWebUser user = WebUserUtil.getLoginUser();
            if (Objects.isNull(user)) {
                return BaseResponse.error(ErrorMessage.LOGIN_ERROR);
            }
            SeckillReq req = request.getData();
            req.setUserId(user.getId());
            return seckillService.oOrder(req);
        } catch (Exception e) {
            log.error("===[秒杀异常！]===", e);
        }
        return BaseResponse.error(ErrorMessage.SYS_ERROR);
    }
}
