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
    String invite(@RequestBody String jsonString){

        boolean check = false;

        try {
            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //If current user is the owner of the book
            if(userID == bookRepository.findOwnerByBookID(bookID).getId())
            {

                check = true;

                String username = obj.getString("username");
                String role = obj.getString("role");

                //search for username
                User user = userRepository.findByUsername(username);

                System.out.println(user.getId());

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
            }

            else
                System.out.println("No authorization!");

        }catch (Exception e){
            System.out.println(e);
        }

        JSONObject response = new JSONObject();

        if(check)
            response.put("success", true);
        else
            response.put("success", false);

        return response.toString();
    }

    @PostMapping("/api/publishbook")
    public @ResponseBody
    void publishBook(@RequestBody String jsonString){

        try {
            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //If current user is the owner of the book
            if(userID == bookRepository.findOwnerByBookID(bookID).getId())
            {
                //Publish the book (set book's isPublished variable to true)
                bookRepository.publishBook(bookID);
            }

            else
                System.out.println("No authorization!");

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
