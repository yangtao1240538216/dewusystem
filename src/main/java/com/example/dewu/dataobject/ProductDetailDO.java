package com.example.dewu.dataobject;

import com.example.dewu.model.ProductDetail;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ProductDetailDO {
    /**
     * 主键
     */
    private String id;

    /**
     * 关联商品
     */
    private String productId;

    /**
     * 价格
     */
    private Double price;

    /**
     * 尺码
     */
    private Double size;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public ProductDetailDO() {

    }

    public ProductDetailDO(ProductDetail productDetail) {
        BeanUtils.copyProperties(productDetail, this);
    }

    public ProductDetail convertToModel() {
        ProductDetail productDetail = new ProductDetail();
        BeanUtils.copyProperties(this, productDetail);
        return productDetail;
    }
}