package com.galvanize.gmdb.gmdb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvanize.gmdb.gmdb.Entity.Movies;
import com.galvanize.gmdb.gmdb.Entity.Reviews;
import com.galvanize.gmdb.gmdb.Repository.MovieRepository;
import com.galvanize.gmdb.gmdb.Repository.ReviewsRepository;
import com.galvanize.gmdb.gmdb.Repository.UserRepository;

import jakarta.websocket.server.PathParam;



@RestController
@RequestMapping("/Movie")
public class MovieController {
    
    @Autowired
    private MovieRepository movieRepository;

    @Autowired 
    private ReviewsRepository reviewsRepository;


    @Autowired
    private UserRepository reviewerRepository;

    
    // 1. As a user
    //    I can GET a list of movies from GMDB that includes Movie ID | Movie Title | Year Released | Genre | Runtime
    //    so that I can see the list of available movies.
    @GetMapping("/all")
    public ResponseEntity<Iterable<Movies>> getAllMovie(){
     return ResponseEntity.ok().body(movieRepository.findAll());   
    }


    
    // 2. As a user
    //    I can provide a movie ID and get back the record shown in story 1,
    //    plus a list of reviews that contains Review ID | Movie ID | Reviewer ID | Review Text | DateTime last modified
    //    so that I can read the reviews for a movie.
    //
    @GetMapping("/{id}")
    public ResponseEntity<Movies> getMovie(@PathVariable Long id){
        return ResponseEntity.ok().body(this.movieRepository.findById(id).get());
    }


    // @GetMapping("/Review/{id}")
    // public ResponseEntity<Reviews> getParticularReview(@PathVariable Long id){
    //     return ResponseEntity.ok().body(reviewsRepository.findById(id).get());
    // } 


    // @GetMapping("/Review/all")
    // public ResponseEntity<Iterable<Reviews>> getAllReview(){
    //     return ResponseEntity.ok().body(reviewsRepository.findAll());
    // }
}
