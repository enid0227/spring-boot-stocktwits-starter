package com.example.stocktwitsdemo.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
  @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
  public UserEntity getUserByUsername(@Param("username") String username);
}
