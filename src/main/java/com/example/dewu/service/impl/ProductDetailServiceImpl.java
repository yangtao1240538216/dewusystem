package com.example.dewu.service.impl;

import com.example.dewu.dao.ProductDetailDAO;
import com.example.dewu.model.ProductDetail;
import com.example.dewu.util.UUIDUtils;
import com.example.dewu.dataobject.ProductDetailDO;
import com.example.dewu.service.ProductDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailDAO productDetailDAO;

    @Override
    public List<ProductDetail> queryProductDetail(List<String> productDetailIds) {
        if (CollectionUtils.isEmpty(productDetailIds)) {
            return null;
        }
        List<ProductDetailDO> productDetailDOS = productDetailDAO.selectByIds(productDetailIds);
        List<ProductDetail> productDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(productDetailDOS)) {
            return productDetails;
        }
        for (ProductDetailDO productDetailDO : productDetailDOS) {
            productDetails.add(productDetailDO.convertToModel());
        }
        return productDetails;
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        if (StringUtils.isBlank(productDetail.getId())) {
            productDetail.setId(UUIDUtils.getUUID());
            productDetailDAO.insert(new ProductDetailDO(productDetail));
            return productDetail;
        }
        productDetailDAO.updateByPrimaryKey(new ProductDetailDO(productDetail));
        return productDetail;
    }

    @Override
    public ProductDetail get(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        ProductDetailDO productDetailDO = productDetailDAO.selectByPrimaryKey(id);
        if (productDetailDO == null) {
            return null;
        }
        return productDetailDO.convertToModel();
    }

    @Override
    public List<ProductDetail> getByProductId(String productId) {

        List<ProductDetail> productDetails = new ArrayList<>();
        if (StringUtils.isBlank(productId)) {
            return productDetails;
        }

        List<ProductDetailDO> productDetailDOS = productDetailDAO.selectByProductId(productId);
        if (CollectionUtils.isEmpty(productDetailDOS)) {
            return productDetails;
        }

        for (ProductDetailDO productDetailDO : productDetailDOS) {
            productDetails.add(productDetailDO.convertToModel());
        }

        return productDetails;
    }

}
