package com.zyx.payimooc.service;

import com.zyx.payimooc.PayimoocApplicationTests;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.form.ShippingForm;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class IShippingServiceTest extends PayimoocApplicationTests {

    @Autowired
    private IShippingService shippingService;

    private Integer uid = 1;
    private Integer shippingId = 6;
    private ShippingForm form;

    @Before
    public void before() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("zyx");
        form.setReceiverAddress("address");
        form.setReceiverCity("bj");
        form.setReceiverDistrict("海淀区");
        form.setReceiverMobile("13131313131");
        form.setReceiverPhone("7700000");
        this.form = form;
        add();
    }

    public void add() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        log.info("result= > ======== >" + responseVo);
        this.shippingId = responseVo.getData().get("shippingId");
//        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @After
    public void delete() {
        ResponseVo responseVo = shippingService.delete(uid, shippingId);
        log.info("result= > ======== >" + responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update() {
        form.setReceiverCity("sz");
        ResponseVo responseVo = shippingService.update(uid, shippingId, form);
        log.info("result= > ======== >" + responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }

    @Test
    public void list() {
        ResponseVo responseVo = shippingService.list(uid, 1, 10);
        log.info("result= > ======== >" + responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());


    }
}
