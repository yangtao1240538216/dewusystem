package com.example.dewu.control;



import com.example.dewu.model.Notify;
import com.example.dewu.service.NotifyHelper;
import com.example.dewu.service.impl.DingDingHelper;
import com.example.dewu.service.impl.EnterpriseWechatHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotifyConsumer {
    private NotifyHelper dingDingHelper = new DingDingHelper();
    private NotifyHelper enterpriseWechatHelper = new EnterpriseWechatHelper();

    @KafkaListener(topics = {"dingDingNotify"})
    public void dingDinglistener(ConsumerRecord<?, ?> record) {
        // #2. 如果消息存在
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            // #3. 获取消息
            Notify message = (Notify) kafkaMessage.get();
            dingDingHelper.sendDingDingNotify(message);
        }
    }

    @KafkaListener(topics = {"wechatNotify"})
    public void wechatlistener(ConsumerRecord<?, ?> record) {
        // #2. 如果消息存在
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            // #3. 获取消息
            Notify message = (Notify) kafkaMessage.get();
            enterpriseWechatHelper.sendWechatNotify(message);
        }
    }
}
