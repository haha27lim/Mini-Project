package sg.edu.nus.iss.miniproject.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumComment {

    private Long id;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private Long postId; // To store the ID of the associated Post entity
    private int thumbUpCount;
    private int thumbDownCount;
    private double rate;
    private boolean isCorrect;
    private List<Long> userIdsThatReviewed = new ArrayList<>(); 
}
