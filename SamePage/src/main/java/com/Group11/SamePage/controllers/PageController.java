package com.Group11.SamePage.controllers;

import com.Group11.SamePage.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/signuppage")
    public String signuppage(){
        return "signuppage";
    }

    @RequestMapping("/loginpage")
    public String loginpage(){
        return "loginpage";
    }

    @RequestMapping("/bookviewpage")
    public String bookviewpage(){
        return "bookviewpage";
    }

    @RequestMapping("/bookeditorpage")
    public String bookeditorpage(){
        return "bookeditorpage";
    }

    @RequestMapping("/submissioneditor")
    public String submissioneditor(){
        return "submissioneditor";
    }


}
