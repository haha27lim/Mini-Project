import { Component, OnInit } from '@angular/core';
import { SavedRecipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-savedrecipe',
  templateUrl: './savedrecipe.component.html',
  styleUrls: ['./savedrecipe.component.css']
})
export class SavedrecipeComponent implements OnInit {

  savedRecipes: SavedRecipe[] = [];
  userId!: number | null;

  constructor(private RecipeSvc: RecipeService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.userId = this.tokenStorageService.getUserId();
    if (this.userId) {
      this.getSavedRecipes(this.userId);
    }
    
  }

  saveRecipe(recipeId: number, recipeTitle: string): void {
    if (!this.userId) {
      console.error('User ID is not available');
      return;
    }

    const savedRecipe: SavedRecipe = {
      userId: this.userId,
      recipeId,
      recipeTitle
    };

    this.RecipeSvc
      .saveRecipe(savedRecipe)
      .then(() => {
        console.log('Recipe saved successfully');
        this.getSavedRecipes(this.userId!);
      })
      .catch(error => {
        console.error('Error saving recipe', error);
      });
  }

  deleteRecipe(savedRecipe: SavedRecipe): void {
    const { id } = savedRecipe;
  
    if (!id) {
      console.error('Recipe ID is not available');
      return;
    }
  
    if (!this.userId) {
      console.error('User ID is not available');
      return;
    }
  
    this.RecipeSvc
      .deleteRecipe(id)
      .then(() => {
        console.log('Recipe deleted successfully');
        this.getSavedRecipes(this.userId!); // Add the non-null assertion operator (!)
      })
      .catch(error => {
        console.error('Error deleting recipe', error);
      });
  }
  

  getSavedRecipes(userId: number): void {
    this.RecipeSvc
      .getSavedRecipes(userId)
      .then(savedRecipes => {
        this.savedRecipes = savedRecipes;
      })
      .catch(error => {
        console.error('Error retrieving saved recipes', error);
      });
  }

  
}
