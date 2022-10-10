import { Component, OnInit } from '@angular/core';
import { userLoginModel } from '../userLoginModel';
import { Router } from '@angular/router';
import { UserServiceService } from '../user-service.service';
import {MessageService} from 'primeng/api';
@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css'],
  providers: [MessageService]
})
export class UserLoginComponent implements OnInit {
  loader=false;
  constructor(private router: Router, private userService:UserServiceService,private messageService: MessageService) {


   }



  ngOnInit(): void {
  }
  model = new userLoginModel('example@mail.com','password');

  submitted = false;
  result="";

  loginUser() { 
  //  this.submitted = true; 
  this.loader=true;
   this.userService.loginUser(this.model).subscribe((res: any)=>{
    if(res.statusCode==404 || res.statusCode==401){
      this.loader=false;
       this.messageService.add({severity:'error', summary:'Message :', detail:res.message});
    }else{
          
            var userName=res.username;
            var result=JSON.stringify(res);
            //localStorage.setItem("userData",res);
            localStorage.setItem("userName",userName);
            localStorage.setItem("access_token",res.accessToken);
            localStorage.setItem("email",res.email);
            var rolse =res.roles[0];
          // console.log("rolse------ ::"+result);
            var userRole="";
            
          if(rolse=="ROLE_SELLER"){
            userRole="Seller";
            }else if(rolse=="ROLE_BUYER"){
            userRole="Buyer";
          }else{
            userRole="ADMIN";
          }
          //console.log("userRole ::"+userRole);
          localStorage.setItem("userRole",userRole);
          this.loader=false;
          if(userRole=="Seller"){
            this.router.navigate(['/seller']);
          }else if(userRole=="Buyer"){
            this.router.navigate(['/buyer']);
          }
     }
   });
    
  
  }

  gotoRegistration() {
    this.router.navigate(['/registration']);
  }
}
