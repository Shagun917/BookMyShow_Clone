package com.bookmyshow.controllers;

import com.bookmyshow.enums.City;
import com.bookmyshow.models.movies.Movie;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final Map<City, List<Movie>> cityVsMovies = new HashMap<>();
    private final List<Movie> allMovies = new ArrayList<>();

    @PostMapping("/add")
    public void addMovie(@RequestBody Movie movie, @RequestParam City city) {
        allMovies.add(movie);
        cityVsMovies.computeIfAbsent(city, k -> new ArrayList<>()).add(movie);
    }

    // Get movie by name
    @GetMapping("/getByName")
    public Movie getMovieByName(@RequestParam String movieName) {
        for (Movie movie : allMovies) {
            if (movie.getMovieName().equals(movieName)) {
                return movie;
            }
        }
        return null;
    }

    // Get movies by city
    @GetMapping("/byCity")
    public List<Movie> getMoviesByCity(@RequestParam City city) {
        return cityVsMovies.getOrDefault(city, new ArrayList<>());
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable int id) {
        for (Movie movie : allMovies) {
            if (movie.getMovieId() == id) {
                return movie;
            }
        }
        return null;
    }

    // ✅ Update movie by ID
    @PutMapping("/update/{id}")
    public String updateMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        for (int i = 0; i < allMovies.size(); i++) {
            Movie current = allMovies.get(i);
            if (current.getMovieId() == id) {
                allMovies.set(i, updatedMovie);

                // Also update in cityVsMovies
                for (Map.Entry<City, List<Movie>> entry : cityVsMovies.entrySet()) {
                    List<Movie> movieList = entry.getValue();
                    for (int j = 0; j < movieList.size(); j++) {
                        if (movieList.get(j).getMovieId() == id) {
                            movieList.set(j, updatedMovie);
                        }
                    }
                }

                return "Movie updated successfully";
            }
        }
        return "Movie not found";
    }

    // ✅ Delete movie by ID
    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable int id) {
        Movie toRemove = null;
        for (Movie movie : allMovies) {
            if (movie.getMovieId() == id) {
                toRemove = movie;
                break;
            }
        }

        if (toRemove == null) return "Movie not found";

        allMovies.remove(toRemove);

        // Also remove from cityVsMovies
        for (List<Movie> movies : cityVsMovies.values()) {
            movies.removeIf(movie -> movie.getMovieId() == id);
        }

        return "Movie deleted successfully";
    }
}
