package sg.edu.nus.iss.miniproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.RecipeDetails;

@Repository
public class RecipeDetailsRepository {
    
    @Autowired
    private JdbcTemplate template;

    private final String insertSQL = "INSERT INTO recipe_details (saved_recipe_id, servings, ready_in_minutes) VALUES (?, ?, ?)";

    private final String findByIdSQL = "SELECT * FROM recipe_details WHERE saved_recipe_id = ?";

    private final String deleteByIdSQL = "DELETE FROM recipe_details WHERE saved_recipe_id = ?";


    public void save(RecipeDetails recipeDetails) {
        template.update(
            insertSQL,
            recipeDetails.getSavedRecipeId(),
            recipeDetails.getServings(),
            recipeDetails.getReadyInMinutes()
        );
    }

    public RecipeDetails findBySavedRecipeId(int savedRecipeId) {
        return template.queryForObject(findByIdSQL, (rs, rowNum) -> {
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setSavedRecipeId(rs.getInt("saved_recipe_id"));
            recipeDetails.setServings(rs.getInt("servings"));
            recipeDetails.setReadyInMinutes(rs.getInt("ready_in_minutes"));
            return recipeDetails;
        }, savedRecipeId);
    }
    
    public void deleteById(int savedRecipeId) {
        template.update(deleteByIdSQL, savedRecipeId);
    }

}
