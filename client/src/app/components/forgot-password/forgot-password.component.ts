import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  form!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.form = this.createForm();
  }

  private createForm(): FormGroup {
    return this.fb.group ({
      email: this.fb.control<string>('', [Validators.required, Validators.email])
    })
  }

  isFormInvalid(): boolean {
    return this.form.invalid || this.form.value['email'] === '';
  }

  onSubmit() {

  }

}
