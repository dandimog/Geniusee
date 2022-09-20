package com.geniusee.cinema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geniusee.cinema.controllers.MovieController;
import com.geniusee.cinema.models.movies.MovieRequest;
import com.geniusee.cinema.models.movies.Movie;
import com.geniusee.cinema.services.movies.IMovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    // TODO: Error cases with ApiError Response

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMovieService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void whenSaveMovie_thenReturnSavedMovie() throws Exception {

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

        when(service.saveMovie(any(MovieRequest.class))).thenReturn(movie);

        this.mockMvc
                .perform(post("/cinema/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) movie.getId())))
                .andExpect(jsonPath("$.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.description", is(movie.getDescription())))
                .andExpect(jsonPath("$.duration", is(movie.getDuration())));
    }

    @Test
    public void whenGetMovieById_thenReturnMovieObject() throws Exception {

        long movieId = 1L;

        Movie movie = Movie.builder()
                .id(movieId)
                .title("title")
                .description("description")
                .duration(120)
                .build();

        when(service.getMovieByID(movieId)).thenReturn(movie);

        this.mockMvc
                .perform(get("/cinema/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is((int) movie.getId())))
                .andExpect(jsonPath("$.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.description", is(movie.getDescription())))
                .andExpect(jsonPath("$.duration", is(movie.getDuration())));
    }

    @Test
    public void whenGetMovieById_thenReturnUpdatedMovieObject() throws Exception {

        long movieId = 1;

        Movie savedMovie = Movie.builder()
                .id(movieId)
                .title("oldTitle")
                .description("OldDescription")
                .duration(100)
                .build();

		MovieRequest request = MovieRequest.builder()
				.title("newTitle")
				.description("newDescription")
				.duration(200)
				.build();

        Movie updatedMovie = Movie.builder()
                .id(movieId)
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .build();

        when(service.getMovieByID(movieId)).thenReturn(savedMovie);
        when(service.updateMovie(eq(movieId), any(MovieRequest.class))).thenReturn(updatedMovie);

        mockMvc.perform(put("/cinema/movies/{id}", movieId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is((int) updatedMovie.getId())))
                .andExpect(jsonPath("$.title", is(updatedMovie.getTitle())))
                .andExpect(jsonPath("$.description", is(updatedMovie.getDescription())))
                .andExpect(jsonPath("$.duration", is(updatedMovie.getDuration())));
    }

    @Test
    public void whenDeleteMovie_thenReturnStatusCode200() throws Exception {

        long movieId = 1L;

        doNothing().when(service).deleteMovieByID(movieId);

        this.mockMvc.perform(delete("/cinema/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
