package com.Group11.SamePage.repositories;

import com.Group11.SamePage.Books.Book;
import com.Group11.SamePage.Users.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into book (title, owner_id) values (:title, :owner_id)",
    nativeQuery = true)
    void createBook(
            @Param("title") String title,
            @Param("owner_id") Integer owner_id);

    @Transactional
    @Modifying
    @Query(value = "insert into middle_editor_book (user_id, book_id) values (:user_id, :book_id)",
            nativeQuery = true)
    void inviteEditor(
            @Param("user_id") Integer user_id,
            @Param("book_id") Integer book_id);
}
