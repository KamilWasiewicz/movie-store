import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterUser, User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLogin = false;
  constructor(private httpClient: HttpClient) { }

  public register(registerUser:RegisterUser):Observable<string>{
    return this.httpClient.post('http://localhost:8080/api/v1/register',registerUser,{responseType:'text'})
  }

  public login(user:User):Observable<string>{
    return this.httpClient.post('http://localhost:8080/api/v1/login',user
    ,{responseType:'text'})
  }
  public logout(token: string): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/v1/logout', {
      headers: {
        Authorization: `Bearer ${token}`
      },
      responseType: 'text'
    });
  }
}
