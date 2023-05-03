import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CUISINES, DIETS } from 'src/app/constants';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-advanced-search-results',
  templateUrl: './advanced-search-results.component.html',
  styleUrls: ['./advanced-search-results.component.css']
})
export class AdvancedSearchResultsComponent implements OnInit, OnDestroy {
  
  title!: string
  queryParams$!: Subscription
  recipes: Recipe[] = []
  cuisine!: string
  addRecipeInformation!: boolean
  number!: number
  diet?: string
  excludeIngredients?: string
  cuisines = CUISINES
  diets = DIETS

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService, 
    private router: Router) {}

  ngOnInit(): void {
    this.queryParams$ = this.activatedRoute.queryParams.subscribe((params) => {
      this.title = params['query'];
      this.cuisine = params['cuisine'];
      this.addRecipeInformation = params['addRecipeInformation'] === 'true';
      this.number = params['number'];
      this.diet = params['diet'];
      this.excludeIngredients = params['excludeIngredients'];
      this.recipeSvc.getSearch(this.cuisine, this.title, this.addRecipeInformation, 32,
        this.diet, this.excludeIngredients)
       .then(recipes => this.recipes = recipes);
    });
  }
  

  showRecipeDetails(id: number) {
    if (this.recipes.length > 0) {
      this.router.navigate(['/details', id]);
    }
  }

  ngOnDestroy(): void {
    this.queryParams$.unsubscribe()
  }
}
