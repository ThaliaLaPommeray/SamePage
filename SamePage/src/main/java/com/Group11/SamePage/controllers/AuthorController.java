package com.Group11.SamePage.controllers;

import com.Group11.SamePage.Books.Submission;
import com.Group11.SamePage.Users.Author;
import com.Group11.SamePage.Users.User;
import com.Group11.SamePage.repositories.BookRepository;
import com.Group11.SamePage.repositories.SubmissionRepository;
import com.Group11.SamePage.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthorController {

    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final BookRepository bookRepository;

    public AuthorController(UserRepository userRepository, SubmissionRepository submissionRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.submissionRepository = submissionRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/api/submit")
    public @ResponseBody Submission submit(@RequestBody String jsonString, Integer bookID, Integer chapterNum){
        Submission submission = null;

        try {
            JSONObject obj = new JSONObject(jsonString);

            String title = obj.getString("title");
            String body = obj.getString("body");
            String estimatedTime = obj.getString("estimatedTime");

            Author author = new Author();

            submission = new Submission(bookID, author,title, chapterNum, body, 0, false, estimatedTime, body.length(), body.split(" ", -1).length);
            submissionRepository.save(submission);
        }catch (Exception e){

        }


        return submission;
    }
}
