package com.Group11.SamePage.repositories;
import com.Group11.SamePage.Users.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u from User u WHERE u.username = :username and u.password = :password")
    User login(
            @Param("username") String username,
            @Param("password") String password);

    @Query("SELECT u from User u WHERE u.username = :username")
    User findByUsername(
            @Param("username") String username);


    @Query("SELECT u from User u WHERE u.id = :userID")
    User findByID(
            @Param("userID") Integer userID);
}

