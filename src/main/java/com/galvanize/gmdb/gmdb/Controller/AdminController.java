package com.galvanize.gmdb.gmdb.Controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvanize.gmdb.gmdb.Entity.GmdbUser;
import com.galvanize.gmdb.gmdb.Entity.Movies;
import com.galvanize.gmdb.gmdb.Repository.MovieRepository;
import com.galvanize.gmdb.gmdb.Repository.UserRepository;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private UserRepository adminRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping("/Register")
    public ResponseEntity<GmdbUser> registerAdmin(@RequestBody GmdbUser reviewer){
        reviewer.setRole("Admin");
        reviewer.setReviews(new ArrayList<>());
        reviewer.setJoinDate(new Date());
        return ResponseEntity.ok().body(this.adminRepository.save(reviewer));
    }

    
    // 8. As an Admin
    //    I can add a new movie to the database by providing the data listed in story 1 (Movie ID should be autogenerated)
    //    so that I can share new movies with the users.
    @PostMapping("/Movie/add")
    public ResponseEntity<Movies> addMovie(@RequestBody Movies movies){   
        movies.setReviews(new ArrayList<>());
        return ResponseEntity.ok().body(movieRepository.save(movies));
    }

    // 9. As an Admin
    //    I can add update the entry for a movie by providing the data listed in Story 1.
    //    so that I can correct errors in previously uploaded movie entries.
    @PutMapping("/Movie/{id}/update")
    public ResponseEntity<Movies> updateMovie(@PathVariable Long id, @RequestBody Movies movies){ 
        movies.setReviews(this.movieRepository.findById(id).get().getReviews());
        movies.setId(id);  
        
        return ResponseEntity.ok().body(movieRepository.save(movies));
    }

    //10. As an admin
    //    I can delete a movie by providing a movie ID
    //    so that I can remove movies I no longer wish to share.
    @DeleteMapping("/Movie/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        try {
            movieRepository.deleteById(id);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
}
