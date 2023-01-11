package com.galvanize.gmdb.gmdb.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Review id")
    @Getter @Setter private Long id;
    
    @Getter @Setter private String review;

    // @Temporal(TemporalType.DATE)
    // @Getter @Setter private Date Modified;     
    @Getter @Setter private String Modified;     

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Movies movies;
  
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "Reviewer_id")
    private GmdbUser Reviewer;
    
    
    public GmdbUser getReviewer() {
        return Reviewer;
    }

    public void setReviewer(GmdbUser reviewer) {
        Reviewer = reviewer;
    }

    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

    @JsonProperty("Movie_id")
    public Long getMovieId(){
        return this.movies.getId();
    }
    
    @JsonProperty("reviewer_id")
    public Long getReviewerId(){
        return this.Reviewer.getId();
    }


}
