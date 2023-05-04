import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RecipeIngredient } from 'src/app/models/recipeIngredient';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-ingredients-search-result',
  templateUrl: './ingredients-search-result.component.html',
  styleUrls: ['./ingredients-search-result.component.css']
})
export class IngredientsSearchResultComponent {

  ingredients!: string;
  ranking!: number;
  number!: number;
  queryParams$!: Subscription
  recipeIngredient: RecipeIngredient[] = []

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService, 
    private router: Router) {}

  ngOnInit(): void {
    this.queryParams$ = this.activatedRoute.queryParams.subscribe(
      async (params) => {
        this.ingredients = params['ingredients'];
        this.ranking = params['ranking'];
        this.number = params['number'];
        try {
          const recipeIngredient = await this.recipeSvc.getRecipesIngredients(this.ingredients, 2, 20);
          console.log("recipeIngredient received:", recipeIngredient);
          this.recipeIngredient = recipeIngredient;
        } catch (error) {
          console.error(error);
        }
      }
    );
  }

  showRecipeDetails(id: number) {
    if (this.recipeIngredient.length > 0) {
      this.router.navigate(['/details', id]);
    }
  }

  ngOnDestroy(): void {
    this.queryParams$.unsubscribe()
  }
}
