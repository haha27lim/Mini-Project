import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RecipeNutrients } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-nutrientlist',
  templateUrl: './nutrientlist.component.html',
  styleUrls: ['./nutrientlist.component.css']
})
export class NutrientlistComponent implements OnInit, OnDestroy {

  param$!: Subscription
  recipeNutrients: RecipeNutrients[] = []
  cuisine: string = '';

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService, 
    private router: Router) {}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(async (params) => {
      const cuisine = params['cuisine'];
      this.cuisine = cuisine;
      const recipes = await this.recipeSvc.getRecipesNutrients(100, 200, 40, 32);
      this.recipeNutrients = recipes;
    });
  }

  showRecipeDetails(id: number) {
    if (this.recipeNutrients.length > 0) {
      this.router.navigate(['/details', id]);
    }
  }

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }
}
