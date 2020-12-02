package com.zyx.payimooc.service;

import com.github.pagehelper.PageInfo;
import com.zyx.payimooc.vo.ProductDetailVo;
import com.zyx.payimooc.vo.ProductVo;
import com.zyx.payimooc.vo.ResponseVo;

import java.util.List;

public interface IProductService {

    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer PageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);

}
