export class userModel {

    constructor(
      public id: number,
      public firstName: string,
      public lastName: string,
      public address: string,
      public city: string,
      public pin: number,
      public phone: number,
      public email: string,
      public password: string,
      public roles: any [],
      public role:string,

      
    ) {  }
  
  }