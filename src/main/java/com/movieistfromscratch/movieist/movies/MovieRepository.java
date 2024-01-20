package com.movieistfromscratch.movieist.movies;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
    // automatically comes with findById, but this searches via ObjectId. We want to search via imdbId instead, hence this method.
    Optional<Movie> findMovieByImdbId(String imdbId); // just by naming it in the convention find<class>By<property>, mongo already knows what to do
}
