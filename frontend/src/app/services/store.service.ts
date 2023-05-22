import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
const STORE_BASE_URL ='http://localhost:8080/api/v1'

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private httpClient: HttpClient) { }

  getAllProduct(category?:string,limit='10'):  Observable<Array<Product>> {
   
    return this.httpClient.get<Array<Product>>(
      `${STORE_BASE_URL}/films${category ? '/category/' + category : ''}?limit=${limit}`
    );
  }

  getAllCategories():Observable<Array<string>>{
    return this.httpClient.get<Array<string>>(
      `${STORE_BASE_URL}/films/categories`
    )
  }
}
