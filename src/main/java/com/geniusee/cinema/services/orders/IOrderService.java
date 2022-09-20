package com.geniusee.cinema.services.orders;

import com.geniusee.cinema.models.orders.Order;
import com.geniusee.cinema.models.orders.OrderRequest;

public interface IOrderService {
    Order saveOrder(OrderRequest request);
    Order getOrderByID(long id);
    Order updateOrder(long id, OrderRequest movie);
    void deleteOrderByID(long id);
}
