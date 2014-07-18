package edu.asu.securebanking.model;

public class InternalAccount {
	
	private int employeeId;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String vpassword;
	private String dob;
	private String ssn;
	private String address;
	private String email;
	private String telephone;
	private String securityquestion;
	private String securityanswer;
	private String gender;
	private String position;
   private boolean firstTimeLogin;
	private int deptid;
   private boolean authorized;

	private double salary;
	private boolean checkbox;

	
	
	
	
	
	
	public boolean isCheckbox() {
		return checkbox;
	}
	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public boolean getFirstTimeLogin() {
		return firstTimeLogin;
	}
	public void setFirstTimeLogin(boolean firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}
	
	public String getVpassword() {
		return vpassword;
	}
	public void setVpassword(String vpassword) {
		this.vpassword = vpassword;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the address1
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the securityquestion
	 */
	public String getSecurityquestion() {
		return securityquestion;
	}
	/**
	 * @param securityquestion the securityquestion to set
	 */
	public void setSecurityquestion(String securityquestion) {
		this.securityquestion = securityquestion;
	}
	/**
	 * @return the securityanswer
	 */
	public String getSecurityanswer() {
		return securityanswer;
	}
	/**
	 * @param securityanswer the securityanswer to set
	 */
	public void setSecurityanswer(String securityanswer) {
		this.securityanswer = securityanswer;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public boolean getAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

}

