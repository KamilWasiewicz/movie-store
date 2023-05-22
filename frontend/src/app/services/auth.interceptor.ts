import {
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
  } from '@angular/common/http';
  import { Injectable } from '@angular/core';
  import { Observable } from 'rxjs';
  
  @Injectable()
  export class AuthInterceptor implements HttpInterceptor {
    intercept(
      req: HttpRequest<any>,
      next: HttpHandler
    ): Observable<HttpEvent<any>> {
      const tokenString = localStorage.getItem('authToken');
      const token = tokenString ? JSON.parse(tokenString).token : null;      
  
      if (token) {
        req = req.clone({
          setHeaders: { Authorization: `Bearer ${token}` },
          
        });
      }
  
      return next.handle(req);
    }
  }