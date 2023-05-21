package sg.edu.nus.iss.miniproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.miniproject.models.SavedRecipe;
import sg.edu.nus.iss.miniproject.repositories.SaveRecipeRepository;

@Service
public class SaveRecipeService {
    
    @Autowired
    private SaveRecipeRepository saveRecipeRepo;

    public SavedRecipe save(SavedRecipe savedRecipe) {
        return saveRecipeRepo.save(savedRecipe);
    }

    public void deleteById(Long id) {
        saveRecipeRepo.deleteById(id);
    }

    public List<SavedRecipe> findByUserId(Long userId) {
        return saveRecipeRepo.findByUserId(userId);
    }

    public List<SavedRecipe> findAll() {
        return saveRecipeRepo.findAll();
    }
}
