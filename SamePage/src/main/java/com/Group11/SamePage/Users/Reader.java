package com.Group11.SamePage.Users;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Reader extends Viewer{

    public Reader(){};
    public Reader(User user) {
        super(user);
    }
}
