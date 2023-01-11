package com.galvanize.gmdb.gmdb.Repository;

import org.springframework.data.repository.CrudRepository;

import com.galvanize.gmdb.gmdb.Entity.GmdbUser;

public interface UserRepository extends CrudRepository<GmdbUser,Long>{

}
