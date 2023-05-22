import { Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import { Subscription } from 'rxjs';
import { StoreService } from 'src/app/services/store.service';

@Component({
  selector: 'app-category',
  templateUrl:'./category.component.html'
})
export class CategoryComponent implements OnInit, OnDestroy{
  @Output() showCategory= new EventEmitter<string>();
  categoriesSubscription:Subscription|undefined;
  categories:string[]|undefined;

  constructor(private storeService: StoreService){
  }

  ngOnInit():void{
     this.categoriesSubscription = this.storeService.getAllCategories()
      .subscribe((response: Array<string>) =>{
      this.categories = response;
    });
  }

  onShowCategory(category: string): void
  {
    this.showCategory.next(category);
    
  }
  showAllProduct():void{
    this.showCategory.next('')
  }

  ngOnDestroy(): void {
    if(this.categoriesSubscription){
        this.categoriesSubscription.unsubscribe();
    }
  }
}
