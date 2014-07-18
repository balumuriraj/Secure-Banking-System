package edu.asu.securebanking.model;

import javax.persistence.ManyToOne;

public class PiiAuth {
	
	private int userid;
	private String isauthorized;
	private String authrequest;
	
	
	public String getAuthrequest() {
		return authrequest;
	}
	public void setAuthrequest(String authrequest) {
		this.authrequest = authrequest;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getIsauthorized() {
		return isauthorized;
	}
	public void setIsauthorized(String isauthorized) {
		this.isauthorized = isauthorized;
	}

}
