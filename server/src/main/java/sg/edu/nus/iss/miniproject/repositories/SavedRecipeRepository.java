package sg.edu.nus.iss.miniproject.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.SavedRecipe;

@Repository
public class SavedRecipeRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SavedRecipe save(SavedRecipe savedRecipe) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
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

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM saved_recipes WHERE id = ?", id);
    }

    public List<SavedRecipe> findByUserId(Long userId) {
        return jdbcTemplate.query(
            "SELECT id, user_id, recipe_id, recipe_title FROM saved_recipes WHERE user_id = ?",
            (rs, rowNum) -> {
                SavedRecipe savedRecipe = new SavedRecipe();
                savedRecipe.setId(rs.getInt("id"));
                savedRecipe.setUserId(rs.getLong("user_id"));
                savedRecipe.setRecipeId(rs.getLong("recipe_id"));
                savedRecipe.setRecipeTitle(rs.getString("recipe_title"));
                return savedRecipe;
            }, userId
        );
    }

    public List<SavedRecipe> findAll() {
        return jdbcTemplate.query(
            "SELECT id, user_id, recipe_id, recipe_title FROM saved_recipes",
            (rs, rowNum) -> {
                SavedRecipe savedRecipe = new SavedRecipe();
                savedRecipe.setId(rs.getInt("id"));
                savedRecipe.setUserId(rs.getLong("user_id"));
                savedRecipe.setRecipeId(rs.getLong("recipe_id"));
                savedRecipe.setRecipeTitle(rs.getString("recipe_title"));
                return savedRecipe;
            }
        );
    }
    
}
