import { Component, NgZone, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  
  form!: FormGroup;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  redirectToHome = false;

  private subscription: Subscription = new Subscription();
  
  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
    private fb: FormBuilder, private router: Router, private toastr: ToastrService, private ngZone: NgZone) {}

  ngOnInit(): void {
    this.form = this.createForm()
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true
      this.roles = this.tokenStorage.getUser().roles
    }
  }

  private createForm(): FormGroup {
    return this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    this.subscription = this.authService.login(this.form.value).subscribe({
      next: (data) => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data)
  
        this.isLoginFailed = false
        this.isLoggedIn = true
        this.roles = this.tokenStorage.getUser().roles
        this.redirectToHome = true;
        this.reloadPage();
      },
      error: (err) => {
        this.errorMessage = err?.error?.message || 'An error occurred';
        this.isLoginFailed = true
        this.toastr.error('Login failed! Please use the correct username or password', this.errorMessage);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  reloadPage(): void {
    this.ngZone.run(() => {
      this.router.navigate(['/home']).then(() => window.location.reload());
    })
  }
}
