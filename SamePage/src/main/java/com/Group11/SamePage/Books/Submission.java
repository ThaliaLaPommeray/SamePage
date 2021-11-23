package com.Group11.SamePage.Books;
import com.Group11.SamePage.Users.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Author author;

    private Integer book_id;
    private String title;
    private Integer chapterNum;
    private String body;
    private Integer voteCount;
    private boolean isAccepted;
    private Date estimatedTime;
    private Integer charCount;
    private Integer wordCount;

    public Submission(){};

    public Submission(Author author, Integer book_id, String title, Integer chapterNum, String body, Integer voteCount, boolean isAccepted, Date estimatedTime, Integer charCount, Integer wordCount) {
        this.author = author;
        this.book_id = book_id;
        this.title = title;
        this.chapterNum = chapterNum;
        this.body = body;
        this.voteCount = voteCount;
        this.isAccepted = isAccepted;
        this.estimatedTime = estimatedTime;
        this.charCount = charCount;
        this.wordCount = wordCount;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getBook() {
        return book_id;
    }

    public void setBook(Integer book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Date getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Date estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Integer getCharCount() {
        return charCount;
    }

    public void setCharCount(Integer charCount) {
        this.charCount = charCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
}
