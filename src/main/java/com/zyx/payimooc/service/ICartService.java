package com.zyx.payimooc.service;

import com.zyx.payimooc.form.CartAddForm;
import com.zyx.payimooc.form.CartUpdateForm;
import com.zyx.payimooc.vo.CartVo;
import com.zyx.payimooc.vo.ResponseVo;

public interface ICartService {

    ResponseVo<CartVo> add(Integer uid, CartAddForm form);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);


}
