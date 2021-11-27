package com.Group11.SamePage.Users;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Viewer extends User{

    public Viewer(){};
    public Viewer(User user) {
        super(user);
    }

}
