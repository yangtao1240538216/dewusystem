package com.example.dewu.api;

import com.example.dewu.dao.ProductDAO;
import com.example.dewu.dao.UserDAO;
import com.example.dewu.model.*;
import com.example.dewu.param.QueryOrderParam;
import com.example.dewu.service.NotifyHelper;
import com.example.dewu.service.OrderService;
import com.example.dewu.service.impl.DingDingHelper;
import com.example.dewu.service.impl.EnterpriseWechatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/api/order")
public class OrderApi {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private OrderService orderService;
    @Autowired
    private KafkaTemplate<String, Order> kafkaOrderTemplate;
    @Autowired
    private KafkaTemplate<String, Notify> kafkaNotifyTemplate;

    /**
     * 生成订单Api
     * @return Result
     */
    @PostMapping(path = "/add")
    @ResponseBody
    public Result<Order> payOrder(@RequestBody Order order) {
        Result<Order> result = new Result();
        result.setSuccess(true);
        if (order == null) {
            result.setSuccess(false);
            result.setMessage("order is null");
            return result;
        }
        Order orderResult = orderService.add(order);
        result.setData(orderResult);
        kafkaNotifyTemplate.send("dingDingNotify", getDingDingTextNotify(order));
        //kafkaNotifyTemplate.send("wechatNotify", getEnterpriseWechatTextNotify(order));
        kafkaOrderTemplate.send("dewuOrder" ,order);
        return result;
    }

    /**
     * 查询支付成功订单APi
     * @return Result
     */
    @GetMapping(path = "/queryrecentpaysuccess")
    @ResponseBody
    public Result<Paging<Order>> queryRecentPaySuccess(@RequestBody QueryOrderParam queryOrderParam) {
        Result<Paging<Order>> result = new Result();
        result.setSuccess(true);
        if (queryOrderParam == null) {
            result.setSuccess(false);
            result.setMessage("queryOrderParam is null");
            return result;
        }
        result.setData(orderService.queryRecentPaySuccess(queryOrderParam));
        return result;
    }

    public Notify getDingDingTextNotify(Order order) {
        Notify notify = new Notify();
        notify.setNotifyType("text");
        notify.setNotifyPlatform(NotifyPlatform.DINGDING);
        List<Long> ids = new ArrayList<>();
        ids.add(Long.valueOf(order.getUserId()));
        notify.setText(userDAO.findByIds(ids).get(0).getUserName()+" 下单 "+productDAO.selectByPrimaryKey(order.getProductDetailId()).getName());
        return notify;
    }

    public Notify getEnterpriseWechatTextNotify(Order order) {
        Notify notify = new Notify();
        notify.setNotifyType("text");
        notify.setNotifyPlatform(NotifyPlatform.DINGDING);
        List<Long> ids = new ArrayList<>();
        ids.add(Long.valueOf(order.getUserId()));
        notify.setText(userDAO.findByIds(ids).get(0).getUserName()+" 下单 "+productDAO.selectByPrimaryKey(order.getProductDetailId()).getName());
        return notify;
    }
}
