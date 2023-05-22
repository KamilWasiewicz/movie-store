import { Component, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Cart, CartItem } from 'src/app/models/cart.model';
import { LoginComponent } from 'src/app/pages/login/login.component';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',

})
export class HeaderComponent {
  hover: boolean = false;
  hover1: boolean = false;
  private _cart: Cart = { items: [] };
  itemsQuantity = 0;
  logout=false
  @Input()
  get cart(): Cart {
    return this._cart;
  }

  set cart(cart: Cart) {
    this._cart = cart;

    this.itemsQuantity = cart.items
      .map((item) => item.quantity)
      .reduce((prev, curent) => prev + curent, 0);
  }

  constructor(private _snackBar: MatSnackBar, private cartService: CartService, public authService:AuthService,private loginComponent:LoginComponent ) {}

  getTotal(items: CartItem[]): number {
    return this.cartService.getTotal(items);
  }

  onClearCart(): void {
    this.cartService.clearCart();
  }
  checkLoginStatus():boolean {    
   this.logout =  this.authService.isLogin;
   return this.logout;
  }
  onLogout() {
    this.loginComponent.logout();
    if(this.logout=true)
    {
     this._snackBar.open('You are logged out.', 'Ok', {
       duration: 2000,
     });
    }
  }
}