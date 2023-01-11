package com.galvanize.gmdb.gmdb.Repository;


import org.springframework.data.repository.CrudRepository;

import com.galvanize.gmdb.gmdb.Entity.Movies;

public interface MovieRepository extends CrudRepository<Movies, Long> {
    
}
