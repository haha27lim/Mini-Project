import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CUISINES, TYPES } from 'src/app/constants';
import { Recipe } from 'src/app/models/recipe';

const DIETS = [
  "vegetarian", "vegan", "gluten free", "ketogenic", "lacto-vegetarian", "ovo-vegetarian", 
  "pescetarian", "paleo", "primal", "low FODMAP", "whole30"
]

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  recipes: Recipe[] = []
  form!: FormGroup
  ButtonDisabled: boolean = true;
  types = TYPES
  cuisines = CUISINES
  diets = DIETS


  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit() {
    this.form = this.createForm();
  }    

  onSearchInput() {
    const title = this.form.value.title.trim();
    this.ButtonDisabled = title.length < 2;
  }

  onSubmit() {
    const title = this.form?.value['title'];
    const addRecipeInformation = true;
    this.router.navigate(['/searchresult'], {queryParams: {query: title, addRecipeInformation}});
  }

  private createForm(): FormGroup {
    return this.fb.group ({
      title: this.fb.control<string>('', [Validators.required, Validators.minLength(2)])
    })
  }


}
