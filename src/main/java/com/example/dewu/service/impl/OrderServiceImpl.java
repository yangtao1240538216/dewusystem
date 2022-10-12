package com.example.dewu.service.impl;

import com.example.dewu.dao.OrderDAO;
import com.example.dewu.model.*;
import com.example.dewu.util.UUIDUtils;
import com.example.dewu.dataobject.OrderDO;
import com.example.dewu.param.QueryOrderParam;
import com.example.dewu.service.OrderService;
import com.example.dewu.service.ProductDetailService;
import com.example.dewu.service.ProductService;
import com.example.dewu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    private DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @Override
    public Order add(Order order) {
        if (order == null) {
            return null;
        }
        order.setId(UUIDUtils.getUUID());
        order.setStatus(OrderStatus.WAIT_BUYER_PAY);
        //生成唯一订单号
        order.setOrderNumber(createOrderNumber());
        OrderDO orderDO = new OrderDO(order);
        int insert = orderDAO.insert(orderDO);
        if (insert == 1) {
            return order;
        }
        return null;
    }

    @Override
    public Order getByOrderNumber(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            return null;
        }
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO != null) {
            Order order = orderDO.convertToModel();
            return order;
        }
        return null;
    }

    @Override
    public Paging<Order> queryRecentPaySuccess(QueryOrderParam queryOrderParam) {
        Paging<Order> result = new Paging<>();

        if (queryOrderParam == null) {
            queryOrderParam = new QueryOrderParam();
        }

        int counts = orderDAO.selectCounts(queryOrderParam);

        if (counts < 1) {
            result.setTotalCount(0);
            return result;
        }
        List<OrderDO> orderDOS = orderDAO.pageQuery(queryOrderParam);
        List<Order> orders = new ArrayList<>();
        List<String> productDetailIds = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        for (OrderDO orderDO : orderDOS) {
            orders.add(orderDO.convertToModel());
            productDetailIds.add(orderDO.getProductDetailId());
            userIds.add(Long.parseLong(orderDO.getUserId()));
        }
        List<User> users = userService.queryUser(userIds);
        List<ProductDetail> productDetails = productDetailService.queryProductDetail(productDetailIds);
        Map<String, ProductDetail> productDetailMap = productDetails.stream().collect(
                Collectors.toMap(ProductDetail::getId, t -> t));
        Map<Long, User> userMap = users.parallelStream().collect(Collectors.toMap(User::getId, t -> t));

        for (Order order : orders) {
            order.setProductDetail(productDetailMap.get(order.getProductDetailId()));
            order.setUser(userMap.get(Long.parseLong(order.getUserId())));
        }
        result.setData(orders);
        result.setTotalCount(counts);
        result.setPageNum(orders.size());
        result.setPageSize(queryOrderParam.getPageSize());
        result.setTotalPage(orders.size() / queryOrderParam.getPageNum());
        return result;
    }

    @Override
    public Order updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        //更改订单状态
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO == null) {
            return null;
        }
        orderDO.setStatus(orderStatus.toString());
        orderDAO.update(orderDO);
        return orderDO.convertToModel();
    }

    @Override
    public Order updateProductPersonNumber(String orderNumber) {
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO == null) {
            return null;
        }
        //获取分布式锁
        RLock transferLock = redisson.getLock("PURCHASE");
        transferLock.lock();
        try {
            ProductDetail productDetail = productDetailService.get(orderDO.getProductDetailId());
            if (productDetail == null) {
                return null;
            }
            productDetail.setStock(productDetail.getStock() - 1);
            productDetailService.save(productDetail);
            Product product = productService.get(productDetail.getProductId());
            product.setPurchaseNum(product.getPurchaseNum() + 1);
            productService.save(product);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            transferLock.unlock();
        }
        return orderDO.convertToModel();
    }

    /**
     * 生成唯一订单号
     * @return String
     */
    private String createOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = dtf2.format(localDateTime);
        RAtomicLong aLong = redisson.getAtomicLong("myOrderPartNum" + date);
        aLong.expire(10, TimeUnit.SECONDS);
        long count = aLong.incrementAndGet();
        String orderId = date + count;
        return orderId;
    }
}
