package sg.edu.nus.iss.miniproject.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.ForumPost;

@Repository
public class ForumPostRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void savePost(ForumPost post) {
        String query = "INSERT INTO post (title, content, author, created_at, updated_at, tag) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, post.getTitle(), post.getContent(), post.getAuthor(),
                post.getCreatedAt(), post.getUpdatedAt(), post.getTag());
    }

    public ForumPost getPostById(Long postId) {
        String query = "SELECT * FROM post WHERE id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            ForumPost post = new ForumPost();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setAuthor(rs.getString("author"));
            post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            post.setTag(rs.getString("tag"));
            return post;
        }, postId);
    }

    public ForumPost findByTitle(String title) {
        String query = "SELECT * FROM post WHERE title = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            ForumPost post = new ForumPost();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setAuthor(rs.getString("author"));
            post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            post.setTag(rs.getString("tag"));
            return post;
        }, title);
    }

    public List<ForumPost> findAllByTag(String tag) {
        String query = "SELECT * FROM post WHERE tag = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            ForumPost post = new ForumPost();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setAuthor(rs.getString("author"));
            post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            post.setTag(rs.getString("tag"));
            return post;
        }, tag);
    }

    public Integer countPostByAuthor(String author) {
        String query = "SELECT COUNT(*) FROM post WHERE author = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, author);
    }

    public List<ForumPost> getAllByAuthor(String author) {
        String query = "SELECT * FROM post WHERE author = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            ForumPost post = new ForumPost();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setAuthor(rs.getString("author"));
            post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            post.setTag(rs.getString("tag"));
            return post;
        }, author);
    }

}
