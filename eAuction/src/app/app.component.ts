import { Component } from '@angular/core';
import { CommunicationService } from './communication.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'eAuction';
  loginUser="";
  userRole="";
constructor(private communication: CommunicationService,private router: Router){

}
ngOnInit(){
  this.communication.userName$.subscribe(message=>{
    this.loginUser=message.split("_")[0];
    this.userRole=message.split("_")[1];

  })



  // this.communication.userRole$.subscribe(role=>{
  //   this.userRole=role+"";
  // })
}


logout(){
  localStorage.removeItem("access_token");
  localStorage.removeItem("userName");
  localStorage.removeItem("email");
  this.loginUser="";
  this.userRole="";
  this.router.navigate(['/']);
}
  


}
