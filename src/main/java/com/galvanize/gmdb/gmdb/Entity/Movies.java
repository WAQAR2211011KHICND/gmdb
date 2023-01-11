package com.galvanize.gmdb.gmdb.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    
    @Column( nullable = false)
    @Getter @Setter private String Title;

    @Column( nullable = false)
    @Getter @Setter private int ReleasedYear;
    
    @Column( nullable = false)
    @Getter @Setter private String Genre;
    
    @Column( nullable = false)
    @Getter @Setter private String Runtime;
    
    @OneToMany(mappedBy = "movies")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter @Setter private List<Reviews> reviews;
    
}
