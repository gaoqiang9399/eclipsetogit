package com.zgcdb.demo.admin.entity;

public class ExcelUserEntity {

	private String username;
	private String password;
	private String email;
	private String fmale;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getFmale() {
		return fmale;
	}

	public void setFmale(String fmale) {
		this.fmale = fmale;
	}

	@Override
	public String toString() {
		return "ExcelUserEntity [username=" + username + ", password=" + password + ", email=" + email + ", fmale="
				+ fmale + "]";
	}

}
