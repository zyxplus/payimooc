package com.zyx.payimooc.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;


}
