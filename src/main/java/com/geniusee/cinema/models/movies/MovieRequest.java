package com.geniusee.cinema.models.movies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {
    @NotNull
    private String title;
    @NotBlank
    private String description;
    @Positive
    private int duration;
}
