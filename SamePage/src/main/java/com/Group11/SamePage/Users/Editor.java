package com.Group11.SamePage.Users;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Editor extends Author{

    public Editor(){};
    public Editor(User user) {
        super(user);
    }
}
