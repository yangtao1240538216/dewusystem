package com.example.dewu.control;

import com.example.dewu.model.Order;
import com.example.dewu.model.PaymentRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Component
public class OrderConsumer {
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private RedissonClient redisson;
    @Autowired
    private RedisTemplate redisTemplate;

    // #1. 监听主题为topic的消息
    @KafkaListener(topics = {"dewuOrder"})
    public void dewuOrderListenr(ConsumerRecord<?, ?> record) {
        // #2. 如果消息存在
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            // #3. 获取消息
            Order message = (Order)kafkaMessage.get();
            Date date = message.getGmtCreated();
            String timeStr = buildTime(date);
            //获取分布式锁
            RLock transferLock = redisson.getLock("lock-order-"+timeStr);
            transferLock.lock();
            try {
                increaseOrderMoney("order-"+timeStr, message.getTotalPrice());
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                transferLock.unlock();
            }
        }
    }

    // #1. 监听主题为topic的消息
    @KafkaListener(topics = {"dewuPayment"})
    public void dewuPaymentlistenr(ConsumerRecord<?, ?> record) {
        // #2. 如果消息存在
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            // #3. 获取消息
            PaymentRecord message = (PaymentRecord)kafkaMessage.get();
            Date date = message.getGmtCreated();
            String timeStr = buildTime(date);
            //获取分布式锁
            RLock transferLock = redisson.getLock("lock-pay-"+timeStr);
            transferLock.lock();
            try {
                increaseOrderMoney("pay-"+timeStr, message.getAmount());
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                transferLock.unlock();
            }
        }
    }

    public void increaseOrderMoney(String key, Double i) {
        Double money = (Double)redisTemplate.opsForValue().get(key);
        if(money == null){
            redisTemplate.opsForValue().set(key, i);
        }else{
            redisTemplate.opsForValue().set(key, money + i);
        }
    }

    private String buildTime(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // 创建一个格式化方式
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 执行时间的格式化处理，得到期望格式的时间字符串
        String timeStr = df.format(localDate);
        return timeStr;
    }
}
