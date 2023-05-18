import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-random-roulette',
  templateUrl: './random-roulette.component.html',
  styleUrls: ['./random-roulette.component.css']
})
export class RandomRouletteComponent implements OnInit, OnDestroy {
  recipes: Recipe[] = []
  title!: string
  queryParams$!: Subscription

  constructor(private router: Router, private recipeSvc: RecipeService,
    private activatedRoute: ActivatedRoute ) {}

  ngOnInit() {
    this.queryParams$ = this.activatedRoute.queryParams.subscribe
    (async (queryParams) => {
      this.title = queryParams['query'];
    });
  }      

  async generateRecipe() {
    try {
      this.recipes = await this.recipeSvc.getRandom(9);
      console.log('Recipes:', this.recipes);
    } catch (err) {
      console.log(err);
    }
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
