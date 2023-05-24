package sg.edu.nus.iss.miniproject.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.miniproject.models.SavedRecipe;
import sg.edu.nus.iss.miniproject.models.UserRecipeCount;
import sg.edu.nus.iss.miniproject.services.SaveRecipeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/saverecipes")
public class SavedRecipeController {

    @Autowired
    private SaveRecipeService saveRecipeSvc;

    @GetMapping
    public ResponseEntity<List<SavedRecipe>> findAll() {
        List<SavedRecipe> savedRecipes = saveRecipeSvc.findAll();
        return ResponseEntity.ok(savedRecipes);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavedRecipe>> findByUserId(@PathVariable Long userId) {
        List<SavedRecipe> savedRecipes = saveRecipeSvc.findByUserId(userId);
        return ResponseEntity.ok(savedRecipes);
    }

    @PostMapping
    public ResponseEntity<SavedRecipe> save(@RequestBody SavedRecipe savedRecipe) {
        SavedRecipe saved = saveRecipeSvc.save(savedRecipe);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        saveRecipeSvc.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/group")
    public ResponseEntity<List<UserRecipeCount>> getAllSavedRecipesGroupedByUser() {
        try {
            List<UserRecipeCount> groupedSavedRecipes = saveRecipeSvc.getAllSavedRecipesGroupByUser();
            return ResponseEntity.ok(groupedSavedRecipes);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    
}

