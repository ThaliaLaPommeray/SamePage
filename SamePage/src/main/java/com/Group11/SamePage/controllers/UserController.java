package com.Group11.SamePage.controllers;
import com.Group11.SamePage.Books.Book;
import com.Group11.SamePage.Users.*;
import com.Group11.SamePage.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.*;
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

        }

        return user;
    }

    @PostMapping("/api/main")
    public @ResponseBody void mainPage(@RequestBody String jsonString){
        try {
            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");

            Set<Integer> set = bookRepository.ownerBookIDCollection(userID);
            set.addAll(bookRepository.editorBookIDCollection(userID));
            set.addAll(bookRepository.authorBookIDCollection(userID));
            set.addAll(bookRepository.readerBookIDCollection(userID));
            set.addAll(bookRepository.viewerBookIDCollection(userID));

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

            String title = obj.getString("title");
            Integer userID = obj.getInt("userID");

            Owner owner = new Owner(userRepository.findById(userID).get());

            bookRepository.createBook(title, userID);

        }catch (Exception e){
            System.out.println(e);
        }
    }

}