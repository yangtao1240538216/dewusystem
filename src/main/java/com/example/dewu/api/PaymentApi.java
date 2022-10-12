package com.example.dewu.api;

import com.example.dewu.model.Order;
import com.example.dewu.model.PaymentRecord;
import com.example.dewu.model.PaymentStatus;
import com.example.dewu.model.Result;
import com.example.dewu.param.PaymentParam;
import com.example.dewu.service.PayService;
import com.example.dewu.service.PaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/alipay")
public class PaymentApi {
    private static final Logger logger = LoggerFactory.getLogger(PaymentApi.class);

    @Autowired
    private PayService payService;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private KafkaTemplate<String, PaymentRecord> kafkaPaymentRecordTemplate;

    @PostMapping(path = "/pay")
    @ResponseBody
    public Result payOrder(@RequestBody PaymentParam paymentParam) {
        Result result = new Result();
        result.setSuccess(false);
        if (StringUtils.isEmpty(paymentParam.getUserId())) {
            result.setMessage("userId is null");
            return result;
        }
        if (paymentParam.getPayAmount() <= 0) {
            result.setMessage("支付金额不能小于0");
            return result;
        }
        result.setSuccess(true);
        PaymentRecord paymentRecord = initPaymentRecord(paymentParam);
        PaymentRecord saveResult = paymentRecordService.save(paymentRecord);
        if (saveResult == null) {
            logger.error("支付流水记录插入失败！");
        }else {
            kafkaPaymentRecordTemplate.send("dewuPayment" , saveResult);
        }
        return payService.payOrder(paymentParam);
    }

    /**
     * 组装支付记录
     * @param paymentParam paymentParam
     * @return PaymentRecord
     */
    private PaymentRecord initPaymentRecord(PaymentParam paymentParam) {
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setPayType(paymentParam.getPayType());
        paymentRecord.setPayStatus(PaymentStatus.PENDING);
        paymentRecord.setUserId(paymentParam.getUserId());
        paymentRecord.setOrderNumber(paymentParam.getOrderNumber());
        paymentRecord.setAmount(paymentParam.getPayAmount());
        return paymentRecord;
    }
}
