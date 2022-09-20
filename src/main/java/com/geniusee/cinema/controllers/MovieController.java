package com.geniusee.cinema.controllers;

import com.geniusee.cinema.models.movies.MovieRequest;
import com.geniusee.cinema.models.movies.Movie;
import com.geniusee.cinema.services.movies.IMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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
    ResponseEntity<List<Movie>> getMoviesByCriteria(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String description,
                                                    @RequestParam(required = false) Integer duration,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "2") int size) {
        Pageable paging = PageRequest.of(page, size);
        Map<String, Object> queryParams = new HashMap<>();
        // TODO: they can be empty
        queryParams.put("title", title);
        queryParams.put("description", description);
        queryParams.put("duration", duration);
        return new ResponseEntity<>(movieService.getAllByCriteria(queryParams, paging), HttpStatus.OK);
    }

}
