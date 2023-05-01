import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RecipeService } from '../services/recipe.service';
import { Recipe } from '../models/recipe';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  recipes: Recipe[] = []
  title!: string
  queryParams$!: Subscription

  constructor(private router: Router, private recipeSvc: RecipeService,
    private activatedRoute: ActivatedRoute ) {}

  ngOnInit() {
    this.queryParams$ = this.activatedRoute.queryParams.subscribe(
      async (queryParams) => {
        try {
          this.recipes = await this.recipeSvc.getRandom(9); // load 9 random recipes
          console.log('Recipes:', this.recipes);
        } catch (err) {
          console.log(err);
        }
      }
    );
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
