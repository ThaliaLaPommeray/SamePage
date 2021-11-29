package com.Group11.SamePage.repositories;


import com.Group11.SamePage.Books.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.LinkedList;
import java.util.Set;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.id = :id")
    Comment findByID(
            @Param("id") Integer id);

    @Query("Select c FROM Comment c WHERE c.submission_id = :submission_id")
    LinkedList<Comment> commentSet(
            @Param("submission_id") Integer submission_id);

}
