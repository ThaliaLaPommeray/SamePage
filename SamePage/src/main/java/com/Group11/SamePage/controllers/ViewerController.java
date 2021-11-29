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

import java.util.LinkedHashSet;
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
    String viewBook(@RequestBody String jsonString) {

        JSONObject response = new JSONObject();
        boolean check = false;

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
                check = true;

                //json
                response.put("success", true);

                //test print book title and owner username
                System.out.println("\"" + bookRepository.findTitleByID(bookID) + "\" by");
                Integer ownerID = bookRepository.findOwnerByBookID(bookID).getId();
                System.out.println(userRepository.findByID(ownerID).getUsername() + "\n");

                //json
                response.put("bookTitle", bookRepository.findTitleByID(bookID));
                response.put("ownerName", userRepository.findByID(ownerID).getUsername());

                //print editor
                Set<Integer> editorIDSet = bookRepository.findEditorIDsByBookID(bookID); //list of Editor's IDs
                System.out.println("Editor list:");

                Set<String> editorNameSet = new LinkedHashSet<>();

                if(editorIDSet!=null){
                    for(Integer s : editorIDSet)
                    {
                        System.out.println(userRepository.findByID(s).getUsername()); //test print the username
                        editorNameSet.add(userRepository.findByID(s).getUsername());
                    }
                }
                else
                    System.out.println("Empty");

                //json
                response.put("editorName", editorNameSet.toArray());

                //test print author
                Set<Integer> authorIDSet = bookRepository.findAuthorIDsByBookID(bookID); //list of Author's IDS
                System.out.println("Author list:");

                Set<String> authorNameSet = new LinkedHashSet<>();

                if(authorIDSet!=null){
                    for(Integer s : authorIDSet)
                    {
                        System.out.println(userRepository.findByID(s).getUsername()); //print the username
                        authorNameSet.add(userRepository.findByID(s).getUsername());
                    }
                }
                else
                    System.out.println("Empty");

                //json
                response.put("authorName", authorNameSet.toArray());

                System.out.println("Chapter list:");

                //test print chapter numbers
                Set<Integer> chapterNumSet = submissionRepository.chapterNumBookSet(bookID);
                if(chapterNumSet!=null){
                    for(Integer s : chapterNumSet)
                        System.out.println(s);
                }
                else
                    System.out.println("Empty");

                //json
                response.put("chapterNum", chapterNumSet.toArray());
            }

            else
                System.out.println("No authorization!");    //test print

        }catch (Exception e){
            System.out.println(e);
        }

        if(!check)
            response.put("success", false);

        return response.toString();
    }

    @PostMapping("/api/viewchapter")
    public @ResponseBody
    String viewChapter(@RequestBody String jsonString) {

        JSONObject response = new JSONObject();

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

                System.out.println("Submissions for Chapter " + chapterNum + ":\n");

                Set<Integer> submissionIDSet = new LinkedHashSet<>();
                Set<String> submissionTitleSet = new LinkedHashSet<>();
                Set<Boolean> isAcceptedSet = new LinkedHashSet<>();
                Set<Integer> voteCountSet = new LinkedHashSet<>();
                Set<String> estimatedTimeSet = new LinkedHashSet<>();

                int i = 1;
                for(Submission s : set) {

                    //test print
                    System.out.println(i + ". \"" + s.getTitle() + "\" by " + s.getAuthor().getUsername());
                    if(s.isAccepted())
                        System.out.println("Status: Accepted");
                    else
                        System.out.println("Status: Not accepted");
                    System.out.println("Votes: " + s.getVoteCount());
                    System.out.println("Estimated time of completion: " + s.getEstimatedTime());
                    System.out.println();
                    i++;

                    submissionIDSet.add(s.getId());
                    submissionTitleSet.add(s.getTitle());
                    isAcceptedSet.add(s.isAccepted());
                    voteCountSet.add(s.getVoteCount());
                    estimatedTimeSet.add(s.getEstimatedTime().toString());
                }

                //json
                response.put("submissionID", submissionIDSet.toArray());
                response.put("submissionTitle", submissionTitleSet.toArray());
                response.put("isAccepted", isAcceptedSet.toArray());
                response.put("voteCount", voteCountSet.toArray());
                response.put("estimatedTime", estimatedTimeSet.toArray());
            }

            else
                System.out.println("No authorization!");


        }catch (Exception e){
            System.out.println(e);
        }

        return response.toString();
    }

    @PostMapping("/api/viewsubmission")
    public @ResponseBody
    String viewSubmission(@RequestBody String jsonString) {

        JSONObject response = new JSONObject();

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

                //test print
                Submission submission = submissionRepository.findByID(submissionID);
                System.out.println("\"" + submission.getTitle() + "\" by " + submission.getAuthor().getUsername());
                if(submission.isAccepted())
                    System.out.println("Status: Accepted");
                else
                    System.out.println("Status: Not accepted");
                System.out.println("Votes: " + submission.getVoteCount());
                System.out.println("Estimated time of completion: " + submission.getEstimatedTime());
                System.out.println("\n" + submission.getBody());

                //json
                response.put("submissionTitle", submission.getTitle());
                response.put("submissionAuthor", submission.getAuthor().getUsername());
                response.put("isAccepted", submission.isAccepted());
                response.put("voteCount", submission.getVoteCount());
                response.put("estimatedTime", submission.getEstimatedTime().toString());

            }

            else
                System.out.println("No authorization!");

        }catch (Exception e){
            System.out.println(e);
        }

        return response.toString();

    }

}
