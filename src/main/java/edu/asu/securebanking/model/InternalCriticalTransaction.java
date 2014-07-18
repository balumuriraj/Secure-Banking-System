package edu.asu.securebanking.model;

public class InternalCriticalTransaction {

	private int transId;
	private String request;
	private int employeeId;
	private int deptid;
	private String ceoapp;
	private String presapp;
	private String vpresapp;

	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public String getCeoapp() {
		return ceoapp;
	}
	public void setCeoapp(String ceoapp) {
		this.ceoapp = ceoapp;
	}
	public String getPresapp() {
		return presapp;
	}
	public void setPresapp(String presapp) {
		this.presapp = presapp;
	}
	public String getVpresapp() {
		return vpresapp;
	}
	public void setVpresapp(String vpresapp) {
		this.vpresapp = vpresapp;
	}



}
