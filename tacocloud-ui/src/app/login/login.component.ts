import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';


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


  constructor(private httpClient: HttpClient, private router: Router) { }

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
      if (response.status === 201) {
        alert('등록이 완료되었습니다.');
        // 사용자 등록 후 로그인 페이지로 자동 이동
        this.router.navigateByUrl('/login').then(() => {
          // 페이지 전환이 완료된 후에 새로고침
          window.location.reload();
        });
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
                    withCredentials: true,
                    observe: 'response'
      }
    ).subscribe((response: any) => {
      if (response.status === 200) {
        alert("로그인 성공");
  
        const user = response.body; // User 객체        
        sessionStorage.setItem('user', JSON.stringify(user));
  
        // Angular 라우팅을 통해 페이지 업데이트
        this.router.navigateByUrl('/').then(() => {
          // 페이지 전환이 완료된 후에 새로고침
          window.location.reload();
        });
      } else {
        console.log(response); // 응답 객체 로그로 출력
        alert("로그인 실패");
      }
    });
  }  
}
