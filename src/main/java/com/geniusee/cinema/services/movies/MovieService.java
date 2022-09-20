package com.geniusee.cinema.services.movies;

import com.geniusee.cinema.models.movies.MovieRequest;
import com.geniusee.cinema.models.movies.Movie;
import com.geniusee.cinema.repositories.movies.IMovieRepository;
import com.geniusee.cinema.repositories.specifications.MovieSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class MovieService implements IMovieService {

    public final IMovieRepository repository;

    @Autowired
    public MovieService(IMovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movie saveMovie(MovieRequest request) {
        return repository.save(new Movie(request));
    }

    @Override
    public Movie getMovieByID(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Cannot find the movie. No movie with id = %s was found.", id)));
    }

    @Override
    public Movie updateMovie(long id, MovieRequest request) {
        try {
            Movie movie = this.getMovieByID(id);
            movie.populateMovieWithRequestData(request);
            return repository.save(movie);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Cannot update the movie. No movie with id = %s was found.", id));
        }

    }

    @Override
    public void deleteMovieByID(long id) {
        try {
            repository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Cannot delete the movie. No movie with id = %s was found.", id));
        }

    }

    @Override
    public List<Movie> getAllByCriteria(Map<String, Object> queryParams, Pageable paging) {
        System.out.println(queryParams.isEmpty());
        if (queryParams.isEmpty()) {
            return repository.findAll(paging).getContent();
        } else {
            Specification<Movie> spec = null;
            // TODO: bug
            System.out.println(queryParams.get("title"));
            if (queryParams.get("title") != null) {
                spec = (MovieSpecs.titleIs((String) queryParams.get("title")));
            }
            System.out.println(queryParams.get("description"));
            if (queryParams.get("description") != null) {
                spec = spec.and(MovieSpecs.descriptionIs((String) queryParams.get("description")));
            }
            System.out.println(queryParams.get("duration"));
            if (queryParams.get("duration") != null) {
                spec = spec.and(MovieSpecs.durationIs(String.valueOf(queryParams.get("duration"))));
            }
            return repository.findAll(spec, paging).getContent();
        }
    }
}
