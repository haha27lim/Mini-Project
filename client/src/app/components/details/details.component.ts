import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Recipe, SavedRecipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';


@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit, OnDestroy {

  id!: number;
  param$!: Subscription;
  recipe!: Recipe;
  isLoggedIn = false;
  userId!: number | null;
  savedRecipes: SavedRecipe[] = [];

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private recipeSvc: RecipeService,
     private tokenStorageService: TokenStorageService, private toastr: ToastrService) {}

  async ngOnInit(): Promise<void> {
    this.param$ = this.activatedRoute.params.subscribe(async (params) => {
      this.id = params['id'];
      try {
        this.recipe = await this.recipeSvc.getRecipeById(this.id);
        console.log(`Recipe loaded successfully: `, this.recipe);
      } catch (error) {
        console.error(`Error loading recipe: `, error);
      }
    });

    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      this.userId = this.tokenStorageService.getUserId();
      if (this.userId) {
        this.getSavedRecipes(this.userId);
      }
    }
  }

  onCommentClick(recipe: Recipe) {
    this.router.navigate(['/comments', recipe.title])
  }

  shareRecipe(recipe: Recipe) {
    this.recipeSvc.shareRecipe(recipe);
  }

  saveRecipe(recipeId: number): void {
    if (!this.userId) {
      console.error('User ID is not available');
      return;
    }

    const isRecipeSaved = this.savedRecipes.some(
      (savedRecipe) => savedRecipe.recipeId === recipeId
    );
    if (isRecipeSaved) {
      this.toastr.info('Recipe is already saved.', 'Info');
      return;
    }

    const savedRecipe: SavedRecipe = {
      userId: this.userId,
      recipeId,
      recipeTitle: this.recipe.title,
      recipeDetails: {
        savedRecipeId: recipeId,
        servings: this.recipe.servings,
        readyInMinutes: this.recipe.readyInMinutes,
      },
    };

    this.recipeSvc
      .saveRecipe(savedRecipe)
      .then(() => {
        console.log('Recipe saved successfully');
        this.toastr.success('Recipe saved successfully.', 'Success');
        this.getSavedRecipes(this.userId!);
      })
      .catch((error) => {
        console.error('Error saving recipe', error);
      });
  }
  
  
  getSavedRecipes(userId: number): void {
    this.recipeSvc.getSavedRecipes(userId)
      .then(savedRecipes => {
        this.savedRecipes = savedRecipes;
      })
      .catch(error => {
        console.error('Error retrieving saved recipes', error);
      });
  }
  
  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }
  
}