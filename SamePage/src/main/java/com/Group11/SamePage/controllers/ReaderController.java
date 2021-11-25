package com.Group11.SamePage.controllers;


import com.Group11.SamePage.Books.Comment;
import com.Group11.SamePage.Users.Reader;
import com.Group11.SamePage.repositories.CommentRepository;
import com.Group11.SamePage.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class ReaderController {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ReaderController(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping("/api/comment")
    public @ResponseBody
    Comment comment(@RequestBody String jsonString) {

        Comment comment = null;

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer submissionID = obj.getInt("submissionID");
            String body = obj.getString("body");

            comment = new Comment(userRepository.findByID(userID), submissionID, body);

            commentRepository.save(comment);

        }catch (Exception e){
            System.out.println(e);
        }

        return comment;
    }

    @PostMapping("/api/viewcomment")
    public @ResponseBody
    void viewComment(@RequestBody String jsonString) {

        try{

            JSONObject obj = new JSONObject(jsonString);

            //Integer userID = obj.getInt("userID");
            Integer submissionID = obj.getInt("submissionID");

            Set<Comment> set = commentRepository.commentSet(submissionID);

            //PRINTING STARTS HERE

            for(Comment c : set)
            {
                System.out.println("Comment by " + c.getReader().getUsername() + ":");
                System.out.println(c.getBody());
                System.out.println();
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

}
