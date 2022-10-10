package com.iiht.eauction.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Bids")
public class BidModel implements Comparable<BidModel> {

	@Id
	private String id;
	private String userId;
	private String productId;
	private String productName;
	private String userName;
	private String email;
	private Number bidPrice;

	public String getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Number getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Number bidPrice) {
		this.bidPrice = bidPrice;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public int compareTo(BidModel o) {

		if (this.getBidPrice().intValue() > o.getBidPrice().intValue())
			return -1;
		else if (this.getBidPrice().intValue() < o.getBidPrice().intValue())
			return 1;
		else
			return 0;
	}

}
