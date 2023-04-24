package electricity_billing_system.database;
import electricity_billing_system.database.Models.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
	private String databaseName = "electricity_billing_system";
	private String username = "root";
	private String password = "";
	private String dbURL = String.format("jdbc:mysql://localhost:3306/%s", databaseName);
	private Connection conn;
		
	public void connect() {
		try {
			 this.conn = DriverManager.getConnection(dbURL, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LoginResponse Login(String username, String password) {
		LoginResponse response = new LoginResponse();
		String sql = "SELECT * FROM users WHERE username = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("password").equals(password)) {
					User user = new User(rs.getInt("id"),rs.getString("fullname"),rs.getString("username"),rs.getString("password"));
					response.setStatus(DbResponse.SUCCESS);
					response.setMessage("Successfully logged in!");
					response.setUser(user);
				}else {
					response.setStatus(DbResponse.FAILED);
					response.setMessage("You entered an incorrect password!");	
				}
			}else {
				response.setStatus(DbResponse.FAILED);
				response.setMessage("No matching account found!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	public ArrayList<Customer> getCustomers(){
		ArrayList<Customer> customers = new ArrayList<>();
		
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM customers");
			Customer customer;
			while(rs.next()) {
				customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirstname(rs.getString("firstname"));
				customer.setLastname(rs.getString("lastname"));
				customer.setPhone(rs.getString("phone"));
				customer.setAddress(rs.getString("address"));
				
				customers.add(customer);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return customers;
	}
	
	public Customer FindCustomer(int id){
		Customer customer = new Customer();
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirstname(rs.getString("firstname"));
				customer.setLastname(rs.getString("lastname"));
				customer.setPhone(rs.getString("phone"));
				customer.setAddress(rs.getString("address"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return customer;
	}
	
	public ArrayList<Customer> getCustomersWithNoActiveConnection(){
		ArrayList<Customer> customers = new ArrayList<>();
		
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM customers WHERE ID NOT IN (SELECT user_id FROM connections WHERE status = 1)");
			Customer customer;
			while(rs.next()) {
				customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirstname(rs.getString("firstname"));
				customer.setLastname(rs.getString("lastname"));
				customer.setPhone(rs.getString("phone"));
				customer.setAddress(rs.getString("address"));
				
				customers.add(customer);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return customers;
	}
	
	public boolean AddCustomer(Customer customer) {
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("INSERT INTO customers(firstname,lastname,phone,address) VALUES(?,?,?,?)");
			stmt.setString(1, customer.getFirstname());
			stmt.setString(2, customer.getLastname());
			stmt.setString(3, customer.getPhone());
			stmt.setString(4, customer.getAddress());
			
			if(stmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean UpdateCustomer(Customer customer) {
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("UPDATE customers SET firstname=?,lastname=?,phone=?,address=? WHERE id=?");
			stmt.setString(1, customer.getFirstname());
			stmt.setString(2, customer.getLastname());
			stmt.setString(3, customer.getPhone());
			stmt.setString(4, customer.getAddress());
			stmt.setInt(5, customer.getId());
			
			if(stmt.executeUpdate() >= 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean DeleteCustomer(int customerId) {
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("DELETE FROM customers WHERE id=?");
			stmt.setInt(1, customerId);
			
			if(stmt.executeUpdate() >= 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<ConnectionModel> getConnections(){
		ArrayList<ConnectionModel> connections = new ArrayList<>();
		
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM connections ORDER BY status ASC");
			ConnectionModel connection;
			while(rs.next()) {
				connection = new ConnectionModel();
				connection.setId(rs.getInt("id"));
				connection.setId(rs.getInt("meter_number"));
				connection.setCustomerId(rs.getInt("customer_id"));
				connection.setStatus(rs.getInt("status"));
				connection.setCustomer(FindCustomer(rs.getInt("customer_id")));
				connections.add(connection);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return connections;
	}
	
	public boolean AddConnection(int meterNumber,int customerId) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("INSERT INTO connections(meter_number,customer_id) VALUES(?,?)");
			stmt.setInt(1, meterNumber);
			stmt.setInt(2, customerId);
			
			if(stmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean UpdateConnection(ConnectionModel connectionModel) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("UPDATE connections SET meter_number=?,customer_id=?,status=?");
			stmt.setInt(1, connectionModel.getMeterNumber());
			stmt.setInt(2, connectionModel.getCustomerId());
			stmt.setInt(3, connectionModel.getStatus());
			
			if(stmt.executeUpdate() >= 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean DeleteConnection(int id) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("DELETE FROM connections WHERE id = ?");
			stmt.setInt(1, id);
			
			if(stmt.executeUpdate() >= 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
}
