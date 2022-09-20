package com.geniusee.cinema.repositories.specifications;

import com.geniusee.cinema.models.movies.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecs {
    public static Specification<Movie> titleIs(String titleName) {
        return (root, query, builder) -> builder.equal(root.get("title"), titleName);
    }

    public static Specification<Movie> descriptionIs(String description) {
        return (root, query, builder) -> builder.equal(root.get("description"), description);
    }

    public static Specification<Movie> durationIs(String durationInMins) {
        return (root, query, builder) -> builder.equal(root.get("duration"), durationInMins);
    }
}
