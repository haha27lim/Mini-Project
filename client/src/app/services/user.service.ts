import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { User } from '../models/user';

const BASE_URL = 'https://typical-deer-production.up.railway.app';

const CONTROL_URL = `${BASE_URL}/api/control`;
const USERS_URI = `${BASE_URL}/api/users`;


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.httpClient.get(`${CONTROL_URL}/all`, { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.httpClient.get(`${CONTROL_URL}/user`, { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.httpClient.get(`${CONTROL_URL}/admin`, { responseType: 'text' });
  }

  getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(USERS_URI);
  }

  getUserById(id: number): Observable<User> {
    return this.httpClient.get<User>(`${USERS_URI}/${id}`);
  }

  createUser(user: User): Observable<User> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = {
        username: user.username,
        email: user.email,
        password: user.password,
        roles: Array.isArray(user.roles) ? user.roles.map(role => role.name) : []
    };
    return this.httpClient.post<User>(USERS_URI, body, { headers });
}
  
  updateUser(id: number, user: User): Observable<User> {
    return this.httpClient.put<User>(`${USERS_URI}/${id}`, user);
  }

  deleteUser(id: number): Observable<string> {
    return this.httpClient.delete<any>(`${USERS_URI}/${id}`).pipe(
      map(() => 'User deleted successfully.'),
      catchError(() => 'User deletion failed.')
    );
  }
  
  
  
}