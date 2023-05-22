export interface Cart{
    items: Array<CartItem>;
}


export interface CartItem{
    product: String;
    price: number;
    quantity: number;
    id:number;
    title:String;
}