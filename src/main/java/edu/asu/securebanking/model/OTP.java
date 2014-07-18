package edu.asu.securebanking.model;

public class OTP {

	private int pkey;
	private int userid;
	private int empid;

	private java.sql.Timestamp time;
	private String otp;

	public int getpkey() {
		return this.pkey;
	}
	public void setpkey(int pkey) {
		this.pkey = pkey;
	}
	public int getuserid() {
		return this.userid;
	}
	public void setuserid(int userid) {
		this.userid = userid;
	}
	public int getempid() {
		return this.empid;
	}
	public void setempid(int empid) {
		this.empid = empid;
	}
	public String getotp() {
		return this.otp;
	}
	public void setotp(String otp) {
		this.otp = otp;
	}
	public java.util.Date gettime() {
		return this.time;
	}
	public void settime(java.sql.Timestamp param) {
		this.time = param;
	}

	
}
