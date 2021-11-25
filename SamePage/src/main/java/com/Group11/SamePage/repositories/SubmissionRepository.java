package com.Group11.SamePage.repositories;

import com.Group11.SamePage.Books.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Set;

public interface SubmissionRepository extends CrudRepository<Submission, Integer> {

    @Query("SELECT s FROM Submission s WHERE s.id = :id")
    Submission findByID(
            @Param("id") Integer id);

    @Query("SELECT s FROM Submission s WHERE s.book_id = :book_id AND s.chapterNum = :chapterNum")
    Set<Submission> submissionIDChapterSet(
            @Param("book_id") Integer book_id,
            @Param("chapterNum") Integer chapterNum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Submission s set s.title = :title, s.body = :body, s.estimatedTime = :estimatedTime, s.charCount = :charCount, s.wordCount = :wordCount WHERE s.id = :id")
    void editSubmission(
            @Param("id") Integer id,
            @Param("title") String title,
            @Param("body") String body,
            @Param("estimatedTime") Date estimatedTime,
            @Param("charCount") Integer charCount,
            @Param("wordCount") Integer wordCount);

}
