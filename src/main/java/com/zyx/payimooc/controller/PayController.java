package com.zyx.payimooc.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.zyx.payimooc.pojo.PayInfo;
import com.zyx.payimooc.service.impl.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    /** 返回一个网页
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum
                               ) {

        PayResponse response = payService.create(orderId, amount, bestPayTypeEnum);

        Map<String, String> map = new HashMap<>();
        //alipay 和 wechat 返回的request不同，需要使用不同的渲染策略
        if (bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
            map.put("body", response.getBody());
            return new ModelAndView("createForAlipayPc", map);
        } else if (bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE) {
            map.put("codeUrl", response.getCodeUrl());
            return new ModelAndView("createForWxNative", map);
        }

        throw new RuntimeException("暂不支持的支付类型");


    }

    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData){
        return payService.asyncNotify(notifyData);
//        log.info("notifyData={}", notifyData);
    }

    /**
     * @Controller 和 @RestController 的区别: @RestController 允许返回对象
     * @param orderId
     * @return
     */
    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderId(@RequestParam String orderId) {
        log.info("查询支付记录。。。");
        return payService.queryByOrderId(orderId);
    }


}
