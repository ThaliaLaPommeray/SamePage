package com.Group11.SamePage.Books;
import com.Group11.SamePage.Users.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false,updatable = false, nullable = false)
    protected Owner owner;

    @ManyToMany
    @JoinTable(
            name = "middle_author_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    protected Collection<Author> authors;

    protected String title;

    public Book(){

    }

    public Book(Owner owner, String title) {
        this.owner = owner;
        this.title = title;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
