import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { environment } from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl = 'http://localhost:8079';

  constructor(
    private http: HttpClient
  ) { }

  login(email: string, password: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const url = `${this.apiUrl}/user/login`;
    const body = { email, password };

    return this.http.post(url, body).pipe(
      catchError((error) => {
        console.error('Login failed:', error);
        return throwError(error);
      })
    );
  }

  register(name: string, email: string, password: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = { name, email, password };

    return this.http.post(`${this.apiUrl}/user`, body, { headers }).pipe(
      catchError((error) => {
        console.error('Error during registration:', error);
        return throwError(error);
      })
    );
  }
}
