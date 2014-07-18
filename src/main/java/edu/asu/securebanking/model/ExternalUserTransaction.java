package edu.asu.securebanking.model;

public class ExternalUserTransaction {
	private int transId;
	private int userId;
	private String transType;
	private String transDetail;
	private double amountInvolved;
	private String status;
	private int accessGranted;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public double getAmountInvolved() {
		return amountInvolved;
	}
	public void setAmountInvolved(double amountInvolved) {
		this.amountInvolved = amountInvolved;
	}
	public String getTransDetail() {
		return transDetail;
	}
	public void setTransDetail(String transDetail) {
		this.transDetail = transDetail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAccessGranted() {
		return accessGranted;
	}
	public void setAccessGranted(int accessGranted) {
		this.accessGranted = accessGranted;
	}
}
