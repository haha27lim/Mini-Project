package sg.edu.nus.iss.miniproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetails {
    
    private int savedRecipeId;
    private int servings;
    private int readyInMinutes;
}
