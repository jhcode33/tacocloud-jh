import { Component, OnInit, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'login-tacocloud',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  model = {
    username: '',
    password: '',
    verifyPassword: '',
    fullName: '', // Add this line
    street: '',
    city: '', // Remove ".formField" from here
    state: '',
    zip: '',
    phone: '',
    login: [] as any[]
};


  constructor(private httpClient: HttpClient) { }

  ngOnInit() { }

  onSubmit() {
      this.httpClient.post(
        'http://localhost:8080/register',
        this.model,
        {
          headers: new HttpHeaders().set('Content-type', 'application/json')
                      .set('Accept', 'application/json'),
        }
      ).subscribe(response => {
        // Handle response if needed
      });
  }
  
}
