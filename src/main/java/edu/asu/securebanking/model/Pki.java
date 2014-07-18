package edu.asu.securebanking.model;

import java.sql.Blob;

import javax.persistence.Lob;


public class Pki {
	private String userName;
	@Lob
	private Blob cert;
	private String transId;
	private String transDetail;
	private String merchant;
	private double amountInvolved;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Blob getCert() {
		return cert;
	}
	public void setCert(Blob cert) {
		this.cert = cert;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransDetail() {
		return transDetail;
	}
	public void setTransDetail(String transDetail) {
		this.transDetail = transDetail;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public double getAmountInvolved() {
		return amountInvolved;
	}
	public void setAmountInvolved(double amountInvolved) {
		this.amountInvolved = amountInvolved;
	}
}
