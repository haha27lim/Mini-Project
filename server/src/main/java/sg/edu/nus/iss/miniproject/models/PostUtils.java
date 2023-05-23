package sg.edu.nus.iss.miniproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUtils {
    
    private String title;
    private String content;
    private String author;
    private String tag;
}
