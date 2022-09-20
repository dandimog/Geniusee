package com.geniusee.cinema;

import com.geniusee.cinema.models.movies.Movie;
import com.geniusee.cinema.models.movies.MovieRequest;
import com.geniusee.cinema.repositories.movies.IMovieRepository;
import com.geniusee.cinema.services.movies.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private IMovieRepository repository;

    @InjectMocks
    private MovieService service;

    @DisplayName("JUnit test for saveMovie() method success scenario")
    @Test
    public void whenSaveMovie_thenReturnMovieObject() {
        MovieRequest request = MovieRequest.builder()
                .title("title")
                .description("description")
                .duration(120)
                .build();
        Movie movie = Movie.builder()
                .id(1L)
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .build();
        when(repository.save(any(Movie.class))).thenReturn(movie);
        Movie savedMovie = service.saveMovie(request);
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getTitle()).isEqualTo(request.getTitle());
        assertThat(savedMovie.getDescription()).isEqualTo(request.getDescription());
        assertThat(savedMovie.getDuration()).isEqualTo(request.getDuration());
        verify(repository, times(1)).save(any(Movie.class));
    }

    @DisplayName("JUnit test for getMovieByID() method success scenario")
    @Test
    public void getMovieByID_thenReturnMovieObject(){
        long movieId = 1L;
        Movie movie = Movie.builder()
                .id(movieId)
                .title("title")
                .description("description")
                .duration(100)
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(movie));
        Movie savedMovie = service.getMovieByID(movieId);
        assertThat(savedMovie).isNotNull();
        verify(repository, times(1)).findById(movieId);
    }

    @DisplayName("JUnit test for getMovieByID() method failure scenario")
    @Test
    public void getMovieByID_thenThrowException(){
        long movieId = 1L;
        when(repository.findById(movieId)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.getMovieByID(movieId));
        verify(repository, times(1)).findById(movieId);
    }

    @DisplayName("JUnit test for updateMovie() method success scenario")
    @Test
    public void whenUpdateMovie_thenReturnUpdatedMovieObject(){
        long movieId = 1L;
        MovieRequest request = MovieRequest.builder()
                .title("newTitle")
                .description("newDescription")
                .duration(200)
                .build();
        Movie savedMovie = Movie.builder()
                .id(movieId)
                .title("title")
                .description("description")
                .duration(100)
                .build();
        Movie newMovie = Movie.builder()
                .id(movieId)
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .build();
        when(repository.findById(movieId)).thenReturn(Optional.of(savedMovie));
        when(repository.save(any(Movie.class))).thenReturn(newMovie);
        Movie updatedMovie = service.updateMovie(movieId, request);
        assertThat(updatedMovie.getId()).isEqualTo(movieId);
        assertThat(updatedMovie.getTitle()).isEqualTo(request.getTitle());
        assertThat(updatedMovie.getDescription()).isEqualTo(request.getDescription());
        assertThat(updatedMovie.getDuration()).isEqualTo(request.getDuration());
    }

    @DisplayName("JUnit test for updateMovie() method failure scenario")
    @Test
    public void whenUpdateMovie_thenThrowException(){
        long movieId = 1L;
        MovieRequest request = MovieRequest.builder()
                .title("newTitle")
                .description("newDescription")
                .duration(200)
                .build();
        when(repository.findById(movieId)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.updateMovie(movieId, request));
    }

    @DisplayName("JUnit test for deleteMovieByID() method success scenario")
    @Test
    public void whenDeleteMovie_thenNothing(){
        long movieId = 1L;
        doNothing().when(repository).deleteById(movieId);
        service.deleteMovieByID(movieId);
        verify(repository, times(1)).deleteById(movieId);
    }

    @DisplayName("JUnit test for deleteMovieByID() method failure scenario")
    @Test
    public void whenDeleteMovie_thenThrowException(){
        long movieId = 1L;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Error message")).when(repository).deleteById(movieId);
        assertThrows(ResponseStatusException.class, () -> service.deleteMovieByID(movieId));
        verify(repository, times(1)).deleteById(movieId);
    }
}
