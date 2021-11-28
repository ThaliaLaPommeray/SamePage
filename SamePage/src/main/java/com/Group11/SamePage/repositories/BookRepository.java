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

    //Returns book's title (string) given the book ID
    @Query("SELECT b.title FROM Book b WHERE b.id = :bookID")
    String findTitleByID(
            @Param("bookID") Integer bookID);

    //Creates a book to put in the book database
    @Transactional
    @Modifying
    @Query(value = "insert into book (title, owner_id, is_published) values (:title, :owner_id, false)",
            nativeQuery = true)
    void createBook(
            @Param("title") String title,
            @Param("owner_id") Integer owner_id);

    //Find book ID by the Book Title and Owner ID
    @Query("SELECT b.id FROM Book b WHERE b.owner.id = :owner_id AND b.title = :title")
    Integer findBookIDByTitleAndOwnerID(
            @Param("title") String title,
            @Param("owner_id") Integer owner_id);

    //Invites editor and saves to database
    @Transactional
    @Modifying
    @Query(value = "insert into middle_editor_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteEditor(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    //Invites author and saves to database
    @Transactional
    @Modifying
    @Query(value = "insert into middle_author_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteAuthor(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    //Invites reader and saves to database
    @Transactional
    @Modifying
    @Query(value = "insert into middle_reader_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteReader(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    //Invites viewer and saves to database
    @Transactional
    @Modifying
    @Query(value = "insert into middle_viewer_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteViewer(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);

    //Returns set of book IDs that a user owns
    @Query("SELECT b.id FROM Book b WHERE b.owner.id = :owner_id")
    Set<Integer> findBookIDsByOwnerID(
            @Param("owner_id") Integer owner_id);

    //Returns set of book IDs that a user can edit
    @Query("SELECT m.book_id FROM MiddleEditorBook m WHERE m.user_id = :user_id")
    Set<Integer> findBookIDsByEditorID(
            @Param("user_id") Integer user_id);

    //Returns set of book IDs that a user can author
    @Query("SELECT m.book_id FROM MiddleAuthorBook m WHERE m.user_id = :user_id")
    Set<Integer> findBookIDsByAuthorID(
            @Param("user_id") Integer user_id);

    //Returns set of book IDs that a user can read
    @Query("SELECT m.book_id FROM MiddleReaderBook m WHERE m.user_id = :user_id")
    Set<Integer> findBookIDsByReaderID(
            @Param("user_id") Integer user_id);

    //Returns set of book IDs that a user can view
    @Query("SELECT m.book_id FROM MiddleViewerBook m WHERE m.user_id = :user_id")
    Set<Integer> findBookIDsByViewerID(
            @Param("user_id") Integer user_id);

    //Returns owner (Owner) of book given the book ID
    @Query("SELECT b.owner FROM Book b WHERE b.id = :book_id")
    Owner findOwnerByBookID(
            @Param("book_id") Integer book_id);

    //Returns set of user IDs of a book's editors
    @Query("SELECT m.user_id FROM MiddleEditorBook m WHERE m.book_id = :book_id")
    Set<Integer> findEditorIDsByBookID(
            @Param("book_id") Integer book_id);

    @Query("SELECT m.user_id FROM MiddleAuthorBook m WHERE m.book_id = :book_id")
    Set<Integer> findAuthorIDsByBookID(
            @Param("book_id") Integer book_id);

    @Query("SELECT m.user_id FROM MiddleReaderBook m WHERE m.book_id = :book_id")
    Set<Integer> findReaderIDsByBookID(
            @Param("book_id") Integer book_id);

    @Query("SELECT m.user_id FROM MiddleViewerBook m WHERE m.book_id = :book_id")
    Set<Integer> findViewerIDsByBookID(
            @Param("book_id") Integer book_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Book b set b.isPublished = true WHERE b.id = :id")
    void publishBook(
            @Param("id") Integer id);

    @Query("SELECT b.id FROM Book b WHERE b.isPublished = true")
    Set<Integer> publishedBookIDSet();
}
