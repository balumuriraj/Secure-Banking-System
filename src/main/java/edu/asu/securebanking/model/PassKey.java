package edu.asu.securebanking.model;

public class PassKey {
	private String passKey;
	private boolean accountType;
	
	public boolean isAccountType() {
		return accountType;
	}
	public void setAccountType(boolean accountType) {
		this.accountType = accountType;
	}
	public String getPassKey() {
		return passKey;
	}
	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}
	
}
