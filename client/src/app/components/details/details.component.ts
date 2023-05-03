import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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

  id!: number;
  param$!: Subscription;
  recipe!: Recipe;
  form!: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private router: Router,
    private recipeSvc: RecipeService, private fb: FormBuilder) {}

  async ngOnInit(): Promise<void> {
    this.param$ = this.activatedRoute.params.subscribe(async (params) => {
      this.id = params['id'];
      try {
        this.recipe = await this.recipeSvc.getRecipeById(this.id);
        console.log(`Recipe loaded successfully: `, this.recipe);
      } catch (error) {
        console.error(`Error loading recipe: `, error);
      }
    });
    this.form = this.createForm();
  }

  private createForm(): FormGroup {
    return this.fb.group ({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      subject: this.fb.control<string>('', [Validators.required]),
      message: this.fb.control<string>('', [Validators.required])
    })
  }

  onSubmit() {

  }
  
  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }
}