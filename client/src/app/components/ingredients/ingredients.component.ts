import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ingredients',
  templateUrl: './ingredients.component.html',
  styleUrls: ['./ingredients.component.css']
})
export class IngredientsComponent implements OnInit{

  form!: FormGroup
  ingredients!: string;
  
  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit() {
    this.form = this.createForm();
  } 

  onSubmit() {
    const ingredients = this.form?.value['ingredients']
    console.log('>>>ingredients: ', ingredients)
    this.router.navigate(['/dishbyingredients/ingredientssearchresult'], { queryParams: { ingredients: ingredients } });
  }

  private createForm(): FormGroup {
    return this.fb.group({
      ingredients: this.fb.control<string>('', [Validators.required])
    });
  }
}
