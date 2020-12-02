package com.zyx.payimooc.service.impl;

import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.zyx.payimooc.enums.PayPlatformEnum;
import com.zyx.payimooc.dao.PayInfoMapper;
import com.zyx.payimooc.pojo.PayInfo;
import com.zyx.payimooc.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class PayService implements IPayService {

    //支付相关
    @Autowired
    private BestPayService bestPayService;


    @Resource
    private PayInfoMapper payInfoMapper;

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {

        PayInfo payInfo = new PayInfo(Long.parseLong(orderId),
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount
                );
        payInfoMapper.insertSelective(payInfo);


        //仅支持两种支付方式
        //把订单信息写入数据库

        // 发起支付
        PayRequest request = new PayRequest();
        request.setOrderName("9196776-最好的支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(0.01);
        // 支付场景
        request.setPayTypeEnum(bestPayTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("发起支付：response={}" + response);

        return response;
    }

    @Override
    public String asyncNotify(String notifyData) {
        //1. 签名检验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("异步通知：payResponse={}", payResponse);

        //2. 金额检验
        PayInfo payInfo = payInfoMapper.selectByOrderId(Long.parseLong(payResponse.getOrderId()));
        if (payInfo == null) {
            throw new RuntimeException("查无此订单");
        }
        //订单状态尚未支付
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())){
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0){
                throw new RuntimeException("异步通知时发现订单金额被二次修改, orderId =" + payResponse.getOrderId());
            }

            //3. 支付后修改订单支付状态
            //修改订单状态
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            //修改交易流水号
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            payInfoMapper.updateByPrimaryKeySelective(payInfo);

        }

        //4. "已支付"状态下，不要重复通知同一订单
        if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
            return "success";
        } else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }
        throw new RuntimeException("异步通知错误: 平台-" + payResponse.getPayPlatformEnum());
    }



    @Override
    public PayInfo queryByOrderId(String orderId){
        return payInfoMapper.selectByOrderId(Long.parseLong(orderId));
    }



}
