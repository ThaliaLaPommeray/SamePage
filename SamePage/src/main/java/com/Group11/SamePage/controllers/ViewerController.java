package com.Group11.SamePage.controllers;

import com.Group11.SamePage.Books.Submission;
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

    @PostMapping("/api/viewbook")
    public @ResponseBody
    void viewBook(@RequestBody String jsonString) {

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //if user is a viewer of the book
            //OR if they are a reader of the book
            //OR if they are an author of the book
            //OR if they are an editor of the book
            //OR if they are the owner of the book
            if  (bookRepository.findViewerIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findReaderIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findAuthorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findEditorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findOwnerByBookID(bookID).getId() == userID)
            {
                //PRINTING STARTS HERE

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

                //print chapter numbers
                Set<Integer> chapterNumSet = submissionRepository.chapterNumBookSet(bookID);
                if(chapterNumSet!=null){
                    for(Integer s : chapterNumSet)
                        System.out.println(s);
                }
                else
                    System.out.println("Empty");
            }

            else
                System.out.println("No authorization!");

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @PostMapping("/api/viewchapter")
    public @ResponseBody
    void viewChapter(@RequestBody String jsonString) {

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //if user is a viewer of the book
            //OR if they are a reader of the book
            //OR if they are an author of the book
            //OR if they are an editor of the book
            //OR if they are the owner of the book
            if  (bookRepository.findViewerIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findReaderIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findAuthorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findEditorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findOwnerByBookID(bookID).getId() == userID)
            {
                Integer chapterNum = obj.getInt("chapterNum");

                Set<Submission> set = submissionRepository.submissionSet(bookID, chapterNum);

                //PRINTING STARTS HERE

                System.out.println("Submissions for Chapter " + chapterNum + ":\n");
                int i = 1;
                for(Submission s : set) {
                    System.out.println(i + ". \"" + s.getTitle() + "\" by " + s.getAuthor().getUsername());

                    if(s.isAccepted())
                        System.out.println("Status: Accepted");
                    else
                        System.out.println("Status: Not accepted");

                    System.out.println("Votes: " + s.getVoteCount());

                    System.out.println("Estimated time of completion: " + s.getEstimatedTime());

                    System.out.println();

                    i++;
                }
            }

            else
                System.out.println("No authorization!");


        }catch (Exception e){
            System.out.println(e);
        }
    }

    @PostMapping("/api/viewsubmission")
    public @ResponseBody
    void viewSubmission(@RequestBody String jsonString) {

        try{

            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");
            Integer bookID = obj.getInt("bookID");

            //if user is a viewer of the book
            //OR if they are a reader of the book
            //OR if they are an author of the book
            //OR if they are an editor of the book
            //OR if they are the owner of the book
            if  (bookRepository.findViewerIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findReaderIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findAuthorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findEditorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findOwnerByBookID(bookID).getId() == userID)
            {
                Integer submissionID = obj.getInt("submissionID");

                Submission submission = submissionRepository.findByID(submissionID);

                System.out.println("\"" + submission.getTitle() + "\" by " + submission.getAuthor().getUsername());

                if(submission.isAccepted())
                    System.out.println("Status: Accepted");
                else
                    System.out.println("Status: Not accepted");

                System.out.println("Votes: " + submission.getVoteCount());

                System.out.println("Estimated time of completion: " + submission.getEstimatedTime());

                System.out.println("\n" + submission.getBody());
            }

            else
                System.out.println("No authorization!");

        }catch (Exception e){
            System.out.println(e);
        }

    }

}
