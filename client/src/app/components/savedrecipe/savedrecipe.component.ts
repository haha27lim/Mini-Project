import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(private RecipeSvc: RecipeService, private tokenStorageService: TokenStorageService,
    private router: Router) { }

  ngOnInit(): void {
    this.userId = this.tokenStorageService.getUserId();
    if (this.userId) {
      this.getSavedRecipes(this.userId);
    }
    
  }

  getSavedRecipes(userId: number): void {
    this.RecipeSvc.getSavedRecipes(userId)
      .then(savedRecipes => {
        this.savedRecipes = savedRecipes;
      })
      .catch(error => {
        console.error('Error retrieving saved recipes', error);
      });
  }

  showRecipeDetails(id: number) {
    if (this.savedRecipes.length > 0) {
      this.router.navigate(['/details', id])
    }
  }

  deleteRecipe(savedRecipe: SavedRecipe): void {
    const { id } = savedRecipe;
  
    if (!id) {
      console.error('Recipe Id is not available');
      return;
    }
  
    if (!this.userId) {
      console.error('User Id is not available');
      return;
    }
  
    this.RecipeSvc.deleteRecipe(id)
      .then(() => {
        console.log('Recipe deleted successfully')
        this.getSavedRecipes(this.userId!)
      })
      .catch(error => {
        console.error('Error deleting recipe', error);
      });
  }

}
