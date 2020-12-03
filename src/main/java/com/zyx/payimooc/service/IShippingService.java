package com.zyx.payimooc.service;

import com.github.pagehelper.PageInfo;
import com.zyx.payimooc.form.ShippingForm;
import com.zyx.payimooc.vo.ResponseVo;

import java.util.Map;

public interface IShippingService {

    ResponseVo<Map<String, Integer>> add(Integer id, ShippingForm form);

    ResponseVo delete(Integer uid, Integer shippingId);

    ResponseVo update(Integer uid, Integer shippingId, ShippingForm form);

    ResponseVo<PageInfo> list(Integer id, Integer pageNum, Integer pageSize);


}
