package com.zyx.payimooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyx.payimooc.dao.ProductMapper;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.pojo.Product;
import com.zyx.payimooc.service.ICategoryService;
import com.zyx.payimooc.service.IProductService;
import com.zyx.payimooc.vo.ProductDetailVo;
import com.zyx.payimooc.vo.ProductVo;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zyx.payimooc.enums.ProductStatusEnum.DELETE;
import static com.zyx.payimooc.enums.ProductStatusEnum.OFF_SALE;

@Service
@Slf4j
public class IProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryById(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);

        List<ProductVo> productVoList = productList.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }


    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        if (product.getStatus().equals(OFF_SALE.getCode())
                || product.getStatus().equals(DELETE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        //隐藏库存信息
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
