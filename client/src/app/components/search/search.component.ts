import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CUISINES } from '../../constants';
import { RecipeService } from '../../services/recipe.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  form!: FormGroup
  ButtonDisabled: boolean = true
  cuisines = CUISINES
  title!: string

  constructor(private fb: FormBuilder, private router: Router, private recipeSvc: RecipeService,
    private activatedRoute: ActivatedRoute ) {}

  ngOnInit() {
    this.form = this.createForm();
  }    

  onSearchInput() {
    const title = this.form.value.title.trim();
    this.ButtonDisabled = title.length < 2;
  }
  
  onSubmit() {
    const title = this.form?.value['title']
    console.log('>>>title: ', title)
    this.router.navigate(['/list'], {queryParams: {query: title}});
  }

  private createForm(): FormGroup {
    return this.fb.group({
      title: this.fb.control<string>('', [Validators.required, Validators.minLength(2)]),
      cuisine: this.fb.control<string>('', [Validators.required])
    });
  }

}
