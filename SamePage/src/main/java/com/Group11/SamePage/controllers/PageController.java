package com.Group11.SamePage.controllers;

import org.springframework.stereotype.Controller;
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

    @RequestMapping("/mainpage")
    public String mainpage(){
        return "mainpage";
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

    @RequestMapping("/submissionviewpage")
    public String submissionviewpage(){
        return "submissionviewpage";
    }

    @RequestMapping("/newsubmission")
    public String newSubmission() { return "newsubmission";
    }
}
