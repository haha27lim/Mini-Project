package sg.edu.nus.iss.miniproject.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.RecipeDetails;
import sg.edu.nus.iss.miniproject.models.SavedRecipe;

@Repository
public class SaveRecipeRepository {
    
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private RecipeDetailsRepository recipeDetailsRepo;

    public SavedRecipe save(SavedRecipe savedRecipe) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO saved_recipes (user_id, recipe_id, recipe_title) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, savedRecipe.getUserId());
            ps.setLong(2, savedRecipe.getRecipeId());
            ps.setString(3, savedRecipe.getRecipeTitle());
            return ps;
        }, keyHolder);
        savedRecipe.setId(keyHolder.getKey().intValue());

        return savedRecipe;
    }

    public List<SavedRecipe> findByUserId(Long userId) {
        String query = "SELECT sr.id, sr.user_id, sr.recipe_id, sr.recipe_title, rd.servings, rd.ready_in_minutes " +
                       "FROM saved_recipes sr " +
                       "INNER JOIN recipe_details rd ON sr.id = rd.saved_recipe_id " +
                       "WHERE sr.user_id = ?";
        
        return template.query(query, (rs, rowNum) -> {
            SavedRecipe savedRecipe = new SavedRecipe();
            savedRecipe.setId(rs.getInt("id"));
            savedRecipe.setUserId(rs.getLong("user_id"));
            savedRecipe.setRecipeId(rs.getLong("recipe_id"));
            savedRecipe.setRecipeTitle(rs.getString("recipe_title"));
            
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setSavedRecipeId(rs.getInt("id"));
            recipeDetails.setServings(rs.getInt("servings"));
            recipeDetails.setReadyInMinutes(rs.getInt("ready_in_minutes"));
            
            savedRecipe.setRecipeDetails(recipeDetails);
            
            return savedRecipe;
        }, userId);
    }
    

    public List<SavedRecipe> findAll() {
        String query = "SELECT * FROM saved_recipes";
        return template.query(query, (rs, rowNum) -> {
            SavedRecipe savedRecipe = new SavedRecipe();
            savedRecipe.setId(rs.getInt("id"));
            savedRecipe.setUserId(rs.getLong("user_id"));
            savedRecipe.setRecipeId(rs.getLong("recipe_id"));
            savedRecipe.setRecipeTitle(rs.getString("recipe_title"));
            savedRecipe.setRecipeDetails(recipeDetailsRepo.findBySavedRecipeId(savedRecipe.getId()));
            return savedRecipe;
        });
    }

    public void deleteById(int id) {
        template.update("DELETE FROM saved_recipes WHERE id = ?", id);
        recipeDetailsRepo.deleteById(id);
    }
    
}
