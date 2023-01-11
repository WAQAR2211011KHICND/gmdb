package com.galvanize.gmdb.gmdb.Entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public class GmdbUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter private String username;
    
    @Getter @Setter private String role;

    @Getter @Setter private Date joinDate;

    @JsonIgnore
    @OneToMany(mappedBy = "Reviewer")
    @Getter @Setter private List<Reviews> reviews;

    @JsonProperty(namespace = "reviewsCount",access = JsonProperty.Access.READ_ONLY)
    public Long getReviewCount(){
        return Long.valueOf( this.reviews.size() );
    }

}
