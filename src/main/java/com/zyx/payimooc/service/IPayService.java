package com.zyx.payimooc.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.zyx.payimooc.pojo.PayInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

public interface IPayService {

    /**
     * 创建订单
     * @param orderId 订单id
     * @param amount 金额
     * @return
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     * @param notifyData
     */
    public String asyncNotify(@RequestBody String notifyData);

    /**
     * 查询支付记录
     * @param orderId
     * @return
     */
    PayInfo queryByOrderId(String orderId);
}
