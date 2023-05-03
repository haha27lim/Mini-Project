import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { TYPES } from 'src/app/constants';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';


@Component({
  selector: 'app-typelist',
  templateUrl: './typelist.component.html',
  styleUrls: ['./typelist.component.css']
})
export class TypelistComponent implements OnInit, OnDestroy{

  param$!: Subscription
  recipes: Recipe[] = []
  type: string = '';

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService, 
    private router: Router) {}
  
  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(async (params) => {
      const type = params['type'];
      this.type = type;
      const recipes = await this.recipeSvc.getRecipesType(type, true, 20);
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
