package com.Group11.SamePage.controllers;
import com.Group11.SamePage.Books.Submission;
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
    public @ResponseBody String signUpUser(@RequestBody String jsonString){

        User newUser = null;
        Boolean check = false;
        try {
            JSONObject obj = new JSONObject(jsonString);

            String username = obj.getString("username");
            String password = obj.getString("password");

            //if user doesn't exist yet
            if(userRepository.findByUsername(username) == null)
            {
                check = true;
                newUser = new User(username,password);
                userRepository.save(newUser);
            }

        }catch (Exception e){
            System.out.println(e);
        }

        JSONObject response = new JSONObject();

        if(!check){
            response.put("success",false);
            response.put("message","User already exists!");
        }else{
            response.put("success", true);
            response.put("username",newUser.getUsername());
            response.put("id",newUser.getId());
        }

        return response.toString();
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
            set.addAll(bookRepository.findBookIDsByOwnerID(userID));
            set.addAll(bookRepository.findBookIDsByEditorID(userID));
            set.addAll(bookRepository.findBookIDsByAuthorID(userID));
            set.addAll(bookRepository.findBookIDsByReaderID(userID));
            set.addAll(bookRepository.findBookIDsByViewerID(userID));

            //PRINTING STARTS HERE

            System.out.println("List of Books:");

            if(set!=null){
                for(Integer s : set)
                    System.out.println(bookRepository.findTitleByID(s));
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

    @PostMapping("/api/viewpublishedbooklist")
    public @ResponseBody void viewPublishedBookList(@RequestBody String jsonString){
        try {

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");

            Set<Integer> set = bookRepository.publishedBookIDSet();
            System.out.println("Published Books:");

            if(set!=null){
                for(Integer s : set)
                    System.out.println(bookRepository.findTitleByID(s));
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @PostMapping("/api/viewpublishedbook")
    public @ResponseBody void viewPublishedBook(@RequestBody String jsonString){
        try {

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //print book title and owner
            System.out.println("\"" + bookRepository.findTitleByID(bookID) + "\" by");
            Integer ownerID = bookRepository.findOwnerByBookID(bookID).getId();
            System.out.println(userRepository.findByID(ownerID).getUsername() + "\n"); //print the username

            //print editor
            Set<Integer> setEditors = bookRepository.findEditorIDsByBookID(bookID); //list of Editor's IDs
            System.out.println("Editor list:");
            if(setEditors!=null){
                for(Integer s : setEditors)
                    System.out.println(userRepository.findByID(s).getUsername()); //print the username
            }
            else
                System.out.println("Empty");

            //print author
            Set<Integer> setAuthors = bookRepository.findAuthorIDsByBookID(bookID); //list of Author's IDS
            System.out.println("Author list:");
            if(setAuthors!=null){
                for(Integer s : setAuthors)
                    System.out.println(userRepository.findByID(s).getUsername()); //print the username
            }
            else
                System.out.println("Empty");

            System.out.println("Chapter list:");

            //print accepted chapter numbers
            Set<Integer> chapterNumSet = submissionRepository.acceptedChapterNumBookSet(bookID);
            if(chapterNumSet!=null){
                for(Integer s : chapterNumSet)
                    System.out.println(s);
            }
            else
                System.out.println("Empty");

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @PostMapping("/api/viewpublishedsubmission")
    public @ResponseBody
    void viewPublishedSubmission(@RequestBody String jsonString) {

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer submissionID = obj.getInt("submissionID");

            Submission submission = submissionRepository.findByID(submissionID);

            System.out.println("\"" + submission.getTitle() + "\" by " + submission.getAuthor().getUsername());

            System.out.println("Votes: " + submission.getVoteCount());

            System.out.println("\n" + submission.getBody());

        }catch (Exception e){
            System.out.println(e);
        }

    }
}