package com.zyx.payimooc.dao;

import com.zyx.payimooc.PayimoocApplicationTests;
import com.zyx.payimooc.pojo.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ProductMapperTest extends PayimoocApplicationTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void selectByProductIdSet() {

        Set<Integer> productIdSet = new HashSet<>();
        productIdSet.add(26);
        productIdSet.add(27);
        List<Product> products = productMapper.selectByProductIdSet(productIdSet);



    }
}
