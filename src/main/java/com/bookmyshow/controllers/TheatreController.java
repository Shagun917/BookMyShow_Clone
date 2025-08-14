package com.bookmyshow.controllers;


import com.bookmyshow.enums.City;
import com.bookmyshow.models.movies.Movie;
import com.bookmyshow.models.theatre.Show;
import com.bookmyshow.models.theatre.Theatre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Theatre")
public class TheatreController {

    Map<City, List<Theatre>> cityVsTheatre;
    // Nagpur → [PVR, INOX]
    // Mumbai → [Cinepolis, Carnival]

    List<Theatre> allTheatre;
    //allTheatre = [PVR, INOX, Cinepolis, Carnival]

    public TheatreController() {
        cityVsTheatre = new HashMap<>();
        allTheatre = new ArrayList<>();
    }
    @PostMapping("/addTheatre")
    public void addTheatre(Theatre theatre, City city) {

        allTheatre.add(theatre);

        List<Theatre> theatres = cityVsTheatre.getOrDefault(city, new ArrayList<>());
        theatres.add(theatre);
        cityVsTheatre.put(city, theatres);
    }

    @GetMapping("/getAllShow")
    public Map<Theatre, List<Show>> getAllShow(Movie movie, City city) {

        //get all the theater of this city

        Map<Theatre, List<Show>> theatreVsShows = new HashMap<>();

        List<Theatre> theatres = cityVsTheatre.get(city);
        // theatre = [PVR, INOX]

        //filter the theatres which run this movie

        for(Theatre theatre : theatres) {

            List<Show> givenMovieShows = new ArrayList<>();
            List<Show> shows = theatre.getShows();
            // shows = [morning, evening]

            for(Show show : shows) {
                if(show.getMovie().getMovieId() == movie.getMovieId()) {
                    givenMovieShows.add(show);
                }
            }
            // givenMovieShows = [morning, evening]
            if(!givenMovieShows.isEmpty()) {
                theatreVsShows.put(theatre, givenMovieShows);
            }
        }

        return theatreVsShows;
    }
}
