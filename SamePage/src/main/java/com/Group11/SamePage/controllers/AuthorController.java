package com.Group11.SamePage.controllers;

import com.Group11.SamePage.Books.Book;
import com.Group11.SamePage.Books.Submission;
import com.Group11.SamePage.Users.Author;
import com.Group11.SamePage.repositories.BookRepository;
import com.Group11.SamePage.repositories.SubmissionRepository;
import com.Group11.SamePage.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    public @ResponseBody Submission submit(@RequestBody String jsonString){

        Submission submission = null;

        try {
            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");  //author
            Integer bookID = obj.getInt("bookID");  //current book
            String title = obj.getString("title");  //title of submission
            Integer chapterNum = obj.getInt("chapterNum");  //current book chapter
            String body = obj.getString("body");    //text of submission

            //estimated date of completion
            Integer year = obj.getInt("year");
            Integer month = obj.getInt("month");
            Integer day = obj.getInt("day");

            Author author = new Author(userRepository.findByID(userID));

            SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYYY");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month-1); //start of month
            cal.set(Calendar.DAY_OF_MONTH, day);

            Date date = new Date(cal.getTimeInMillis());

            submission = new Submission(author, bookID, title, chapterNum, body, 0,
                    false, date, body.length(), body.split(" ").length);

            submissionRepository.save(submission);

        }catch (Exception e){
            System.out.println(e);
        }

        return submission;
    }
}
