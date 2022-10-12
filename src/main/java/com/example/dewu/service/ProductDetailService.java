package com.example.dewu.service;

import com.example.dewu.model.ProductDetail;

import java.util.List;

/**
 * @Author jiaoheng
 * @DATE 2020-07-13
 */
public interface ProductDetailService {
    /**
     * 添加或者删除商品详情
     * @param productDetail 商品详情
     * @return ProductDetail
     */
    ProductDetail save(ProductDetail productDetail);

    /**
     * 获取商品详情
     * @param productId 商品主键
     * @return List<ProductDetail>
     */
    List<ProductDetail> getByProductId(String productId);

    /**
     * 根据主键id查询订单详情信息
     * @param id 主键id
     * @return ProductDetail
     */
    ProductDetail get(String id);

    /**
     * 获取多个商品详情
     * @param productDetailIds 查询参数
     * @return List<ProductDetail>
     */
    public List<ProductDetail> queryProductDetail(List<String> productDetailIds);
}
