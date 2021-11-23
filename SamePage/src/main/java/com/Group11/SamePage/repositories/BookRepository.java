package com.Group11.SamePage.repositories;

import com.Group11.SamePage.Books.*;

import com.Group11.SamePage.Middle.MiddleEditorBook;
import com.Group11.SamePage.Users.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into book (title, owner_id) values (:title, :owner_id)",
            nativeQuery = true)
    int createBook(
            @Param("title") String title,
            @Param("owner_id") Integer owner_id);

    @Transactional
    @Modifying
    @Query(value = "insert into middle_editor_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteEditor(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    @Transactional
    @Modifying
    @Query(value = "insert into middle_author_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteAuthor(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    @Transactional
    @Modifying
    @Query(value = "insert into middle_reader_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteReader(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    @Transactional
    @Modifying
    @Query(value = "insert into middle_viewer_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteViewer(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    @Query("SELECT b.id FROM Book b WHERE b.owner.id = :owner_id")
    Set<Integer> ownerBookIDCollection(
            @Param("owner_id") Integer owner_id);

    @Query("SELECT m.book_id FROM MiddleEditorBook m WHERE m.user_id = :user_id")
    Set<Integer> editorBookIDCollection(
            @Param("user_id") Integer user_id);

    @Query("SELECT m.book_id FROM MiddleAuthorBook m WHERE m.user_id = :user_id")
    Set<Integer> authorBookIDCollection(
            @Param("user_id") Integer user_id);

    @Query("SELECT m.book_id FROM MiddleReaderBook m WHERE m.user_id = :user_id")
    Set<Integer> readerBookIDCollection(
            @Param("user_id") Integer user_id);

    @Query("SELECT m.book_id FROM MiddleViewerBook m WHERE m.user_id = :user_id")
    Set<Integer> viewerBookIDCollection(
            @Param("user_id") Integer user_id);

//    @Query("SELECT b from Book b WHERE b.id = :bookID")
//    Book findByID(
//            @Param("bookID") Integer bookID);
}
