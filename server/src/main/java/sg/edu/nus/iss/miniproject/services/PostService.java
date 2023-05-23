package sg.edu.nus.iss.miniproject.services;

import java.util.List;
import java.util.Set;

import sg.edu.nus.iss.miniproject.models.ForumPost;
import sg.edu.nus.iss.miniproject.models.PostUtils;

public interface PostService {

    ForumPost savePost(PostUtils postUtils);

    ForumPost getPost(Long id);

    List<ForumPost> getPosts();

    void deletePost(Long id);

    ForumPost updatePost(Long id, PostUtils post);

    List<ForumPost> getPostsByTag(String tag);

    Set<String> getTags();

    Integer getUserPostsCount(String username);

    List<ForumPost> getUserPosts(String username);

}