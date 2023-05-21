package sg.edu.nus.iss.miniproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.miniproject.models.SavedRecipe;
import sg.edu.nus.iss.miniproject.repositories.SavedRecipeRepository;

@Service
public class SavedRecipeService {
    
    @Autowired
    private SavedRecipeRepository savedRecipeRepository;

    public SavedRecipe save(SavedRecipe savedRecipe) {
        return savedRecipeRepository.save(savedRecipe);
    }

    public void deleteById(Long id) {
        savedRecipeRepository.deleteById(id);
    }

    public List<SavedRecipe> findByUserId(Long userId) {
        return savedRecipeRepository.findByUserId(userId);
    }

    public List<SavedRecipe> findAll() {
        return savedRecipeRepository.findAll();
    }
}
