package com.zyx.payimooc.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zyx.payimooc.PayimoocApplicationTests;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.form.CartAddForm;
import com.zyx.payimooc.form.CartUpdateForm;
import com.zyx.payimooc.vo.CartVo;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Slf4j
public class ICartServiceTest extends PayimoocApplicationTests {

    @Autowired
    private ICartService cartService;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Integer productId1 = 26;
    private Integer productId2 = 27;
    private Integer uid = 1;

    @Before
    public void add() {

        log.info("添加商品");
        CartAddForm form = new CartAddForm();
        form.setProductId(productId1);
        form.setSelected(true);
        ResponseVo<CartVo> list = cartService.add(1, form);

        form = new CartAddForm();
        form.setProductId(productId2);
        form.setSelected(false);
        list = cartService.add(uid, form);


    }

    @Test
    public void list() {
        ResponseVo<CartVo> responseVo = cartService.list(uid);
//        log.info("list={}", gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }


    @Test
    public void update() {
        CartUpdateForm form = new CartUpdateForm();
        form.setQuantity(10);
        form.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.update(uid, productId2, form);
//        log.info("list={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }

    @After
    public void delete() {
        log.info("删除商品");
        cartService.delete(uid,productId1);
        cartService.delete(uid,productId2);
    }


    @Test
    public void selectAll() {
        ResponseVo<CartVo> responseVo = cartService.selectAll(uid);
//        log.info("list={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }

    @Test
    public void unSelectAll() {
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(uid);
//        log.info("list={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }

    @Test
    public void sum() {
        ResponseVo<Integer> responseVo = cartService.sum(uid);
//        log.info("list={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }
}

