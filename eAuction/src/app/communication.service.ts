import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommunicationService {

  private _userNameSource=new Subject<String>();
  private _userRoleSource=new Subject<String>();
  userName$=this._userNameSource.asObservable();
  userRole$=this._userNameSource.asObservable();
  constructor() { }
  sendUserDate(userName:string){
    this._userNameSource.next(userName);
  }
  sendUserRole(userRole:string){
    this._userRoleSource.next(userRole);
  }


}
