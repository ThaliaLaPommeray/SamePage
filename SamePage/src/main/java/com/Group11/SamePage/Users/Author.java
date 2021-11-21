package com.Group11.SamePage.Users;

import com.Group11.SamePage.Books.Book;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
public class Author extends Reader{


    @ManyToMany
    @JoinTable(
            name = "middle_author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    protected Collection<Book> writtenBooks;

    public Author(){};
    public Author(User user) {
        super(user);
    }

    public Collection<Book> getWrittenBooks() {
        return writtenBooks;
    }

    public void setWrittenBooks(Collection<Book> writtenBooks) {
        this.writtenBooks = writtenBooks;
    }
}
