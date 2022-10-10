import { Component, OnInit } from '@angular/core';
import { userModel } from '../userModel';
import { UserServiceService } from '../user-service.service';
import {MessageService} from 'primeng/api';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css'],
  providers: [MessageService]
})
export class UserRegistrationComponent implements OnInit {
loader=false;
  constructor(private userService:UserServiceService,private messageService: MessageService,private router: Router) {


   }

  ngOnInit(): void {
  }
  
  
  model : userModel ={} as userModel;
  submitted = false;
  mobNumberPattern = "^((\\+91-?)|0)?[0-9]{10}$"; 

  onSubmit(RegistrationForm:NgForm) { 
    this.loader=true;
    this.messageService.clear();
    var role=RegistrationForm.controls['role'].value;
   // console.log("role ::"+role);
    this.model.roles=[role];
    this.userService.registerUser(this.model).subscribe((response: any)=>{
     // console.log("response ::"+response.message);
        
        if(response.status=='Success'){
          this.loader=false;
          this.messageService.add({severity:'success', summary:'Message: ', detail:''+response.message});
          this.router.navigate(['/']);
        }else{
          this.loader=false;
          this.messageService.add({severity:'error', summary:'Message: ', detail:response.message});
        }
      
        
      });
    

  
  }

  navigateToLogin(){
    this.router.navigate(['/']);
  }

  

  

}
