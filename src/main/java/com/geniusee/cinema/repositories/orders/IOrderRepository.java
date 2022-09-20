package com.geniusee.cinema.repositories.orders;

import com.geniusee.cinema.models.orders.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IOrderRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order> {}
