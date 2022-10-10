package com.iiht.eauction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iiht.eauction.model.BidModel;
import com.iiht.eauction.model.Product;

public interface ProductService {
	
	public List<Product>updatedProductList(List<Product> list, String loggedUserId);
	
	public boolean bidValidationForProduct(BidModel model);
	
	public List<BidModel> getAllBidOfProduct(String productId);
	
	public boolean deleteBidFromProduct(String productId, String bidId);
	public boolean deleteProduct(String productId);
	
	public boolean updateBidToProduct(BidModel model);


}
