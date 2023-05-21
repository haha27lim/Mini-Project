package sg.edu.nus.iss.miniproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.miniproject.models.SavedRecipe;
import sg.edu.nus.iss.miniproject.services.SaveRecipeService;

@RestController
@RequestMapping("/api/saverecipes")
public class SavedRecipeController {

    @Autowired
    private SaveRecipeService saveRecipeSvc;

    @PostMapping
    public SavedRecipe save(@RequestBody SavedRecipe savedRecipe) {
        return saveRecipeSvc.save(savedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        saveRecipeSvc.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public List<SavedRecipe> findByUserId(@PathVariable Long userId) {
        return saveRecipeSvc.findByUserId(userId);
    }

    @GetMapping
    public List<SavedRecipe> findAll() {
        return saveRecipeSvc.findAll();
    }
}

