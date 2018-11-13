package com.galvanize.gmdb.gmdb;

import javax.persistence.*;

import java.util.Collection;


@Entity(name="MOVIES")
public class Movie {
    // Movie ID | Movie title | year Released | genre | runtime

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MOVIE_ID")
    private long movieId;

    @Column(name="TITLE")
    private String title;

    @Column(name="YEAR")
    private String year;

    @Column(name="GENRE")
    private String genre;

    @Column(name="RUNTIME")
    private Long runtime; //expressed in minutes

    @OneToMany(mappedBy="movie",targetEntity=Review.class)
    private Collection reviews;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Collection getReviews() {
        return reviews;
    }

    public void setReviews(Collection reviews) {
        this.reviews = reviews;
    }
}
