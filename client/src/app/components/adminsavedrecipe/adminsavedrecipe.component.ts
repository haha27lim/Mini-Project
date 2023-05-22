import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SavedRecipe } from 'src/app/models/recipe';
import { User } from 'src/app/models/user';
import { RecipeService } from 'src/app/services/recipe.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-adminsavedrecipe',
  templateUrl: './adminsavedrecipe.component.html',
  styleUrls: ['./adminsavedrecipe.component.css']
})
export class AdminsavedrecipeComponent implements OnInit {

  savedRecipes: SavedRecipe[] = [];
  userId!: number | null;
  form!: FormGroup;


  constructor(private RecipeSvc: RecipeService, private router: Router, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.createForm();
    
  }

  private createForm(): FormGroup{
    return this.fb.group({
      userId: this.fb.control<string>('', [Validators.required])
    })
  }

  searchUser(): void {
    const userId = this.form.get('userId')?.value;
  
    if (!userId) {
      console.error('User id is not available');
      return;
    }
  
    this.getSavedRecipes(userId);
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
