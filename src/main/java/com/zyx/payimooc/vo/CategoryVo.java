package com.zyx.payimooc.vo;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class CategoryVo {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    private List<CategoryVo> subCategories;
}
