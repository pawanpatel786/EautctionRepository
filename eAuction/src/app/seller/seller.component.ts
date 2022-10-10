import { Component,  OnInit, Output,EventEmitter } from '@angular/core';
import { VirtualTimeScheduler } from 'rxjs';
import { products } from '../product';
import { UserServiceService } from '../user-service.service';
import {MessageService} from 'primeng/api';
import { bidDetails } from '../bidDetails';
import { NgForm } from '@angular/forms';
import { CommunicationService } from '../communication.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-seller',
  templateUrl: './seller.component.html',
  styleUrls: ['./seller.component.css'],
  providers: [MessageService]
})
export class SellerComponent implements OnInit {
  
  product: products[]=[];
  biddetails: bidDetails[]=[];
  display: boolean = false;
  bidDetailsDisplay=false;
  model : products=new products('','','','','Painting',0,'','');
  
  bidProductName:string="";
  minDate = new Date();
  invalidDate = new Date();
  login_email=localStorage.getItem("email");
  product_loader=false
  bidDetail_loader=false;

  
  constructor(private userService:UserServiceService,private messageService: MessageService, private communication: CommunicationService,private router: Router) {
  
   }

  ngOnInit(): void {
    
    var userName= localStorage.getItem("userName");
    var role=localStorage.getItem("userRole");

   
    if(role=="Buyer"){
      this.router.navigate(['/']);
    }
    this.communication.sendUserDate(userName+"_"+role);
    //this.communication.sendUserRole(role+"");
    this.product_loader=true;
    this.userService.getProducts().subscribe((res: products[])=>{
         
          this.product=res;
          this.product_loader=false;
       
      
    });
  }

  productAddPopup(){
    this.display=true;
  }
  addProduct(ProductForm:NgForm){

            this.product_loader=true;
    this.userService.addProduct(this.model).subscribe((response: any)=>{
          
      if(response.status=='Success'){
        
        this.messageService.add({severity:'success', summary:'Message :', detail:response.message});
        this.userService.getProducts().subscribe((res: products[])=>{
          this.product=res;
          ProductForm.reset();
          this.product_loader=false;
      
        });
     
      }else{
        this.messageService.add({severity:'error', summary:'Message :', detail:response.message});
        this.product_loader=false;
      }
    
      
    });
  }
  bidDetails(productId:string, productName:string){
    
    this.messageService.clear();
    this.userService.getBidDetails(productId).subscribe((res: bidDetails[])=>{
      
      this.bidProductName=productName;
      if(res.length!=0){
        this.biddetails=res;
        this.bidDetailsDisplay=true;
      }else{
        this.messageService.add({severity:'error', summary:'Message :', detail:'Bid Details are not available for Product: '+this.bidProductName});
      }
     
      
    });

    
    
  }
  deleteProduct(productId:string,productName:string){

   
    this.messageService.clear();
    if(confirm("Are you sure to delete Product: "+productName)){
      this.product_loader=true;
        this.userService.deleteProduct(productId).subscribe((res: any)=>{
                  
          if(res.status=="Success"){
            this.userService.getProducts().subscribe((res: products[])=>{
              this.product=res;
              this.product_loader=false;
           
          
            });

            this.messageService.add({severity:'success', summary:'Message :', detail:'Product: '+this.bidProductName+'is deleted successfully'});
          }else{
            this.messageService.add({severity:'error', summary:'Message :', detail:''+res.message});
            this.product_loader=false;
          }
        
          
          
        });
  }
  }
  
}
