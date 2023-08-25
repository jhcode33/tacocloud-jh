import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart/cart-service';

@Component({
  selector: 'taco-header',
  templateUrl: 'header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  cart: CartService;
  user: any; // Declare a variable to store the user object

  constructor(cartService: CartService) {
    this.cart = cartService;
  }

  ngOnInit() {
    // Retrieve user object from sessionStorage
    const userString = sessionStorage.getItem('user');
    if (userString) {
      this.user = JSON.parse(userString); // Parse the user object from JSON
    }
  }

  logout() {
    // 로그아웃 처리: 세션에서 사용자 객체 삭제 및 페이지 새로고침
    sessionStorage.removeItem('user');
    this.user = null;
    window.location.reload();
  }
}
