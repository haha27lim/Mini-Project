import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CUISINES } from '../constants';
import { Subscription } from 'rxjs';
import { RecipeService } from '../services/recipe.service';
import { Recipe } from '../models/recipe';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  recipes: Recipe[] = []
  title!: string
  queryParams$!: Subscription

  constructor(private router: Router, private recipeSvc: RecipeService,
    private activatedRoute: ActivatedRoute ) {}

  ngOnInit() {
    this.queryParams$ = this.activatedRoute.queryParams.subscribe(
      async (queryParams) => {
        this.title = queryParams['query'];
        try {
          this.recipes = await this.recipeSvc.getRandom(9); // load 9 random recipes
          console.log('Recipes:', this.recipes);
        } catch (err) {
          console.log(err);
        }
      }
    );
  }      

  showRecipeDetails(recipe: Recipe) {
    if (this.recipes.length > 0) {
      this.router.navigate(['/details', recipe.id]);
    }
  }
  

  ngOnDestroy(): void {
    this.queryParams$.unsubscribe()
  }

  
}
