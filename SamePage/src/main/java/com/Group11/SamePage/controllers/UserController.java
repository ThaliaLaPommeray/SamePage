package com.Group11.SamePage.controllers;

import com.Group11.SamePage.Users.User;
import com.Group11.SamePage.repositories.UserRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.json.*;

@Controller
public class UserController {


    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/api/signup")
    public @ResponseBody String signUpUser(@RequestBody String jsonString){

        try {
            JSONObject obj = new JSONObject(jsonString);

            String username = obj.getString("username");
            String password = obj.getString("password");

            User newUser = new User(username,password);
            userRepository.save(newUser);


        }catch (Exception e){

        }

        return "SUCCESS";
    }



}
