package com.geniusee.cinema.models.movies;

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
public class Movie {

    public Movie(MovieRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.duration = request.getDuration();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_generator")
    @SequenceGenerator(name = "movie_generator", sequenceName = "movie_sequence", allocationSize = 1)
    private long id;
    private String title;
    private String description;
    private int duration;

    public void populateMovieWithRequestData(MovieRequest request) {
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
        this.setDuration(request.getDuration());
    }
}
