package com.example.dewu.dao;

import com.example.dewu.dataobject.ProductDO;
import com.example.dewu.param.BasePageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDAO {
    int insert(ProductDO record);

    ProductDO selectByPrimaryKey(String id);

    List<ProductDO> selectAll();

    int selectAllCounts();

    List<ProductDO> pageQuery(BasePageParam param);

    int updateByPrimaryKey(ProductDO productDO);

    int deleteByPrimaryKey(String id);
}