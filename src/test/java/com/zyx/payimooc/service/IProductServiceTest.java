package com.zyx.payimooc.service;

import com.github.pagehelper.PageInfo;
import com.zyx.payimooc.PayimoocApplicationTests;
import com.zyx.payimooc.dao.ProductMapper;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.vo.ProductDetailVo;
import com.zyx.payimooc.vo.ProductVo;
import com.zyx.payimooc.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class IProductServiceTest extends PayimoocApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = productService.list(null, 2, 2);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }

    @Test
    public void detail() {
        ResponseVo<ProductDetailVo> responseVo = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }
}
