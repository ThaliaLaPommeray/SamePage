package com.Group11.SamePage.controllers;

import com.Group11.SamePage.Books.Submission;
import com.Group11.SamePage.repositories.BookRepository;
import com.Group11.SamePage.repositories.SubmissionRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EditorController {

    private final SubmissionRepository submissionRepository;
    private final BookRepository bookRepository;


    public EditorController(SubmissionRepository submissionRepository, BookRepository bookRepository) {
        this.submissionRepository = submissionRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/api/acceptsubmission")
    public @ResponseBody String acceptSubmission(@RequestBody String jsonString){

        boolean check = false;

        try {
            JSONObject obj = new JSONObject(jsonString);

            Integer userID = obj.getInt("userID");  //current editor logged in
            Integer submissionID = obj.getInt("submissionID"); //current submission
            Integer bookID = obj.getInt("bookID"); //current book

            //if current user is an editor of the book
            //OR if they are the owner
            if (bookRepository.findEditorIDsByBookID(bookID).contains(userID) ||
                    bookRepository.findOwnerByBookID(bookID).getId() == userID)
            {
                check = true; //permissions valid

                Submission temp = submissionRepository.findAcceptedSubmission(bookID);
                Submission newAccepted = submissionRepository.findByID(submissionID);

                //if a submission is already accepted for this chapter
                if(temp != null && temp.getChapterNum() == newAccepted.getChapterNum())
                {
                    if(temp.getId() != submissionID) //if we're not already on the accepted submission
                    {
                        //unaccept previous submission
                        submissionRepository.unacceptSubmission(temp.getId());
                        //accept new submission
                        submissionRepository.acceptSubmission(submissionID);
                    }

                    //else: don't unaccept any, don't accept any. do nothing.
                }
                else
                {
                    //accept new submission
                    submissionRepository.acceptSubmission(submissionID);
                }
            }

            else
                System.out.println("You have no authorization.");


        }catch (Exception e){
            System.out.println(e);
        }

        JSONObject response = new JSONObject();

        response.put("success", check);

        return response.toString();
    }
}
