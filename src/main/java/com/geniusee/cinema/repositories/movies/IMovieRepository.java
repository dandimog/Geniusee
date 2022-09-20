package com.geniusee.cinema.repositories.movies;

import com.geniusee.cinema.models.movies.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IMovieRepository extends PagingAndSortingRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {}
