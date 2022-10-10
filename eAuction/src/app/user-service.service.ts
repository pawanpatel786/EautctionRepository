import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { userLoginModel } from './userLoginModel';
import { HttpHeaders } from '@angular/common/http';
import { products } from './product';
import { bidDetails } from './bidDetails';
import { userModel } from './userModel';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {
  eAuctionUrl = 'https://d3g7rvebddzs5m.cloudfront.net/eAuction/';
  constructor(private http: HttpClient) { 

  }
/** POST: add a new hero to the database */
 
 

loginUser(user: userLoginModel): Observable<any> {
  var httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      //Authorization: 'Barear'
    })
  };
  return this.http.post<userLoginModel>(this.eAuctionUrl+"signin", user, httpOptions)
    .pipe(
      catchError(this.handleError)
    );
}
registerUser(userModel: userModel): Observable<any> {
  var token=localStorage.getItem("access_token");
  var httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'Bearer '+token,
      //Authorization: 'Barear'
    })
  };
  return this.http.post<any>(this.eAuctionUrl+"signup", userModel, httpOptions)
    .pipe(
      catchError(this.handleError)
    );
}
addProduct(product: products): Observable<any> {
  var token=localStorage.getItem("access_token");
  var httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'Bearer '+token,
      //Authorization: 'Barear'
    })
  };
  return this.http.post<any>(this.eAuctionUrl+"seller/addProduct", product, httpOptions)
    .pipe(
      catchError(this.handleError)
    );
}

addBid(bidDetails: bidDetails): Observable<any> {
  var token=localStorage.getItem("access_token");
  var httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'Bearer '+token,
      //Authorization: 'Barear'
    })
  };
  return this.http.post<any>(this.eAuctionUrl+"buyer/addBid", bidDetails, httpOptions)
    .pipe(
      catchError(this.handleError)
    );
}

updateBid(bidDetails: bidDetails): Observable<any> {
  var token=localStorage.getItem("access_token");
  var httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'Bearer '+token,
      //Authorization: 'Barear'
    })
  };
  return this.http.post<any>(this.eAuctionUrl+"buyer/updateBid", bidDetails, httpOptions)
    .pipe(
      catchError(this.handleError)
    );
}



getProducts(): Observable<any> {
 var token=localStorage.getItem("access_token");
  var httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Bearer '+token,
    })
  };
   
  return this.http.get<products>(this.eAuctionUrl+"seller/getProducts",httpOptions)
    .pipe(
      catchError(this.handleError)
    );
}

getBidDetails(productId:string): Observable<any> {
  var token=localStorage.getItem("access_token");
   var httpOptions = {
     headers: new HttpHeaders({
       Authorization: 'Bearer '+token,
     })
   };
    
   return this.http.get<bidDetails>(this.eAuctionUrl+"buyer/getBidDetails?productId="+productId,httpOptions)
     .pipe(
       catchError(this.handleError)
     );
 }

 deleteProduct(productId:string): Observable<any> {
  var token=localStorage.getItem("access_token");
   var httpOptions = {
     headers: new HttpHeaders({
       Authorization: 'Bearer '+token,
     })
   };
    
   return this.http.delete<any>(this.eAuctionUrl+"seller/deleteProduct?productId="+productId,httpOptions)
     .pipe(
       catchError(this.handleError)
     );
 }

 deleteBid(bidId:string,productId:string): Observable<any> {
  var token=localStorage.getItem("access_token");
   var httpOptions = {
     headers: new HttpHeaders({
       Authorization: 'Bearer '+token,
     })
   };
    
   return this.http.get<any>(this.eAuctionUrl+"buyer/deleteBid?productId="+productId+'&bidId='+bidId,httpOptions)
     .pipe(
       catchError(this.handleError)
     );
 }
  private handleError(error: HttpErrorResponse) {
    // if (error.status === 401) {
    //   //alert("alert 401");
    // }else
    
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
