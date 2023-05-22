import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-product-header',
  templateUrl:'./product-header.componetns.html',
  styles: [
  ]
})

export class ProductHeaderComponent {
  @Output() columsCountChange = new EventEmitter<number>();
  @Output() itemsCountChange = new EventEmitter<number>();
  itemsShowCount = 10;
  
  onItemsUpdated(count: number): void {
    this.itemsCountChange.emit(count);
    this.itemsShowCount = count;
  }

  onColumsUpdated(colsNum: number): void{
    this.columsCountChange.emit(colsNum);
  }
}
