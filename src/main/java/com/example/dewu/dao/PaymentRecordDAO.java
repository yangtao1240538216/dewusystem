package com.example.dewu.dao;

import com.example.dewu.dataobject.PaymentRecordDO;
import com.example.dewu.param.PaymentRecordQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentRecordDAO {
    int insert(PaymentRecordDO record);

    List<PaymentRecordDO> select(PaymentRecordQueryParam paymentRecordQueryParam);

    int update(PaymentRecordDO record);
}