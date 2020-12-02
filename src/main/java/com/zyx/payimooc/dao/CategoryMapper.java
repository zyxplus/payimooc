package com.zyx.payimooc.dao;

import com.zyx.payimooc.pojo.Category;
import com.zyx.payimooc.vo.ResponseVo;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectAll();
}
