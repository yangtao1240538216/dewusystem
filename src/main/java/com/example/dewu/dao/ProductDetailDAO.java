package com.example.dewu.dao;

import com.example.dewu.dataobject.ProductDetailDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDetailDAO {
    int insert(ProductDetailDO record);

    ProductDetailDO selectByPrimaryKey(String id);

    List<ProductDetailDO> selectAll();

    List<ProductDetailDO> selectByProductId(String productId);

    List<ProductDetailDO> selectByIds(List<String> ids);

    int updateByPrimaryKey(ProductDetailDO record);

    int deleteByPrimaryKey(String id);
}
