package com.bookmyshow.models.movies;

import lombok.Getter;
import lombok.Setter;

public class Movie {

    @Setter
    @Getter
    int movieId;
    @Getter
    @Setter
    String movieName;
    int movieDurationInMinutes;

    public Movie(int i, String movieName, int movieDurationInMinutes) {
        this.movieId = i;
        this.movieName = movieName;
        this.movieDurationInMinutes = movieDurationInMinutes;
    }

    public int getMovieDuration() {
        return movieDurationInMinutes;
    }

    public void setMovieDuration(int movieDuration) {
        this.movieDurationInMinutes = movieDuration;
    }
}