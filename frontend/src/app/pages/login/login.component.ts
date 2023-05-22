import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import {  User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoginError =false;

  user: User = {
    email: '',
    password: ''
  };

  constructor(private fb: FormBuilder, private authService: AuthService,private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }
 
  logout() {
    const token = localStorage.getItem('authToken');
  
    if (token) {
      this.authService.logout(token)
        .pipe(take(1))
        .subscribe({
          next: () => {
            this.authService.isLogin = false;
            localStorage.clear()
          },
          error: (error) => {
            console.error(error);
          }
        });
    } else {
      console.log('Token not found in localStorage');
    }
  }
  
  
login() {
  if (this.loginForm.valid) {
    this.user = this.loginForm.value;
    this.authService.login(this.user).subscribe({
      next: (token: string) => {
        localStorage.setItem('authToken', token);
        this.router.navigate(['']);
        
        this.authService.isLogin = true;
      },
      error: (error) => {
        console.error(error);
        if (error.status === 403) {
          console.log("Incorrect e-mail or password");
          this.isLoginError = true;
        }
      }
    });
  }
}

}