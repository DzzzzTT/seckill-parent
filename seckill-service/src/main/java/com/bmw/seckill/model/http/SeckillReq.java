package com.bmw.seckill.model.http;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SeckillReq implements Serializable {

    @NotNull(message = "产品id不能为空")
    private Long productId;

    private Long userId;
}
