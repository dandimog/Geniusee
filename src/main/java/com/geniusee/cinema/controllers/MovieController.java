package com.geniusee.cinema.controllers;

import com.geniusee.cinema.models.movies.Movie;
import com.geniusee.cinema.models.movies.MovieRequest;
import com.geniusee.cinema.services.movies.IMovieService;
import com.geniusee.cinema.utils.ParamsExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/cinema")
public class MovieController {

    private final IMovieService movieService;

    @Autowired
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movies")
    ResponseEntity<Movie> createMovie(@RequestBody @Valid MovieRequest movie) {
        return new ResponseEntity<>(movieService.saveMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping("/movies/{id}")
    ResponseEntity<Movie> findMovieById(@PathVariable("id") long id) {
        return new ResponseEntity<>(movieService.getMovieByID(id), HttpStatus.OK);
    }

    @PutMapping("/movies/{id}")
    ResponseEntity<Movie> updateMovie(@PathVariable("id") long id, @RequestBody @Valid MovieRequest movie) {
        return new ResponseEntity<>(movieService.updateMovie(id, movie), HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    ResponseEntity<Void> deleteMovie(@PathVariable("id") long id) {
        movieService.deleteMovieByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/movies")
    ResponseEntity<List<Movie>> getMoviesByCriteria(@RequestParam Map<String, String> queryParams,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "2") int size) {
        return new ResponseEntity<>(
                movieService.getAllByCriteria(
                        ParamsExtractor.extractValidParamsForType(queryParams, Movie.class),
                        PageRequest.of(page, size)),
                HttpStatus.OK);
    }


}
