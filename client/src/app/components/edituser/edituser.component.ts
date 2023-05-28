import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edituser',
  templateUrl: './edituser.component.html',
  styleUrls: ['./edituser.component.css']
})
export class EdituserComponent implements OnInit {

  form!: FormGroup;
  userId: number = 0;

  constructor(private userService: UserService, private fb: FormBuilder, private route: ActivatedRoute,
    private router: Router, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.userId = this.route.snapshot.params['id'];
    this.form = this.createForm();

    this.userService.getUserById(this.userId).subscribe
    ((user) => {
      this.form.patchValue(user);
    });
  }

  createForm(): FormGroup {
    return this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      const user = this.form.value;
      this.userService.updateUser(this.userId, user).subscribe(() => {
        this.toastr.success('User updated successfully.', 'Success');
        this.router.navigate(['/listusers']);
      });
    }
  }
}
