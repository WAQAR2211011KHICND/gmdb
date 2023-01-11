package com.galvanize.gmdb.gmdb.Repository;

import org.springframework.data.repository.CrudRepository;

import com.galvanize.gmdb.gmdb.Entity.Reviews;

public interface ReviewsRepository extends CrudRepository<Reviews, Long> {
    
}
