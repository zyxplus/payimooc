package com.zyx.payimooc.service;

import com.zyx.payimooc.vo.CategoryVo;
import com.zyx.payimooc.vo.ResponseVo;

import java.util.List;

public interface ICategoryService {
    ResponseVo<List<CategoryVo>> selectAll();
}
