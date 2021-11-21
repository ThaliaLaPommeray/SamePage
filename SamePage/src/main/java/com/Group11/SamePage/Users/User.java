package com.Group11.SamePage.Users;
import javax.persistence.*;

@Entity
@Table(name = "user", schema = "samepage")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    protected String username;
    protected String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    protected User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
