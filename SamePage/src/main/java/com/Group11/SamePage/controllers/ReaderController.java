package com.Group11.SamePage.controllers;

import com.Group11.SamePage.Books.Comment;
import com.Group11.SamePage.repositories.BookRepository;
import com.Group11.SamePage.repositories.CommentRepository;
import com.Group11.SamePage.repositories.SubmissionRepository;
import com.Group11.SamePage.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

@Controller
public class ReaderController {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final SubmissionRepository submissionRepository;
    private final BookRepository bookRepository;

    public ReaderController(UserRepository userRepository, CommentRepository commentRepository, SubmissionRepository submissionRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.submissionRepository = submissionRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/api/comment")
    public @ResponseBody
    String comment(@RequestBody String jsonString) {

        Comment comment = null;
        boolean check = false;

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");
            Integer submissionID = obj.getInt("submissionID");

            //if user is a reader of the book
            //OR if they are an author of the book
            //OR if they are an editor of the book
            //OR if they are the owner of the book
            if  (   bookRepository.findReaderIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findAuthorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findEditorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findOwnerByBookID(bookID).getId() == userID)
            {
                check = true;

                String body = obj.getString("body");

                comment = new Comment(userRepository.findByID(userID), submissionID, body);

                commentRepository.save(comment);
                commentRepository.save(comment);
            }

            else
                System.out.println("No authorization!");

        }catch (Exception e){
            System.out.println(e);
        }

        JSONObject response = new JSONObject();

        //json
        if(check)
            response.put("success", true);
        else
            response.put("success", false);

        return response.toString();
    }

    @PostMapping("/api/viewcomment")
    public @ResponseBody
    String viewComment(@RequestBody String jsonString) {

        JSONObject response = new JSONObject();
        boolean check = false;

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");
            Integer submissionID = obj.getInt("submissionID");

            //if user is a reader of the book
            //OR if they are an author of the book
            //OR if they are an editor of the book
            //OR if they are the owner of the book
            if  (bookRepository.findReaderIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findAuthorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findEditorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findOwnerByBookID(bookID).getId() == userID)
            {
                check = true;

                LinkedList<Comment> commentList = commentRepository.commentSet(submissionID);
                LinkedList<Integer> userIDList = new LinkedList();
                LinkedList<String> usernameList = new LinkedList<>();
                LinkedList<String> bodyList = new LinkedList<>();

                for(Comment c : commentList)
                {
                    //test print
                    System.out.println("Comment by " + c.getReader().getUsername() + ":");
                    System.out.println(c.getBody());
                    System.out.println();

                    //set making
                    userIDList.add(c.getReader().getId());
                    usernameList.add(c.getReader().getUsername());
                    bodyList.add(c.getBody());
                }

                //json
                response.put("success", true);
                response.put("userID", userIDList.toArray());
                response.put("username", usernameList.toArray());
                response.put("body", bodyList.toArray());
            }

            else
                System.out.println("No authorization!");    //test print

        }catch (Exception e){
            System.out.println(e);
        }

        //json
        if(!check)
            response.put("success", false);

        return response.toString();

    }

    @PostMapping("/api/votesubmission")
    public @ResponseBody
    String voteSubmission(@RequestBody String jsonString) {

        JSONObject response = new JSONObject();
        boolean check = false;

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");
            Integer submissionID = obj.getInt("submissionID");

            //if user is a reader of the book
            //OR if they are an author of the book
            //OR if they are an editor of the book
            //OR if they are the owner of the book
            if (bookRepository.findReaderIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findAuthorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findEditorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findOwnerByBookID(bookID).getId() == userID)
            {
                check = true;
                submissionRepository.voteSubmission(submissionRepository.findByID(submissionID).getVoteCount() + 1, submissionID);
            }
            else
                System.out.println("No authorization!");

        }catch (Exception e){
            System.out.println(e);
        }

        //json
        if(check)
            response.put("success", true);
        else
            response.put("success", false);

        return response.toString();
    }
}
