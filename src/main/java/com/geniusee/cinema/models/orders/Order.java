package com.geniusee.cinema.models.orders;

import com.geniusee.cinema.models.movies.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Order {

    public Order(OrderRequest request) {
        this.movie = Movie.builder().id(request.getMovieId()).build();
        this.price = request.getPrice();
        this.seatNumber = request.getSeatNumber();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
    @SequenceGenerator(name = "order_generator", sequenceName = "order_sequence", allocationSize = 1)
    private long id;
    @ManyToOne()
    private Movie movie;
    private int price;
    private int seatNumber;
}
