import { Component, OnInit, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from '../api/user-service';


@Component({
  selector: 'login-tacocloud',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  singInModel = {
    username: '',
    password: '',
  };

  regitrationModel = {
    username: '',
    password: '',
    verifyPassword: '',
    fullName: '', // Add this line
    street: '',
    city: '', // Remove ".formField" from here
    state: '',
    zip: '',
    phone: '',
};


  constructor(private httpClient: HttpClient, private router: Router, private userService: UserService) { }

  ngOnInit() { }

  onRegisterSubmit() {
      this.httpClient.post(
        'http://localhost:8080/register',
        this.regitrationModel,
        {
          headers: new HttpHeaders().set('Content-type', 'application/json')
                      .set('Accept', 'application/json'),
          observe: 'response'
        }
      ).subscribe(response => {
        if (response.status  === 201) {
          // 페이지를 다시 로드하는 코드
          alert('등록이 완료되었습니다.');
          this.router.navigate(['/ui/login']);
        } else {
          // 다른 상황에 대한 처리
        }
      });
  }

  onSignIn() {
    this.httpClient.post(
      'http://localhost:8080/customLogin',
      this.singInModel,
      {
        headers: new HttpHeaders().set('Content-type', 'application/json')
                    .set('Accept', 'application/json'),
        observe: 'response'
      }
    ).subscribe((response: any) => {
      if (response.status === 200) {
        alert("로그인 성공");

        const user = response.body; // User 객체
        console.log(user);
        
        sessionStorage.setItem('user', JSON.stringify(user));

        this.userService.updateUser(user);
        this.router.navigateByUrl('/').then(() => {
          window.location.reload();
        });
      } else {
        console.log(response); // 응답 객체 로그로 출력
        alert("로그인 실패");
      }
    });
  }
}
