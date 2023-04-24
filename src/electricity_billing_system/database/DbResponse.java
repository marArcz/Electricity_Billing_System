package electricity_billing_system.database;

public class DbResponse {
	public static final int SUCCESS = 1;
	public static final int FAILED = 0;
	
	private int status;
	private String message;
	
	
	public DbResponse() {}
	
	public DbResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
