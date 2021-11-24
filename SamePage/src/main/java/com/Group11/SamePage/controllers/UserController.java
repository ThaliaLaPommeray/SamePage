package com.Group11.SamePage.controllers;
import com.Group11.SamePage.Users.*;
import com.Group11.SamePage.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SubmissionRepository submissionRepository;

    public UserController(UserRepository userRepository, BookRepository bookRepository, SubmissionRepository submissionRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.submissionRepository = submissionRepository;
    }

    @PostMapping("/api/signup")
    public @ResponseBody User signUpUser(@RequestBody String jsonString){

        User newUser = null;

        try {
            JSONObject obj = new JSONObject(jsonString);

            String username = obj.getString("username");
            String password = obj.getString("password");

            newUser = new User(username,password);

            userRepository.save(newUser);

        }catch (Exception e){
            System.out.println(e);
        }

        return newUser;
    }

    @PostMapping("/api/login")
    public @ResponseBody User loginUser(@RequestBody String jsonString){

        User user = null;

        try {
            JSONObject obj = new JSONObject(jsonString);

            String username = obj.getString("username");
            String password = obj.getString("password");

            user = userRepository.login(username, password);

        }catch (Exception e){
            System.out.println(e);
        }

        return user;
    }

    @PostMapping("/api/main")
    public @ResponseBody void mainPage(@RequestBody String jsonString){
        try {
            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");

            // get list of book IDs user can interact with
            Set<Integer> set = new HashSet<>();
            set.add(bookRepository.ownerBookID(userID));
            set.addAll(bookRepository.editorBookIDSet(userID));
            set.addAll(bookRepository.authorBookIDSet(userID));
            set.addAll(bookRepository.readerBookIDSet(userID));
            set.addAll(bookRepository.viewerBookIDSet(userID));

            System.out.println("List of Books:");

            if(set!=null){
                for(Integer s : set)
                    System.out.println(s);
            }
            else
                System.out.println("Empty");

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @PostMapping("/api/createbook")
    public @ResponseBody void createBook(@RequestBody String jsonString){

        try {

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            String title = obj.getString("title");

            Owner owner = new Owner(userRepository.findById(userID).get());

            bookRepository.createBook(title, userID);

        }catch (Exception e){
            System.out.println(e);
        }
    }

}