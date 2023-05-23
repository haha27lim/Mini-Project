package sg.edu.nus.iss.miniproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUtils {
    
    private String username;
    private String content;
    private Long postId;

}
