import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import { AuthService } from "./auth.service";

@Injectable({
    providedIn: 'root'
  })
  export class AuthGuard {

    constructor(private authService: AuthService, private router: Router) {}
  
    canActivate(route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): boolean | UrlTree {

        if (!this.authService.isLoggedIn()) {
            alert('You are not allowed to view this page. You are redirected to login Page');

            this.router.navigate(['/login'])
            return false;
        }
        return true;
    }
  }
