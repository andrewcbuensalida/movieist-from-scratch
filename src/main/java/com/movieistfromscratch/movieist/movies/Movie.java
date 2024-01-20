package com.movieistfromscratch.movieist.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "movies") // let's mongodb know this is a collection
@Data // takes care of getters/setters/toString methods
@AllArgsConstructor // requires all the properties to be initialized when doing new
@NoArgsConstructor // if allows object to be initialized without arguments, it puts default values
public class Movie {
    @Id // lets mongodb know this is the unique id for each movie
    private ObjectId id;
    private String imdbId;
    private String title;
    private String releaseDate;
    private String trailerLink;
    private String poster;
    private List<String> backdrops;
    private List<String> genres;
    @DocumentReference // only saves the foreign key, not the actual review in mongodb, but in postman gets the whole review. reviews will be in a separate collection.
    private List<Review> reviews; // this probably has to be reviewId

    public Movie(String imdbId, String title, String releaseDate, String trailerLink, String poster, List<String> backdrops, List<String> genres) {
        this.imdbId = imdbId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.trailerLink = trailerLink;
        this.poster = poster;
        this.backdrops = backdrops;
        this.genres = genres;
    }
}
