import { Component, OnInit,OnDestroy } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import {Subscription} from 'rxjs';
import { StoreService } from 'src/app/services/store.service';
import { CartService } from 'src/app/services/cart.service';
const ROWS_HEIGHT: {[id:number]:number} ={ 1: 400, 3: 335, 4: 350}
@Component({
  selector: 'app-home',
  templateUrl:'./home.component.html',
})
export class HomeComponent implements OnInit,OnDestroy{
  cols=3;
  rowHeight=ROWS_HEIGHT[this.cols]
  category: string|undefined;
  products: Array<Product> | undefined;
  productsSubscription: Subscription | undefined
  count='10'
  constructor(private storeService: StoreService, private cartService:CartService){}

 

  ngOnInit(): void{
    this.getProducts();
  }

  getProducts():void{
   this.productsSubscription = this.storeService
    .getAllProduct(this.category,this.count)
    .subscribe((_products) => {
      this.products = _products;
    });
  }

  onColumsCountChange(colsNum:number): void{
      this.cols = colsNum;
      this.rowHeight=ROWS_HEIGHT[this.cols];
  }
  onShowCategory(newCategory: string): void{
    this.category=newCategory;
    this.getProducts();
  }

  onItemsCountChange(newCount: number): void {
    this.count = newCount.toString();
    this.getProducts();
  }

  onAddToCart(product:Product):void{
    this.cartService.addToCart({
      product: product.imageUrl,
      title: product.title,
      price:product.price,
      quantity:1,
      id:product.id
    })
  }


   ngOnDestroy(): void {
    if(this.productsSubscription){
      this.productsSubscription.unsubscribe();
    }
  }
}
