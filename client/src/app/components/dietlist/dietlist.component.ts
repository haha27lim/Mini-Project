import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-dietlist',
  templateUrl: './dietlist.component.html',
  styleUrls: ['./dietlist.component.css']
})
export class DietlistComponent implements OnInit, OnDestroy {
  
  param$!: Subscription
  recipes: Recipe[] = []
  diet: string = '';

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService, 
    private router: Router) {}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(async (params) => {
      const diet = params['diet'];
      this.diet = diet;
      const recipes = await this.recipeSvc.getRecipesDiet(diet, true, 32);
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
