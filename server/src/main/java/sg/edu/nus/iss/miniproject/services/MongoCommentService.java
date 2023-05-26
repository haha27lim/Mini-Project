package sg.edu.nus.iss.miniproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.miniproject.models.Comment;
import sg.edu.nus.iss.miniproject.repositories.MongoCommentRepository;

@Service
public class MongoCommentService {
    
    @Autowired
    private MongoCommentRepository commentRepo;

    public Comment insertComment(Comment r){
        return commentRepo.insertComment(r);
    }

    public List<Comment> getAllComments() {
        return commentRepo.getAllComments();
    }

}
