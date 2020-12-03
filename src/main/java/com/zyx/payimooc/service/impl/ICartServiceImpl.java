package com.zyx.payimooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.zyx.payimooc.dao.ProductMapper;
import com.zyx.payimooc.enums.ProductStatusEnum;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.form.CartAddForm;
import com.zyx.payimooc.form.CartUpdateForm;
import com.zyx.payimooc.pojo.Cart;
import com.zyx.payimooc.pojo.Product;
import com.zyx.payimooc.service.ICartService;
import com.zyx.payimooc.service.IProductService;
import com.zyx.payimooc.vo.CartProductVo;
import com.zyx.payimooc.vo.CartVo;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ICartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Gson gson = new Gson();


    /**
     * 添加商品到购物车
     * @param form
     * @return
     */
    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm form) {
        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        //商品不存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //下架状态
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //库存不足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //写入到redis
        Cart cart;

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        if (StringUtils.isEmpty(value)) {
            //新增
            cart = new Cart(product.getId(), quantity, form.getSelected());
        } else {
            //加quantity(1)
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(
                redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart));

        return list(uid);
    }


    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectALl = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;

        //查询购物车里面所有product（by productId）
        Set<Integer> productIdSet = new HashSet<>();
        Set<String> stringSet = entries.keySet();

        for (String key : stringSet) {
            Integer productId = Integer.valueOf(key);
            productIdSet.add(productId);
        }

        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        //查询购物车里面所有product => 结束

        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        //查询购物车里每件商品的详情并转换成CartProductVo
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            cartTotalQuantity += cart.getQuantity();
            for (Product product : productList) {
                if (product.getId().equals(productId)) {
                    CartProductVo cartProductVo = new CartProductVo(productId,
                            cart.getQuantity(),
                            product.getName(),
                            product.getSubtitle(),
                            product.getMainImage(),
                            product.getPrice(),
                            product.getStatus(),
                            product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                            product.getStock(),
                            cart.getProductSelected()
                    );
                    cartProductVoList.add(cartProductVo);

                    //全选按钮是否生效
                    if (!cart.getProductSelected()) {
                        selectALl = false;
                    }
                    //选中后，商品金额累加
                    if (cart.getProductSelected()) {
                        BigDecimal price = cartProductVo.getProductTotalPrice();
                        cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                    }
                }
            }

        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setSelectedAll(selectALl);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);

        return ResponseVo.success(cartVo);
    }


    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //是否修改数量
        Cart cart = gson.fromJson(value, Cart.class);
        if (form.getQuantity() != null && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }
        //是否选中
        if (form.getSelected() != null) {
            cart.setProductSelected(form.getSelected());
        }
        //在redis中更新
        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }


    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //在redis中删除
        opsForHash.delete(redisKey, String.valueOf(productId));

        return list(uid);
    }


    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    private List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> cartList = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            cartList.add(cart);
        }

        return cartList;

    }

}
