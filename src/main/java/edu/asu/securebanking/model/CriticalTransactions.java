package edu.asu.securebanking.model;

public class CriticalTransactions {
	
	private int transid;
	private boolean internaltrans;
	private boolean externaltrans;
	private int userid;
	private int employeeid;
	private String request;
	private boolean CEOapproval;
	private boolean presidentapproval;
	private boolean vicepresidentapproval;
	private boolean sysadminapproval;
	
	public int getTransid() {
		return transid;
	}
	public void setTransid(int transid) {
		this.transid = transid;
	}
	public boolean isInternaltrans() {
		return internaltrans;
	}
	public void setInternaltrans(boolean internaltrans) {
		this.internaltrans = internaltrans;
	}
	public boolean isExternaltrans() {
		return externaltrans;
	}
	public void setExternaltrans(boolean externaltrans) {
		this.externaltrans = externaltrans;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public boolean isCEOapproval() {
		return CEOapproval;
	}
	public void setCEOapproval(boolean cEOapproval) {
		CEOapproval = cEOapproval;
	}
	public boolean isPresidentapproval() {
		return presidentapproval;
	}
	public void setPresidentapproval(boolean presidentapproval) {
		this.presidentapproval = presidentapproval;
	}
	public boolean isVicepresidentapproval() {
		return vicepresidentapproval;
	}
	public void setVicepresidentapproval(boolean vicepresidentapproval) {
		this.vicepresidentapproval = vicepresidentapproval;
	}
	public boolean isSysadminapproval() {
		return sysadminapproval;
	}
	public void setSysadminapproval(boolean sysadminapproval) {
		this.sysadminapproval = sysadminapproval;
	}
	
	
	
}
