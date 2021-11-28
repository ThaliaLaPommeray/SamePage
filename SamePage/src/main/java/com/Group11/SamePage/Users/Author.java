package com.Group11.SamePage.Users;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Author extends Reader{

    public Author(){};
    public Author(User user) {
        super(user);
    }
}