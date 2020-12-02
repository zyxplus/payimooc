package com.zyx.payimooc.service.impl;

import com.zyx.payimooc.dao.CategoryMapper;
import com.zyx.payimooc.pojo.Category;
import com.zyx.payimooc.service.ICategoryService;
import com.zyx.payimooc.vo.CategoryVo;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zyx.payimooc.consts.MallConst.ROOT_PARENT_ID;

@Service
@Slf4j
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {

        List<Category> categories = categoryMapper.selectAll();

        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            if (category.getParentId().equals(ROOT_PARENT_ID)){
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category, categoryVo);
                categoryVoList.add(categoryVo);
            }
        }
        categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());

        findSubCategory(categoryVoList, categories);

        return ResponseVo.success(categoryVoList);

    }


    /**
     * 查询子目录
     * @param categoryVoList 父级目录
     * @param categories 类别
     */
    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {

        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> currentList = new ArrayList<>();
            for (Category category : categories) {
                if (categoryVo.getId().equals(category.getParentId())) {
                    currentList.add(Category2CategoryVo(category));
                }
            }
//            currentList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategories(currentList);
            findSubCategory(currentList, categories);
            log.info(categoryVo.getName() + "===>" + categoryVo.getSubCategories().toString());
        }


    }


    @Override
    public void findSubCategoryById(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategory(id, resultSet, categories);
    }

    public void findSubCategory(Integer id, Set<Integer> resultSet, List<Category> categories) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubCategory(category.getId(), resultSet, categories);
            }
        }
    }



        private CategoryVo Category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }


}
