package com.galvanize.gmdb.gmdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.gmdb.Controller.MovieController;
import com.galvanize.gmdb.gmdb.Controller.ReviewerController;
import com.galvanize.gmdb.gmdb.Entity.GmdbUser;
import com.galvanize.gmdb.gmdb.Entity.Movies;
import com.galvanize.gmdb.gmdb.Entity.Reviews;
import com.galvanize.gmdb.gmdb.Repository.MovieRepository;
import com.galvanize.gmdb.gmdb.Repository.ReviewsRepository;
import com.galvanize.gmdb.gmdb.Repository.UserRepository;
import com.jayway.jsonpath.Option;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class GmdbApplicationTests {

	// Stories for this project are shown below in order of value, with the highest value listed first.
    // This microservice will contain the CRUD operations required to interact with the GMDB movie database.
    // Other functionality (e.g. user authentication) is hosted in other microservices.
	private MockMvc mvc;

    private MockMvc mvc2;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository reviewerRepository;

    @Mock 
    private ReviewsRepository reviewsRepository;


    @InjectMocks
    private ReviewerController reviewerController;

    @InjectMocks
    private MovieController movieController;

    private JacksonTester<Movies> jsonMovie;
    private JacksonTester<Iterable<Movies>> jsonMovies;

    private JacksonTester<Reviews> jsonReview;
    private JacksonTester<Iterable<Reviews>> jsonReviews;
    
    private JacksonTester<GmdbUser> jsonReviewer;
    private JacksonTester<Iterable<GmdbUser>> jsonReviewers;
    
    @BeforeEach
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
		mvc  = MockMvcBuilders.standaloneSetup(movieController).build();
		mvc2 = MockMvcBuilders.standaloneSetup(reviewerController).build();
	}


    @Test
	public void contextLoads() {
	}
    
    // 1. As a user
    //    I can GET a list of movies from GMDB that includes Movie ID | Movie Title | Year Released | Genre | Runtime
    //    so that I can see the list of available movies.
    @Test
	public void canCreateANewBook() throws Exception {
		Movies movies1 = new Movies("let us c",2000, "Romance", "120");
		Movies movies2 = new Movies("let us c",2010, "Dramatic", "180");

        List<Movies> AllMovies = new ArrayList<>();
        AllMovies.add(movies1);
        AllMovies.add(movies2);
        when(movieRepository.findAll()).thenReturn( AllMovies );
		mvc.perform(get("/Movie/all")
				.contentType(MediaType.APPLICATION_JSON)
                )
				.andExpect(status().isOk())
				.andExpect(content().json(jsonMovies.write(AllMovies).getJson()));
	}

    // 2. As a user
    //    I can provide a movie ID and get back the record shown in story 1,
    //    plus a list of reviews that contains Review ID | Movie ID | Reviewer ID | Review Text | DateTime last modified
    //    so that I can read the reviews for a movie.
    @Test
    public void getRecord()throws Exception{
		
        Movies movies1 = new Movies("let us c",2000, "Romance", "120");
        // Reviews reviews = new Reviews();
        // movies1.setReviews(null);
         
        when(movieRepository.findById(1L)).thenReturn( Optional.ofNullable(movies1) );
        mvc.perform(get("/Movie/1")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(jsonMovie.write(movies1).getJson()));

    }

    // 3. As a user
    //    I can provide a Reviewer ID and get back a record that contains Reivewer ID | Username | Date Joined | Number of Reviews
    //    so that I can see details about a particular reviewer.
    @Test 
    public void getReviewer()throws Exception{

        Date todayDate = new Date();
        GmdbUser reviewer = new GmdbUser("Muhammad Waqar", "Reviewer", todayDate);
        reviewer.setReviews(new ArrayList<>());
        when(reviewerRepository.findById(1L)).thenReturn(Optional.of(reviewer));
        
        mvc2.perform(
            get("/Reviewer/1").contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(jsonReviewer.write(reviewer).getJson()));
    }

    // 4. As a user
    //    I can register as a reviewer by providing my Username. (Reviewer ID should be autogenerated)
    //    So that I can start reviewing movies.
    @Test
    public void registerUSer() throws IOException, Exception{
        GmdbUser reviewer = new GmdbUser("Muhammad Waqar", "", new Date());        
        when(reviewerRepository.save(reviewer)).thenReturn(reviewer);
        reviewer.setReviews(new ArrayList<>());
        System.out.println(new Date());
        mvc2.perform(post("/Reviewer/Register")
				.content(jsonReviewer.write(reviewer).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                
        )
        .andExpect(status().isOk());
    }

    // 5. As a reviewer
    //    I can post a review by providing my reviewer ID, 
    //    a movie ID and my review text. 
    //    (Review ID should be autogenerated)
    //    So that I can share my opinions with others.
    @Test
    public void provideReview()throws Exception{
        Reviews reviews= new Reviews("Thats Movie is Fab..!");

		Movies movies2 = new Movies("let us c",2010, "Dramatic", "180");
        GmdbUser reviewer = new GmdbUser("Muhammad Waqar", "Reviewer", new Date());
        reviewer.setId(1L);
        reviewer.setReviews(new ArrayList<>()); 
        
        
        reviews.setMovies(movies2);
        reviews.setReviewer(reviewer);
        reviews.setModified(new Date().toString());

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movies2));
        when(reviewerRepository.findById(1L)).thenReturn(Optional.of(reviewer));
        when(reviewsRepository.save(reviews)).thenReturn(reviews);

        mvc2.perform(
        post("/Reviewer/1/Movie/1/Review")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonReview.write(reviews).getJson())
        )
        .andExpect(status().isOk());

        
    }

    // 6. As a reviewer
    //    I can delete a review by providing my reviewer ID and a review ID
    @Test
    public void deleteReview() throws Exception{
        
        doNothing().when(reviewsRepository).deleteById(1L);
        mvc2.perform(
            delete("/Reviewer/1/review/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
        
        doThrow(new RuntimeException()).when(reviewsRepository).deleteById(2L);
        mvc2.perform(
            delete("/Reviewer/1/review/2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
            
            

    }

    // 7. As a reviewer
    //    I can update a review by providing my reviewer ID, a movie ID and my review text.
    //    So that I can modify the opinion I'm sharing with others.
    // @Test
    // public void updateReview() throws Exception{
        
    //     // doNothing().when(reviewsRepository).deleteById(1L);

    //     Reviews reviews= new Reviews("Thats Movie is Fab..!");
    //     reviews.setId(1L);
        
	// 	Movies movies2 = new Movies("let us c",2010, "Dramatic", "180");
    //     movies2.setId(1L);

    //     movies2.setReviews(List.of(reviews));

    //     GmdbUser reviewer = new GmdbUser("Muhammad Waqar", "Reviewer", new Date());
    //     reviewer.setId(1L);


    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movies2));
    //     when(reviewerRepository.findById(1L)).thenReturn(Optional.of(reviewer));
    //     when(reviewsRepository.save(reviews)).thenReturn(reviews);

    //     mvc2.perform(
    //         put("/Reviewer/1?movieId=1")
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(jsonReview.write(reviews).getJson()))
    //         .andExpect(status().isAccepted());

    // }

    // 8. As an Admin
    //    I can add a new movie to the database by providing the data listed in story 1 (Movie ID should be autogenerated)
    //    so that I can share new movies with the users.
    // 9. As an Admin
    //    I can add update the entry for a movie by providing the data listed in Story 1.
    //    so that I can correct errors in previously uploaded movie entries.
    //
    //10. As an admin
    //    I can delete a movie by providing a movie ID
    //    so that I can remove movies I no longer wish to share.
    //
    //11. As an admin
    //    I can impersonate a reviewer and do any of the things they can do
    //    so that I can help confused reviewers.



}
