package electricity_billing_system.database;
import electricity_billing_system.database.Models.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
			ResultSet rs = st.executeQuery("SELECT * FROM customers WHERE ID NOT IN (SELECT customer_id FROM connections WHERE status = 1)");
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
	
	public ArrayList<Customer> getCustomersWithActiveConnection(){
		ArrayList<Customer> customers = new ArrayList<>();
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("SELECT * FROM customers WHERE id IN (SELECT customer_id FROM connections WHERE status = ?)");
			stmt.setInt(1, ConnectionModel.ACTIVE);
			
			ResultSet rs = stmt.executeQuery();
			
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
				connection.setMeterNumber(rs.getInt("meter_number"));
				connection.setCustomerId(rs.getInt("customer_id"));
				connection.setStatus(rs.getInt("status"));
				connection.setCustomer(FindCustomer(rs.getInt("customer_id")));
				connection.setConnectionDate(rs.getDate("connection_date"));
				connections.add(connection);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return connections;
	}
	
	public ConnectionModel findConnection(int id){
		PreparedStatement stmt;
		ConnectionModel connection = new ConnectionModel();
		try {
			stmt = conn.prepareStatement("SELECT * FROM connections WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				connection = new ConnectionModel();
				connection.setId(rs.getInt("id"));
				connection.setMeterNumber(rs.getInt("meter_number"));
				connection.setCustomerId(rs.getInt("customer_id"));
				connection.setStatus(rs.getInt("status"));
				connection.setConnectionDate(rs.getDate("connection_date"));
				connection.setCustomer(FindCustomer(rs.getInt("customer_id")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return connection;
	}
	
	public ConnectionModel findConnectionByCustomer(int customerId){
		PreparedStatement stmt;
		ConnectionModel connection = new ConnectionModel();
		try {
			stmt = conn.prepareStatement("SELECT * FROM connections WHERE customer_id = ?");
			stmt.setInt(1, customerId);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				connection = new ConnectionModel();
				connection.setId(rs.getInt("id"));
				connection.setMeterNumber(rs.getInt("meter_number"));
				connection.setCustomerId(rs.getInt("customer_id"));
				connection.setStatus(rs.getInt("status"));
				connection.setConnectionDate(rs.getDate("connection_date"));
				connection.setCustomer(FindCustomer(rs.getInt("customer_id")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return connection;
	}
	
	public boolean AddConnection(int meterNumber,int customerId,java.sql.Date connectionDate) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("INSERT INTO connections(meter_number,customer_id,connection_date) VALUES(?,?,?)");
			stmt.setInt(1, meterNumber);
			stmt.setInt(2, customerId);
			stmt.setDate(3, connectionDate);
			
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
			stmt = conn.prepareStatement("UPDATE connections SET meter_number=?,customer_id=?,connection_date=?,status=? WHERE id = ?");
			stmt.setInt(1, connectionModel.getMeterNumber());
			stmt.setInt(2, connectionModel.getCustomerId());
			stmt.setDate(3, connectionModel.getConnectionDate());
			stmt.setInt(4, connectionModel.getStatus());
			stmt.setInt(5, connectionModel.getId());
			if(stmt.executeUpdate() >= 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean UpdateConnectionStatus(int id, int status) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("UPDATE connections SET status = ? WHERE id = ?");
			stmt.setInt(1, status);
			stmt.setInt(2, id);
			
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
	
	public boolean AddBill(int connectionId,int rate, int units, int amount, String billNo) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("INSERT INTO bills(connection_id,rate, units, amount,bill_no) VALUES(?,?,?,?,?)");
			stmt.setInt(1, connectionId);
			stmt.setInt(2, rate);
			stmt.setInt(3, units);
			stmt.setInt(4, amount);
			stmt.setString(5,billNo);
			
			if(stmt.executeUpdate() >= 0) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean UpdateBill(Bill bill) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("UPDATE bills SET connection_id = ?, rate=?,units=?,amount=?");
			stmt.setInt(1, bill.getConnectionId());
			stmt.setInt(2, bill.getRate());
			stmt.setInt(3, bill.getUnits());
			stmt.setInt(4, bill.getAmount());
			
			
			return stmt.executeUpdate() >= 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean DeleteBill(int id) {
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("DELETE FROM bills WHERE id = ?");
			stmt.setInt(1, id);
			
			return stmt.executeUpdate() >= 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<Bill> getBills(){
		ArrayList<Bill> bills = new ArrayList<>();
		
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM bills");
			Bill bill;
			while(rs.next()) {
				bill = new Bill();
				bill.setId(rs.getInt("id"));
				bill.setConnectionId(rs.getInt("connection_id"));
				bill.setUnits(rs.getInt("units"));
				bill.setRate(rs.getInt("rate"));
				bill.setAmount(rs.getInt("amount"));
				bill.setBillNo(rs.getString("bill_no"));
				bill.setCreatedAt(rs.getDate("created_at"));
				bill.setConnectionModel(findConnection(rs.getInt("connection_id")));
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transactions WHERE bill_id = ?");
				stmt.setInt(1, bill.getId());
				ResultSet r = stmt.executeQuery();
				bill.setStatus(r.next()? Bill.PAID:Bill.UNPAID);
				
				bills.add(bill);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bills;
	}
	
	public ArrayList<Bill> getUnpaidBills(){
		ArrayList<Bill> bills = getBills();
		ArrayList<Bill> unpaid = new ArrayList<>();
		
		bills.forEach(b ->{
			if (b.getStatus() == Bill.UNPAID) {
				unpaid.add(b);
			}
		});
		
		return unpaid;
	}
	
	public Bill findBill(int id){
		PreparedStatement stmt;
		Bill bill = new Bill();
		try {
			stmt = conn.prepareStatement("SELECT * FROM bills WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				bill.setId(rs.getInt("id"));
				bill.setAmount(rs.getInt("amount"));
				bill.setRate(rs.getInt("rate"));
				bill.setBillNo(rs.getString("bill_no"));
				bill.setUnits(rs.getInt("units"));
				bill.setConnectionId(rs.getInt("connection_id"));
				bill.setConnectionModel(findConnection(rs.getInt("connection_id")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bill;
	}
	
	public String newBillNo() {
		String billNo = "";
		
		PreparedStatement stmt;
		Bill bill = new Bill();
		try {
			String lastBillNo = "";
			String leading =  String.valueOf(LocalDate.now().getYear()) + "-";
			
			PreparedStatement st = conn.prepareStatement("SELECT * FROM bills WHERE bill_no LIKE ? ORDER by id DESC");
			st.setString(1, "%" + leading + "%");
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				lastBillNo = rs.getString("bill_no");
				
				//take last number 
				String s = lastBillNo.substring(lastBillNo.lastIndexOf('-') + 1);
				int lastNo = Integer.parseInt(s);
				
				lastNo += 1;
				billNo = leading +  String.format("%04d", lastNo);
				
			}else {
				  billNo = leading + String.format("%04d", 1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
						
		return billNo;
	}
	
	public ArrayList<TransactionModel> getTransactionModels(){
		ArrayList<TransactionModel> transactions = new ArrayList<>();
		
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM transactions");
			TransactionModel transaction;
			while(rs.next()) {
				transaction = new TransactionModel();
				transaction.setId(rs.getInt("id"));
				transaction.setAmount(rs.getInt("amount"));
				transaction.setCreatedAt(rs.getDate("created_at"));
				transaction.setBillId(rs.getInt("bill_id"));
				transaction.setBill(findBill(rs.getInt("bill_id")));
				
				transactions.add(transaction);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return transactions;
	}
	
	public TransactionModel findTransaction(int id){
		TransactionModel transaction = new TransactionModel();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("SELECT * FROM transactions WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) {
				transaction = new TransactionModel();
				transaction.setId(rs.getInt("id"));
				transaction.setAmount(rs.getInt("amount"));
				transaction.setCreatedAt(rs.getDate("created_at"));
				transaction.setBillId(rs.getInt("bill_id"));
				transaction.setBill(findBill(rs.getInt("bill_id")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return transaction;
	}
	
	public Boolean addTransaction(int billId,int amount){
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("INSERT INTO transactions(bill_id, amount) VALUES(?,?)");
			stmt.setInt(1,billId);
			stmt.setInt(2,amount);
			
			
			return stmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Boolean updateTransaction(TransactionModel transaction){
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("UPDATE transactions SET bill_id=?,amount=? WHERE id = ?");
			stmt.setInt(1,transaction.getBillId());
			stmt.setInt(2,transaction.getAmount());
			stmt.setInt(3,transaction.getId());
			
			
			return stmt.executeUpdate() >= 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Boolean deleteTransaction(int id){
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("DELETE FROM transactions WHERE id = ?");
			stmt.setInt(1,id);
					
			return stmt.executeUpdate() >= 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
}
