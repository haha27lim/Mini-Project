import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';
import { BASE_URL } from '../constants';


const RECIPE_URL = `${BASE_URL}/api/auth`;

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private tokenStorageService: TokenStorageService) { }

  login(credentials:any): Observable<any> {
    return this.httpClient.post(`${RECIPE_URL}/signin`, {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user: any): Observable<any> {
    return this.httpClient.post(`${RECIPE_URL}/signup`, {
      username: user.username,
      email: user.email,
      password: user.password
    }, httpOptions);
  }

  isLoggedIn(): boolean {
    const token = this.tokenStorageService.getToken();
    return token !== null && token !== undefined && token !== '';
  }
}
