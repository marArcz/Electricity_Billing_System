package electricity_billing_system.database;

import electricity_billing_system.database.Models.User;

public class LoginResponse extends DbResponse {
	private User user = null;
	
	public LoginResponse(int status, String message) {
		super(status, message);
	}
	
	public LoginResponse() {}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
