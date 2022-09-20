package com.geniusee.cinema.services.orders;

import com.geniusee.cinema.models.movies.Movie;
import com.geniusee.cinema.models.orders.Order;
import com.geniusee.cinema.models.orders.OrderRequest;
import com.geniusee.cinema.repositories.orders.IOrderRepository;
import com.geniusee.cinema.services.movies.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService implements IOrderService {

    public final IOrderRepository orderRepository;
    public final IMovieService movieService;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IMovieService movieService) {
        this.orderRepository = orderRepository;
        this.movieService = movieService;
    }

    @Override
    public Order saveOrder(OrderRequest request) {
        Order order = orderRepository.save(new Order(request));
        Movie movie = movieService.getMovieByID(request.getMovieId());
        order.setMovie(movie);
        return order;
    }

    @Override
    public Order getOrderByID(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Cannot find the Order. No Order with id = %s was found.", id)));
    }

    @Override
    public Order updateOrder(long id, OrderRequest request) {
        try {
            Order order = new Order(request);
            order.setId(id);
            Movie movie = movieService.getMovieByID(request.getMovieId());
            order.setMovie(movie);
            return orderRepository.save(order);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Cannot update the Order. No Order with id = %s was found.", id));
        }

    }

    @Override
    public void deleteOrderByID(long id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Cannot delete the Order. No Order with id = %s was found.", id));
        }

    }
}
