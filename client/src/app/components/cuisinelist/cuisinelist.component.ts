import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-cuisinelist',
  templateUrl: './cuisinelist.component.html',
  styleUrls: ['./cuisinelist.component.css']
})
export class CuisinelistComponent implements OnInit, OnDestroy{

  param$!: Subscription
  recipes: Recipe[] = []
  cuisine: string = '';

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService, 
    private router: Router) {}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(async (params) => {
      const cuisine = params['cuisine'];
      this.cuisine = cuisine;
      const recipes = await this.recipeSvc.getRecipesCuisine(cuisine, true, 32);
      this.recipes = recipes;
    });
  }

  showRecipeDetails(id: number) {
    if (this.recipes.length > 0) {
      this.router.navigate(['/details', id]);
    }
  }

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }
}
