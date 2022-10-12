package com.example.dewu.service;

import com.example.dewu.model.PaymentRecord;
import com.example.dewu.param.PaymentRecordQueryParam;

import java.util.List;

public interface PaymentRecordService {
    /**
     * 添加或修改支付记录
     * @Param paymentRecord
     * @return PaymentRecord
     */
    PaymentRecord save(PaymentRecord paymentRecord);

    /**
     * 查询支付记录
     * @param queryParam 查询参数
     * @return List<PaymentRecord>
     */
    List<PaymentRecord> query(PaymentRecordQueryParam queryParam);

    /**
     * 更新状态
     * @param paymentRecord 支付记录
     * @return PaymentRecord
     */
    PaymentRecord updateStatus(PaymentRecord paymentRecord);
}
