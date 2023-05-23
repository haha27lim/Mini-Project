package sg.edu.nus.iss.miniproject.services;

import java.util.List;

import sg.edu.nus.iss.miniproject.models.CommentUtils;
import sg.edu.nus.iss.miniproject.models.ForumComment;
import sg.edu.nus.iss.miniproject.models.UserReview;

public interface PostCommentService {

    ForumComment addComment(CommentUtils comment);

    List<ForumComment> getComments();

    void deleteComment(Long id);

    ForumComment editComment(ForumComment comment);

    ForumComment addThumbUp(UserReview userReview);

    ForumComment addThumbDown(UserReview userReview);

    ForumComment markAsCorrect(Long id);

}
