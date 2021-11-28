package com.Group11.SamePage.Books;

import com.Group11.SamePage.Users.Reader;
import com.Group11.SamePage.Users.User;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User reader;

    private Integer submission_id;
    private String body;

    public Comment() {

    }

    public Comment(User reader, Integer submission_id, String body) {
        this.reader = reader;
        this.submission_id = submission_id;
        this.body = body;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public Integer getSubmission_id() {
        return submission_id;
    }

    public void setSubmissionID(Integer submission_id) {
        this.submission_id = submission_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
