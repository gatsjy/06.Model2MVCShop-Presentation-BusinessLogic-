package com.model2.mvc.service.domain;

import java.sql.Date;

public class Purchase {
	
	private User buyer;
	private String divyAddr;
	private String divyDate;
	private String divyRequest;
	private Date orderDate;
	private String paymentOption;
	private Product purchaseProd;
	private String receiverName;
	private String receiverPhone;
	private String tranCode;
	private int tranNo;
	private int purchaseQuantity;
	private int purchasePrice;
	private String purchaseCancel;
	private int star;
	private String review;

	public Purchase(){
	}

	public User getBuyer() {
		return buyer;
	}

	public String getDivyAddr() {
		return divyAddr.replace("-", "");
	}

	public String getDivyDate() {
		return divyDate;
	}

	public String getDivyRequest() {
		return divyRequest;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public Product getPurchaseProd() {
		return purchaseProd;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public String getTranCode() {
		return tranCode.trim();
	}

	public int getTranNo() {
		return tranNo;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public String getPurchaseCancel() {
		return purchaseCancel;
	}

	public int getStar() {
		return star;
	}

	public String getReview() {
		return review;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public void setDivyAddr(String divyAddr) {
		this.divyAddr = divyAddr;
	}

	public void setDivyDate(String divyDate) {
		if(divyDate==null) {
			this.divyDate = divyDate;
		}else {
			this.divyDate = divyDate.substring(0, 10);
		}
	}

	public void setDivyRequest(String divyRequest) {
		this.divyRequest = divyRequest;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption.trim();
	}

	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public void setPurchaseCancel(String purchaseCancel) {
		this.purchaseCancel = purchaseCancel;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
}