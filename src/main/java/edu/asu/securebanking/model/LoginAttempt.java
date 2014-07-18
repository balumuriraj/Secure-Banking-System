package edu.asu.securebanking.model;

public class LoginAttempt {

	private String user_name;
	private String pwd;
	private String passkey;

	public String getuser_name() {
		return user_name;
	}
	public void setuser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getpwd() {
		return pwd;
	}
	public void setpwd(String pwd) {
		this.pwd = pwd;
	}
	public String getpasskey() {
		return passkey;
	}
	public void setpasskey(String passkey) {
		this.passkey = passkey;
	}

	
}
