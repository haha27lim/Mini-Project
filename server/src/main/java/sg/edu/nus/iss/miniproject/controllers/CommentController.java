package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.miniproject.models.Comment;
import sg.edu.nus.iss.miniproject.services.FoodService;
import sg.edu.nus.iss.miniproject.services.MongoCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    
    @Autowired
    private MongoCommentService commentSvc;

    @PostMapping(path = "/comment", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> saveComment(@RequestParam String title, @RequestParam String name, @RequestParam String email,
        @RequestParam String subject, @RequestParam Integer rating, @RequestParam String comment) {
		Comment c= new Comment();
		c.setTitle(title);
		c.setName(name);
        c.setEmail(email);
        c.setSubject(subject);
		c.setRating(rating);
		c.setComment(comment);
		Comment r = commentSvc.insertComment(c);
		return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(r.toJson().toString());
	}
}
