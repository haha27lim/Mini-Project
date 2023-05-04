import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RecipeService } from '../services/recipe.service';
import { Recipe } from '../models/recipe';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [NgbCarouselConfig]
})
export class HomeComponent implements OnInit {

  popularRecipes: Recipe[] = []
  vegRecipes: Recipe[] = []
  italian = 'italian'
  german = 'german'
  chinese = 'chinese'
  japanese = 'japanese'

  constructor(private router: Router, private recipeSvc: RecipeService,
    private activatedRoute: ActivatedRoute, private config: NgbCarouselConfig ) {
    config.interval = 5000;
    config.wrap = true;
    config.keyboard = false;
    config.pauseOnHover = false;
  }

  ngOnInit() {
    // cache to local storage because random on homepage
    const popular = localStorage.getItem('popular')
    if (popular) {
      this.popularRecipes = JSON.parse(popular)
    } else {
      this.recipeSvc.getRandom(6).then((popular) => {
        console.log('Popular recipes:', popular)
        localStorage.setItem('popular', JSON.stringify(popular))
        this.popularRecipes = popular
      }).catch((err) => {
        console.log(err);
      });
    }

    const vegetarian = localStorage.getItem('vegetarian')
    if (vegetarian) {
      this.vegRecipes = JSON.parse(vegetarian)
    } else {
      this.recipeSvc.getRandom(9, 'vegetarian').then((vegetarian) => {
        console.log('Vegetarian recipes:', vegetarian)
        localStorage.setItem('vegetarian', JSON.stringify(vegetarian))
        this.vegRecipes = vegetarian
      }).catch((err) => {
        console.log(err);
      });
    }
  }        

  showRecipeDetails(id: number) {
    if (this.popularRecipes.length > 0 || this.vegRecipes.length > 0) {
      this.router.navigate(['/details', id])
    }
  }

  
}
