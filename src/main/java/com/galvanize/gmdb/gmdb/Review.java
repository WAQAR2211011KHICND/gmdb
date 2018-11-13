package com.galvanize.gmdb.gmdb;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity(name="REVIEWS")
public class Review {
    //Review ID | Movie ID | Reviewer ID | Review Text | DateTime last modified
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REVIEW_ID")
    private long reviewId;

    @Column(name="REVIEW_TEXT")
    private String reviewText;

    @Column(name="MOVIE_ID",insertable = false,updatable = false)
    private long movieId;

    @Column(name="REVIEWER_ID",insertable = false,updatable = false)
    private long reviewerId;

    @Column(columnDefinition = "date", name="LAST_MODIFIED")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastModified;

    @ManyToOne
    @JoinColumn(name="MOVIE_ID",referencedColumnName="MOVIE_ID")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="REVIEWER_ID",referencedColumnName="REVIEWER_ID")
    private Reviewer reviewer;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }
}
