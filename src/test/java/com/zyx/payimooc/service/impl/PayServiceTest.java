package com.zyx.payimooc.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.zyx.payimooc.PayimoocApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PayServiceTest extends PayimoocApplicationTests {

    @Autowired
    private PayService payService;

    @Test
    public void create() {

        payService.create("wxd898fcb01713c658", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }


}