export class products {

    constructor(
      public id: string,
      public productName: string,
      public shortDescription: string,
      public detailedDescription: string,
      public category: string,
      public startingPrice: number,
      public bidEndDate: string,
      public isDeleted:string
      
      
    ) {  }
  
  }