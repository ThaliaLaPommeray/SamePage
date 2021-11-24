package com.Group11.SamePage.controllers;

import com.Group11.SamePage.repositories.BookRepository;
import com.Group11.SamePage.repositories.SubmissionRepository;
import com.Group11.SamePage.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class ViewerController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SubmissionRepository submissionRepository;

    public ViewerController(UserRepository userRepository, BookRepository bookRepository, SubmissionRepository submissionRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.submissionRepository = submissionRepository;
    }

    @PostMapping("/api/book")
    public @ResponseBody
    void viewBook(@RequestBody String jsonString) {

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //print owner
            System.out.println("Owner:");
            Integer ownerID = bookRepository.ownerIDBook(bookID).getId();

            //print editor
            Set<Integer> setEditors = bookRepository.editorUserIDSet(bookID);
            System.out.println("Editor list:");
            if(setEditors!=null){
                for(Integer s : setEditors)
                    System.out.println(s);
            }
            else
                System.out.println("Empty");

            //print author
            Set<Integer> setAuthors = bookRepository.editorUserIDSet(bookID);
            System.out.println("Author list:");
            if(setAuthors!=null){
                for(Integer s : setAuthors)
                    System.out.println(s);
            }
            else
                System.out.println("Empty");

            System.out.println("Chapter list:");

            //print chapter numbers
            Set<Integer> setChapterNums = bookRepository.chapterNumBookSet(bookID);
            if(setChapterNums!=null){
                for(Integer s : setChapterNums)
                    System.out.println(s);
            }
            else
                System.out.println("Empty");

        }catch (Exception e){

        }
    }
}
