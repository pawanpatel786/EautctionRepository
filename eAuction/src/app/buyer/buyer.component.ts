import { Component, OnInit } from '@angular/core';
import { bidDetails } from '../bidDetails';
import { products } from '../product';
import { UserServiceService } from '../user-service.service';
import {MessageService} from 'primeng/api';
import { CommunicationService } from '../communication.service';
@Component({
  selector: 'app-buyer',
  templateUrl: './buyer.component.html',
  styleUrls: ['./buyer.component.css'],
  providers: [MessageService]
})
export class BuyerComponent implements OnInit {
  product: products[]=[];
  biddetails: bidDetails[]=[];
  bidDetailsDisplay=false;
  addBidDisplay=false;
  login_email=localStorage.getItem("email");
  model : bidDetails={} as bidDetails;
  bidProductName:string="";
  productId:string="";
  showAddButton:boolean=true;
  duplicateBid:boolean=false;
  product_loader=false
  bidDetail_loader=false;
  addBid_loader=false;
  constructor(private userService:UserServiceService,private messageService: MessageService,private communication: CommunicationService) { }

  ngOnInit(): void {
    this.product_loader=true;
    var userName= localStorage.getItem("userName");
    var role=localStorage.getItem("userRole");
    this.communication.sendUserDate(userName+"_"+role);
    this.userService.getProducts().subscribe((res: products[])=>{
      //console.log("response----shortDescription-- ::"+res[0].shortDescription);
      this.product=res;
      this.product_loader=false;
       
      
    });
  }
  bidDetails(productId:string,productName:string){
   
    this.bidDetail_loader=true;
    this.messageService.clear();
    this.userService.getBidDetails(productId).subscribe((res: bidDetails[])=>{
      this.bidProductName=productName;
      this.productId=productId;
      this.biddetails=res;
      //this.validateDuplicateUser(res);
        this.bidDetailsDisplay=true;
         this.bidDetail_loader=false;  
    });

    
   
  }

  showAddBidDetals(bidProductName:string,bidPrice:number,productId:string,bidId:string){
    this.bidDetailsDisplay=false;
    this.addBidDisplay=true;
    this.model.productId=productId;
    this.model.id="";
    if(bidProductName.length!=0){
      this.model.productName=bidProductName;
      this.model.bidPrice=bidPrice;
      if(bidId.length!=0){
        this.model.id=bidId;
      }
      
      
    }else{
      this.model.productName=bidProductName;
      this.model.bidPrice==0;
      this.showAddButton=true;
    }

    if(bidPrice!=0){
      this.showAddButton=false;
    }
  }
  showBidTable(){
    this.addBidDisplay=false;
    this.bidDetailsDisplay=true;
    this.bidDetail_loader=false;
    
  }
  cancelfromAddBidForm(){
    this.addBidDisplay=false;
    this.bidDetailsDisplay=false;
    this.addBid_loader=false;
  }
  updateBidDetals(){
    this.addBid_loader=true;
      this.userService.updateBid(this.model).subscribe((response: any)=>{
     // console.log("response ::"+response.message);
        
        if(response.status=='Success'){
         // alert("productId ::"+this.model.productId);
         this.addBid_loader=false;
          this.userService.getBidDetails(this.model.productId).subscribe((res: bidDetails[])=>{
            this.bidProductName=this.model.productName;
            this.productId=this.model.productId;
            this.biddetails=res;
            //this.validateDuplicateUser(res);
            this.bidDetailsDisplay=false;
                 
          });
          
          
          this.messageService.add({ key:'bidTableId', severity:'success', summary:'Message: ', detail:''+response.message});
        }else{
          this.addBid_loader=false;
          this.bidDetailsDisplay=false;
          this.messageService.add({key:'bidTableId',severity:'error',  summary:'Message: ', detail:''+response.message});
        }
      this.showBidTable();
        
      });

  }
  deleteBidDetals(bidId:string, productId: string){
     
    if(confirm("Are you sure to delete this Bid")){
        this.userService.deleteBid(bidId,productId).subscribe((res: any)=>{
        if(res.status=='Success'){
         // alert("productId ::"+this.model.productId);
          //alert("productId1 ::"+productId);
          

          this.userService.getBidDetails(this.model.productId).subscribe((res: bidDetails[])=>{
            this.bidProductName=this.model.productName;
            this.productId=this.model.productId;
            this.biddetails=res;
            //this.validateDuplicateUser(res);
            this.bidDetailsDisplay=false;
                 
          });
          this.messageService.add({ key:'bidTableId',severity:'success', summary:'Message: ', detail:''+res.message});
      
          }else{
            this.bidDetailsDisplay=false;
            this.messageService.add({ key:'bidTableId',severity:'error', summary:'Message: ', detail:''+res.message});
          }
          this.showBidTable();
 });
    }
   


  }
  addBid(){
    this.addBid_loader=true;
    this.userService.addBid(this.model).subscribe((response: any)=>{
        if(response.status=='Success'){
           this.userService.getBidDetails(this.model.productId).subscribe((res: bidDetails[])=>{
            this.bidProductName=this.model.productName;
            this.productId=this.model.productId;
            this.biddetails=res;
            //this.validateDuplicateUser(res);
            this.addBid_loader=false;
            // this.bidDetailsDisplay=true;
            this.addBidDisplay=false;

                 
          });
          
          this.messageService.add({ key:'bidTableId',severity:'success', summary:'Message : ', detail:response.message});
        }else{
          this.addBid_loader=false;
          this.addBidDisplay=false;
          this.messageService.add({ key:'bidTableId',severity:'error', summary:'Message :', detail:''+response.message});
        }
        // this.showBidTable();
        
      });
  }
 validateDuplicateUser(biddetails:bidDetails[]){
  this.duplicateBid=false;
        biddetails.forEach( (element) => {
          if(element.email==this.login_email){
            this.duplicateBid=true;
          return;
          }else{
            this.duplicateBid=false;
          }
        });
 }

}
