package com.Group11.SamePage.controllers;
import com.Group11.SamePage.Users.User;
import com.Group11.SamePage.repositories.*;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OwnerController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public OwnerController(UserRepository userRepository, BookRepository bookRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/api/invite")
    public @ResponseBody
    void invite(@RequestBody String jsonString){

        try {
            JSONObject obj = new JSONObject(jsonString);

            String username = obj.getString("username");
            String role = obj.getString("role");
            Integer bookID = obj.getInt("bookID");

            //search for username
            User user = userRepository.searchByUsername(username);

            //check for promotion?
            if(role.equalsIgnoreCase("editor")){
                bookRepository.inviteEditor(user.getId(), bookID);
            }
            else if(role.equalsIgnoreCase("author")){
                bookRepository.inviteAuthor(user.getId(), bookID);
            }
            else if(role.equalsIgnoreCase("reader")){
                bookRepository.inviteReader(user.getId(), bookID);
            }
            else if(role.equalsIgnoreCase("viewer")){
                bookRepository.inviteViewer(user.getId(), bookID);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}