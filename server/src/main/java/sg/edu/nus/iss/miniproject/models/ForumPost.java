package sg.edu.nus.iss.miniproject.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumPost {
    
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String author;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String tag;
    private Set<Comment> comments = new HashSet<>();
}
