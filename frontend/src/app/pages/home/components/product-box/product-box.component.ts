import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';

@Component({
  selector: '[app-product-box]',
  templateUrl:'./product-box.component.html'
})
export class ProductBoxComponent {
  @Input() fullWidthMode= false;
  @Input() product: Product | undefined;
  @Output()addToCart = new EventEmitter();

  constructor(private router: Router){}

  onAddToCart():void{
    if (this.isUserLoggedIn()) {
    this.addToCart.emit(this.product)}
    else{
      this.router.navigate(['/login']);
    }
  }
  isUserLoggedIn(): boolean {
    const token = localStorage.getItem('authToken');
    return !!token; 
  }
}
