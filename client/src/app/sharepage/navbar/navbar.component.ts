import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  form!: FormGroup
  ButtonDisabled: boolean = true;

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit() {
    this.form = this.createForm();
  }    

  onSearchInput() {
    const title = this.form.value.title.trim();
    this.ButtonDisabled = title.length < 2;
  }

  onSubmit() {
    const title = this.form.value.title.trim();
  }

  private createForm(): FormGroup {
    return this.fb.group ({
      title: this.fb.control<string>('', [Validators.required, Validators.minLength(2)])
    })
  }
}
