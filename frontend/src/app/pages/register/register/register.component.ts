import { Component } from '@angular/core';
import { FormBuilder, FormGroup, RequiredValidator, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterUser } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl:'./register.component.html',
  styleUrls: ['./register.component.css']})
  

export class RegisterComponent {
  registerForm: FormGroup;
  
 registerUser: RegisterUser = {
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  };
  constructor(private fb: FormBuilder, private authService: AuthService,private router: Router) {
    this.registerForm = this.fb.group({
      firstName:['',[Validators.required,Validators.pattern(/^[A-Za-z]+$/),Validators.minLength(4)]],
      lastName:['',[Validators.required,Validators.pattern(/^[A-Za-z]+$/),Validators.minLength(4)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  register(registerForm: FormGroup) {
    if (registerForm.valid) {
      const firstName = registerForm.get('firstName')?.value;
      const lastName = registerForm.get('lastName')?.value;
      const email = registerForm.get('email')?.value;
      const password = registerForm.get('password')?.value;
  
      const registerUser: RegisterUser = {
        firstName,
        lastName,
        email,
        password
      };
  
      this.authService.register(registerUser).subscribe(() => {
        this.router.navigate(['/login']);
      });
    }
  }
  
}
