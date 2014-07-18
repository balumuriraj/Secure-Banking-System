package edu.asu.securebanking.model;

public class InternalUserTransaction {
	private int transId;
	private int employeeId;
	private String transType;
	private String description;
	private String status;
	private int deptId;
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int DeptId) {
		this.deptId = DeptId;
	}
	
}
