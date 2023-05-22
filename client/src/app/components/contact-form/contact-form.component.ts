import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RecipeService } from 'src/app/services/recipe.service';


@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {

  form!: FormGroup;

  constructor(private fb: FormBuilder, private recipeSvc: RecipeService) { }

  ngOnInit(): void {
    this.form = this.createForm()
  }

  private createForm(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required, Validators.minLength(3)],
      email: ['', [Validators.required, Validators.email]],
      subject: ['', [Validators.required]],
      message: ['', Validators.required]
    });
  }

  submitForm(): void {
    if (this.form.invalid) {
      return;
    }

    this.recipeSvc.sendContactEmail(this.form.value)
      .then(() => {
        alert('Contact Us Form submitted successfully! We will get back.');
        this.resetForm();
      })
      .catch(error => {
        console.error('Error submitting form:', error);
        alert('Error submitting form. Try again later.');
      });
  }

  resetForm(): void {
    this.form.reset();
  }
}