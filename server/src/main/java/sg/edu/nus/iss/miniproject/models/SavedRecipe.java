package sg.edu.nus.iss.miniproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedRecipe {
    
    private Integer id;
    private Long userId;
    private Long recipeId;
    private String recipeTitle;

}

