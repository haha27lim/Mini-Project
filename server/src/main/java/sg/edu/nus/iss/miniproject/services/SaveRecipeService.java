package sg.edu.nus.iss.miniproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.miniproject.models.RecipeDetails;
import sg.edu.nus.iss.miniproject.models.SavedRecipe;
import sg.edu.nus.iss.miniproject.repositories.RecipeDetailsRepository;
import sg.edu.nus.iss.miniproject.repositories.SaveRecipeRepository;

@Service
public class SaveRecipeService {
    
    @Autowired
    private SaveRecipeRepository saveRecipeRepository;

    @Autowired
    private RecipeDetailsRepository recipeDetailsRepository;

    @Transactional
    public SavedRecipe save(SavedRecipe savedRecipe) {
        SavedRecipe savedRecipeWithId = saveRecipeRepository.save(savedRecipe);
        RecipeDetails recipeDetails = savedRecipe.getRecipeDetails();
        recipeDetails.setSavedRecipeId(savedRecipeWithId.getId());
        recipeDetailsRepository.save(recipeDetails);
        savedRecipeWithId.setRecipeDetails(recipeDetails);
        return savedRecipeWithId;
    }

    public List<SavedRecipe> findByUserId(Long userId) {
        List<SavedRecipe> savedRecipes = saveRecipeRepository.findByUserId(userId);
        for (SavedRecipe savedRecipe : savedRecipes) {
            RecipeDetails recipeDetails = recipeDetailsRepository.findBySavedRecipeId(savedRecipe.getId());
            savedRecipe.setRecipeDetails(recipeDetails);
        }
        return savedRecipes;
    }
    
    public List<SavedRecipe> findAll() {
        List<SavedRecipe> savedRecipes = saveRecipeRepository.findAll();
        for (SavedRecipe savedRecipe : savedRecipes) {
            RecipeDetails recipeDetails = recipeDetailsRepository.findBySavedRecipeId(savedRecipe.getId());
            savedRecipe.setRecipeDetails(recipeDetails);
        }
        return savedRecipes;
    }
    
    @Transactional
    public void deleteById(int id) {
        recipeDetailsRepository.deleteById(id);
        saveRecipeRepository.deleteById(id);
    }
    
}
