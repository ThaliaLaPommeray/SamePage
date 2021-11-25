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

import java.text.SimpleDateFormat;
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

            //PRINTING STARTS HERE

            //print owner
            System.out.println("Owner:");
            Integer ownerID = bookRepository.ownerIDBook(bookID).getId();
            System.out.println(userRepository.findByID(ownerID).getUsername()); //print the username

            //print editor
            Set<Integer> setEditors = bookRepository.editorUserIDSet(bookID); //list of Editor's IDs
            System.out.println("Editor list:");
            if(setEditors!=null){
                for(Integer s : setEditors)
                    System.out.println(userRepository.findByID(s).getUsername()); //print the username
            }
            else
                System.out.println("Empty");

            //print author
            Set<Integer> setAuthors = bookRepository.authorUserIDSet(bookID); //list of Author's IDS
            System.out.println("Author list:");
            if(setAuthors!=null){
                for(Integer s : setAuthors)
                    System.out.println(userRepository.findByID(s).getUsername()); //print the username
            }
            else
                System.out.println("Empty");

            System.out.println("Chapter list:");

            //print chapter numbers
            Set<Integer> setChapterNums = bookRepository.chapterNumBookSet(bookID);
            if(setChapterNums!=null){
                for(Integer s : setChapterNums)
                    System.out.println(s);
            }
            else
                System.out.println("Empty");

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
            Integer chapterNum = obj.getInt("chapterNum");

            Set<Submission> set = submissionRepository.submissionIDChapterSet(bookID, chapterNum);

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

        }catch (Exception e){
            System.out.println(e);
        }

    }

}
