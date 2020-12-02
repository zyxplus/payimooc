package com.zyx.payimooc.service;

import com.zyx.payimooc.PayimoocApplicationTests;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.vo.CategoryVo;
import com.zyx.payimooc.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ICategoryServiceTest extends PayimoocApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }
}
