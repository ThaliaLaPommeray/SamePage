package com.Group11.SamePage.repositories;

import com.Group11.SamePage.Books.*;

import com.Group11.SamePage.Middle.MiddleAuthorBook;
import com.Group11.SamePage.Middle.MiddleEditorBook;
import com.Group11.SamePage.Users.Owner;
import com.Group11.SamePage.Users.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query("SELECT b.title FROM Book b WHERE b.id = :bookID")
    String findTitleByID(
            @Param("bookID") Integer bookID);

    @Transactional
    @Modifying
    @Query(value = "insert into book (title, owner_id, is_published) values (:title, :owner_id, false)",
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
    Integer ownerBookID(
            @Param("owner_id") Integer owner_id);

    @Query("SELECT m.book_id FROM MiddleEditorBook m WHERE m.user_id = :user_id")
    Set<Integer> editorBookIDSet(
            @Param("user_id") Integer user_id);

    @Query("SELECT m.book_id FROM MiddleAuthorBook m WHERE m.user_id = :user_id")
    Set<Integer> authorBookIDSet(
            @Param("user_id") Integer user_id);

    @Query("SELECT m.book_id FROM MiddleReaderBook m WHERE m.user_id = :user_id")
    Set<Integer> readerBookIDSet(
            @Param("user_id") Integer user_id);

    @Query("SELECT m.book_id FROM MiddleViewerBook m WHERE m.user_id = :user_id")
    Set<Integer> viewerBookIDSet(
            @Param("user_id") Integer user_id);

    @Query("SELECT b.owner FROM Book b WHERE b.id = :book_id")
    Owner ownerIDBook(
            @Param("book_id") Integer book_id);

    @Query("SELECT m.user_id FROM MiddleEditorBook m WHERE m.book_id = :book_id")
    Set<Integer> editorUserIDSet(
            @Param("book_id") Integer book_id);

    @Query("SELECT m.user_id FROM MiddleAuthorBook m WHERE m.book_id = :book_id")
    Set<Integer> authorUserIDSet(
            @Param("book_id") Integer book_id);

    @Query("SELECT m FROM MiddleEditorBook m WHERE m.user_id = :user_id AND m.book_id = :book_id")
    MiddleEditorBook checkEditor(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Book b set b.isPublished = true WHERE b.id = :id")
    void publishBook(
            @Param("id") Integer id);

    @Query("SELECT b.id FROM Book b WHERE b.isPublished = true")
    Set<Integer> publishedBookIDSet();
}
