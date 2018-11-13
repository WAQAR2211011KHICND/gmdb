package com.galvanize.gmdb.gmdb;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MovieRepository repository;

    public MoviesController(MovieRepository repository){
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Movie> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Movie create(@RequestBody Movie movie) {
        return this.repository.save(movie);
    }
}

