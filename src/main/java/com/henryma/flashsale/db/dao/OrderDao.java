package com.henryma.flashsale.db.dao;

import com.henryma.flashsale.db.po.Order;

public interface OrderDao {

    public void insertOrder(Order order);

    public Order queryOrder(String orderNo);

    public void updateOrder(Order order);

}
