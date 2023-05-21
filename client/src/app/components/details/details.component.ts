import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';


@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit, OnDestroy {

  id!: number;
  param$!: Subscription;
  recipe!: Recipe;
 

  constructor(private activatedRoute: ActivatedRoute, private router: Router,
    private recipeSvc: RecipeService, private fb: FormBuilder) {}

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
  }

  onCommentClick(recipe: Recipe) {
    this.router.navigate(['/comments', recipe.title])
  }

  shareRecipe(recipe: Recipe) {
    this.recipeSvc.shareRecipe(recipe);
  }
  
  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }
  
}