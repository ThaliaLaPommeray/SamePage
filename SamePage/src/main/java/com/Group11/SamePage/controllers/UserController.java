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
        }
        else{
            response.put("success", true);
            response.put("username",newUser.getUsername());
            response.put("id",newUser.getId());
        }

        return response.toString();
    }

    @PostMapping("/api/login")
    public @ResponseBody String loginUser(@RequestBody String jsonString){

        User user = null;
        boolean check = false;

        try {
            JSONObject obj = new JSONObject(jsonString);

            String username = obj.getString("username");
            String password = obj.getString("password");

            user = userRepository.login(username, password);

            if(user != null) //if user is found
                check = true;

        }catch (Exception e){
            System.out.println(e);
        }

        JSONObject response = new JSONObject();

        if(!check){
            response.put("success",false);
        }
        else{
            response.put("success", true);
            response.put("username",user.getUsername());
            response.put("id",user.getId());
        }

        return response.toString();
    }

    @PostMapping("/api/main")
    public @ResponseBody String mainPage(@RequestBody String jsonString){

        JSONObject response = new JSONObject();
        boolean check = false;

        try {
            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");

            System.out.println("List of Books:"); //Test print

            Set<String> bookTitleSet = new HashSet<>();

            // get list of book IDs user can interact with
            Set<Integer> bookIDSet = new HashSet<>();
            bookIDSet.addAll(bookRepository.findBookIDsByOwnerID(userID));
            bookIDSet.addAll(bookRepository.findBookIDsByEditorID(userID));
            bookIDSet.addAll(bookRepository.findBookIDsByAuthorID(userID));
            bookIDSet.addAll(bookRepository.findBookIDsByReaderID(userID));
            bookIDSet.addAll(bookRepository.findBookIDsByViewerID(userID));

            if(bookIDSet!=null){
                for(Integer s : bookIDSet)
                {
                    bookTitleSet.add(bookRepository.findTitleByID(s));
                    System.out.println(bookRepository.findTitleByID(s)); //Test print
                }

                //json
                response.put("success", true);
                response.put("bookID", bookIDSet.toArray());
                response.put("bookTitle", bookTitleSet.toArray());
            }

        }catch (Exception e){
            System.out.println(e);
        }

        if(!check)
        {
            {
                //Test print
                System.out.println("Empty");

                //json
                response.put("success",false);
            }
        }

        return response.toString();
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
    public @ResponseBody String viewPublishedBookList(@RequestBody String jsonString){

        JSONObject response = new JSONObject();
        boolean check = false;

        try {

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");

            Set<Integer> bookIDSet = bookRepository.publishedBookIDSet();

            System.out.println("Published Books:");

            Set<String> bookTitleSet = new HashSet<>();

            if(bookIDSet!=null){

                check = true;

                for(Integer s : bookIDSet)
                {
                    bookTitleSet.add(bookRepository.findTitleByID(s));
                    System.out.println(bookRepository.findTitleByID(s)); //Test print
                }

                //json
                response.put("success", true);
                response.put("bookID", bookIDSet.toArray());
                response.put("bookTitle", bookTitleSet.toArray());
            }

        }catch (Exception e){
            System.out.println(e);
        }

        if(!check)
            if(!check)
            {
                {
                    //Test print
                    System.out.println("Empty");

                    //json
                    response.put("success",false);
                }
            }

        return response.toString();
    }

    @PostMapping("/api/viewpublishedbook")
    public @ResponseBody String viewPublishedBook(@RequestBody String jsonString){

        JSONObject response = new JSONObject();

        try {

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //print book title and owner
            System.out.println("\"" + bookRepository.findTitleByID(bookID) + "\" by");
            Integer ownerID = bookRepository.findOwnerByBookID(bookID).getId();
            System.out.println(userRepository.findByID(ownerID).getUsername() + "\n"); //print the username

            //json
            response.put("bookTitle", bookRepository.findTitleByID(bookID));
            response.put("bookOwner", bookRepository.findOwnerByBookID(bookID).getUsername());

            //print editor
            Set<Integer> editorsIDSet = bookRepository.findEditorIDsByBookID(bookID); //list of Editor's IDs
            System.out.println("Editor list:");
            Set<String> editorsNameSet = new HashSet<>();
            if(editorsIDSet!=null){
                for(Integer s : editorsIDSet)
                {
                    System.out.println(userRepository.findByID(s).getUsername()); //print the username
                    editorsNameSet.add(userRepository.findByID(s).getUsername());
                }
            }
            else
                System.out.println("Empty");

            //json
            response.put("bookEditor", editorsNameSet.toArray());

            //print author
            Set<Integer> authorsIDSet = bookRepository.findAuthorIDsByBookID(bookID); //list of Author's IDS
            System.out.println("Author list:");
            Set<String> authorsNameSet = new HashSet<>();
            if(authorsIDSet!=null){
                for(Integer s : authorsIDSet)
                {
                    System.out.println(userRepository.findByID(s).getUsername()); //print the username
                    authorsNameSet.add(userRepository.findByID(s).getUsername());
                }
            }
            else
                System.out.println("Empty");

            //json
            response.put("bookAuthor", authorsNameSet.toArray());

            System.out.println("Chapter list:");

            //print accepted chapter numbers
            Set<Integer> chapterNumSet = submissionRepository.acceptedChapterNumBookSet(bookID);
            if(chapterNumSet!=null){
                for(Integer s : chapterNumSet)
                    System.out.println(s);
            }
            else
                System.out.println("Empty");

            //json
            response.put("chapterNumber", chapterNumSet.toArray());

        }catch (Exception e){
            System.out.println(e);
        }

        return response.toString();
    }

    @PostMapping("/api/viewpublishedsubmission")
    public @ResponseBody String viewPublishedSubmission(@RequestBody String jsonString) {

        JSONObject response = new JSONObject();

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer submissionID = obj.getInt("submissionID");

            Submission submission = submissionRepository.findByID(submissionID);

            //test print
            System.out.println("\"" + submission.getTitle() + "\" by " + submission.getAuthor().getUsername());

            System.out.println("Votes: " + submission.getVoteCount());

            System.out.println("\n" + submission.getBody());

            //json
            response.put("title", submission.getTitle());
            response.put("author", submission.getAuthor().getUsername());
            response.put("voteCount", submission.getVoteCount());
            response.put("body", submission.getBody());

        }catch (Exception e){
            System.out.println(e);
        }

        return response.toString();
    }
}