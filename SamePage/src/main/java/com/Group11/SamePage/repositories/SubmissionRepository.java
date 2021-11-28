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
    Set<Submission> submissionSet(
            @Param("book_id") Integer book_id,
            @Param("chapterNum") Integer chapterNum);

    @Query("SELECT s.chapterNum FROM Submission s WHERE s.book_id = :book_id")
    Set<Integer> chapterNumBookSet(
            @Param("book_id") Integer book_id);

    @Query("SELECT s.chapterNum FROM Submission s WHERE s.book_id = :book_id AND s.isAccepted = true")
    Set<Integer> acceptedChapterNumBookSet(
            @Param("book_id") Integer book_id);

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

    @Query("SELECT s FROM Submission s WHERE s.isAccepted = true AND s.book_id = :book_id")
    Submission findAcceptedSubmission(
            @Param("book_id") Integer book_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Submission s set s.isAccepted = false WHERE s.id = :id")
    void unacceptSubmission(
            @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Submission s set s.isAccepted = true WHERE s.id = :id")
    void acceptSubmission(
            @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Submission s set s.voteCount = :voteCount WHERE s.id = :id")
    void voteSubmission(
            @Param("voteCount") Integer voteCount,
            @Param("id") Integer id);
}
