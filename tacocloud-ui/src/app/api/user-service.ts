import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userSubject = new Subject<any>();
  userObservable$ = this.userSubject.asObservable();

  constructor() { }

  updateUserFromSession() {
    const sessionUser = sessionStorage.getItem('user');
    if (sessionUser) {
      const sessionUserJSON = JSON.parse(sessionUser);
      this.userSubject.next(sessionUserJSON);
    }
  }
  
  updateUser(user: any) {
    this.userSubject.next(user);
  }

  clearUser() {
    this.userSubject.next(null);
    sessionStorage.removeItem('user');
  }
}


