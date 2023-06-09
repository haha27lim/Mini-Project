import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Recipe } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipelist',
  templateUrl: './recipelist.component.html',
  styleUrls: ['./recipelist.component.css']
})
export class RecipelistComponent implements OnInit {

  maincourseRecipes: Recipe[] = []
  soupRecipes: Recipe[] = []
  breakfastRecipes: Recipe[] = []
  dessertRecipes: Recipe[] = []
  vegan = 'vegan'
  appetizer = 'appetizer'
  vietnamese = 'vietnamese'
  european = 'european'

  constructor(private router: Router, private recipeSvc: RecipeService) { }

  ngOnInit() {
    // cache it to session storage
    const mainCourse = sessionStorage.getItem('mainCourse');
    const soup = sessionStorage.getItem('soup');
    const breakfast = sessionStorage.getItem('breakfast');
    const dessert = sessionStorage.getItem('dessert');

    if (mainCourse) {
      this.maincourseRecipes = JSON.parse(mainCourse);
    } else {
      this.recipeSvc.getRandom(8, 'dinner').then((maincourse) => {
        console.log('Main course recipes:', maincourse);
        sessionStorage.setItem('mainCourse', JSON.stringify(maincourse));
        this.maincourseRecipes = maincourse;
      }).catch((err) => {
        console.log(err);
      });
    }

    if (soup) {
      this.soupRecipes = JSON.parse(soup);
    } else {
      this.recipeSvc.getRandom(8, 'soup').then((soup) => {
        console.log('Soup recipes:', soup);
        sessionStorage.setItem('soup', JSON.stringify(soup));
        this.soupRecipes = soup;
      }).catch((err) => {
        console.log(err);
      });
    }

    if (breakfast) {
      this.breakfastRecipes = JSON.parse(breakfast);
    } else {
      this.recipeSvc.getRandom(8, 'breakfast').then((breakfast) => {
        console.log('Breakfast recipes:', breakfast);
        sessionStorage.setItem('breakfast', JSON.stringify(breakfast));
        this.breakfastRecipes = breakfast;
      }).catch((err) => {
        console.log(err);
      });
    }

    if (dessert) {
      this.dessertRecipes = JSON.parse(dessert);
    } else {
      this.recipeSvc.getRandom(8, 'dessert').then((dessert) => {
        console.log('Dessert recipes:', dessert);
        sessionStorage.setItem('dessert', JSON.stringify(dessert));
        this.dessertRecipes = dessert;
      }).catch((err) => {
        console.log(err);
      });
    }
  }
    

  showRecipeDetails(id: number) {
    if (this.maincourseRecipes.length > 0 || this.soupRecipes.length > 0 ||
      this.breakfastRecipes.length > 0 || this.dessertRecipes.length > 0) {
      this.router.navigate(['/details', id])
    }
  }

}
