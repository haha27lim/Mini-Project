import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_BASE_URL = '/api/auth';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  login(credentials:any): Observable<any> {
    return this.httpClient.post(`${API_BASE_URL}/signin`, {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user: any): Observable<any> {
    return this.httpClient.post(`${API_BASE_URL}/signup`, {
      username: user.username,
      email: user.email,
      password: user.password
    }, httpOptions);
  }
}
