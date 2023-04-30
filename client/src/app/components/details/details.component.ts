import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';



@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit, OnDestroy {

  recId = "";
  param$!: Subscription;
  recipe!: Recipe;

  constructor(private activatedRoute: ActivatedRoute, private router: Router,
    private recipeSvc: RecipeService) {}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      async (params) => {
        this.recId = params['recId'];    
      }
    )

  }

  ngOnDestroy(): void {
      this.param$.unsubscribe();
  }
}

// this.recipe = await this.recipeSvc.getRecipeById(Number(this.recId));