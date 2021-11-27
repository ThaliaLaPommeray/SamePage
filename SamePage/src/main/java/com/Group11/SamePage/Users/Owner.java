package com.Group11.SamePage.Users;

import com.Group11.SamePage.Books.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "user")
public class Owner extends Editor{

//    @OneToMany
//    @JoinColumn(name = "owner_id", nullable = false)
//    protected Collection<Book> ownedBooks;

    public Owner(){};
    public Owner(User user) {
        super(user);
    }


//    public Collection<Book> getOwnedBooks() {
//        return ownedBooks;
//    }
//
//    public void setOwnedBooks(Collection<Book> ownedBooks) {
//        this.ownedBooks = ownedBooks;
//    }
//
//    public void addOwnedBook(Book book){
//        if (ownedBooks == null){
//            this.ownedBooks = new ArrayList<Book>();
//        }
//        this.ownedBooks.add(book);
//    }
}
