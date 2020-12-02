package com.zyx.payimooc.service;

import com.zyx.payimooc.vo.CategoryVo;
import com.zyx.payimooc.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {
    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryById(Integer id, Set<Integer> resultSet);

}
