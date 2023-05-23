package sg.edu.nus.iss.miniproject.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.ForumComment;

@Repository
public class ForumCommentRepository {
  
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveComment(ForumComment comment) {
        String query = "INSERT INTO comment (username, content, created_at, post_id, thumb_up_count, thumb_down_count, rate, is_correct) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, comment.getUsername(), comment.getContent(), comment.getCreatedAt(),
                comment.getPostId(), comment.getThumbUpCount(), comment.getThumbDownCount(),
                comment.getRate(), comment.isCorrect());
    }

    public ForumComment getCommentById(Long commentId) {
        String query = "SELECT * FROM comment WHERE id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            ForumComment comment = new ForumComment();
            comment.setId(rs.getLong("id"));
            comment.setUsername(rs.getString("username"));
            comment.setContent(rs.getString("content"));
            comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            comment.setPostId(rs.getLong("post_id"));
            comment.setThumbUpCount(rs.getInt("thumb_up_count"));
            comment.setThumbDownCount(rs.getInt("thumb_down_count"));
            comment.setRate(rs.getDouble("rate"));
            comment.setCorrect(rs.getBoolean("is_correct"));
            return comment;
        }, commentId);
    }

    public List<ForumComment> getSortedComments() {
        String query = "SELECT * FROM comment ORDER BY is_correct DESC";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            ForumComment comment = new ForumComment();
            comment.setId(rs.getLong("id"));
            comment.setUsername(rs.getString("username"));
            comment.setContent(rs.getString("content"));
            comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            comment.setPostId(rs.getLong("post_id"));
            comment.setThumbUpCount(rs.getInt("thumb_up_count"));
            comment.setThumbDownCount(rs.getInt("thumb_down_count"));
            comment.setRate(rs.getDouble("rate"));
            comment.setCorrect(rs.getBoolean("is_correct"));
            return comment;
        });
    }
}
