package com.geniusee.cinema.services.movies;

import com.geniusee.cinema.models.movies.MovieRequest;
import com.geniusee.cinema.models.movies.Movie;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IMovieService {
    Movie saveMovie(MovieRequest request);
    Movie getMovieByID(long id);
    Movie updateMovie(long id, MovieRequest movie);
    void deleteMovieByID(long id);
    List<Movie> getAllByCriteria(Map<String, String> queryParams, Pageable paging);
}
