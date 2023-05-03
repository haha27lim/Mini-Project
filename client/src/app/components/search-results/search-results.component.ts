import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit, OnDestroy {

  title!: string
  queryParams$!: Subscription
  recipes: Recipe[] = []

  constructor(private activatedRoute: ActivatedRoute, private recipeSvc: RecipeService, 
    private router: Router) {}

  ngOnInit() {
    this.queryParams$ = this.activatedRoute.queryParams.subscribe(
      async (queryParams) => {
        this.title = queryParams['query']
        console.log("Getting results for.. " + this.title)
        this.recipeSvc.getGenSearch(this.title, true, 20)
          .then(recipes => {
            this.recipes = recipes;
          })
      }
    )
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
