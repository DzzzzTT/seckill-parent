package com.bmw.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.bmw.seckill.common.base.BaseRequest;
import com.bmw.seckill.common.base.BaseResponse;
import com.bmw.seckill.common.entity.CommonWebUser;
import com.bmw.seckill.common.exception.ErrorMessage;
import com.bmw.seckill.model.SeckillUser;
import com.bmw.seckill.model.http.UserReq;
import com.bmw.seckill.model.http.UserResp;
import com.bmw.seckill.security.WebUserUtil;
import com.bmw.seckill.service.UserService;
import com.bmw.seckill.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final String USER_PHONE_CODE_BEFORE = "u:p:c:b";

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取短信验证码
     * 1、验证手机号是否为注册用户手机号，如果不是报错
     * 2、生成随机数字，然后调用第三方接口给用户发送短信验证码（第三方一般都有sdk，接口都是http的，即我们可以使用httpcient发送数据）
     * 3、将手机号作为key、验证码为value、超时时间传入redis中，给接口调用方返回成功信号
     */
    @PostMapping("/getPhoneSmsCode")
    public BaseResponse<Boolean> getPhoneSmsCode(@Valid @RequestBody BaseRequest<UserReq.BaseUserInfo> req){
        String phone = req.getData().getPhone();
        SeckillUser seckillUser = userService.findByPhone(phone);

        if (seckillUser != null) {
            String randomCode = "123456";
            redisUtil.set(USER_PHONE_CODE_BEFORE + phone, randomCode,60*30);
            return BaseResponse.ok(true);
        }else return BaseResponse.ok(false);
    }


    /**
     * 验证码登录
     * 1、先验证用户输入的短信验证码是否正确
     * 2、验证成功后删除redis种的短信验证码
     * 3、生成登录用的token，下发token
     */
    @PostMapping("/userPhoneLogin")
    public BaseResponse userPhoneLogin(@Valid @RequestBody BaseRequest<UserReq.LoginUserInfo> req) throws Exception{
        UserReq.LoginUserInfo loginInfo = req.getData();
        System.out.println("333333333"+loginInfo);
        Object existObj = redisUtil.get(USER_PHONE_CODE_BEFORE + loginInfo.getPhone());
        if (existObj == null || !existObj.toString().equals(loginInfo.getSmsCode())) {
            return BaseResponse.error(ErrorMessage.SMSCODE_ERROR);
        } else {
            redisUtil.del(USER_PHONE_CODE_BEFORE + loginInfo.getPhone());

            SeckillUser seckillUser = userService.findByPhone(loginInfo.getPhone());
            System.out.println("【】【】【】【】【】"+seckillUser);
            CommonWebUser commonWebUser = new CommonWebUser();
            BeanUtils.copyProperties(commonWebUser, seckillUser);
            System.out.println("1111111111"+commonWebUser);
            String token = UUID.randomUUID().toString().replaceAll("-","");
            //设置token超时时间为1个月，实际根据需求确定
            redisUtil.set(token, JSON.toJSONString(commonWebUser), 60*60*24*30);
            System.out.println("==========="+JSON.toJSONString(commonWebUser));
            UserResp.BaseUserResp resp = new UserResp.BaseUserResp();
            resp.setToken(token);
            return BaseResponse.ok(resp);
        }

    }

    @GetMapping("/checkUserToken")
    public void checkUserToken(){
        CommonWebUser commonWebUser = WebUserUtil.getLoginUser();
        log.info(JSON.toJSONString(commonWebUser));
    }
}
