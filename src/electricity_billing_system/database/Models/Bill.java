package electricity_billing_system.database.Models;

import java.sql.Date;

import electricity_billing_system.database.DbHelper;

public class Bill {
	private int id;
	private int connectionId;
	private ConnectionModel connectionModel;
	private int units;
	private int amount;
	private Date createdAt;
	public static final int UNPAID = 0;
	public static final int PAID = 1;
	private int rate;
	private int status = UNPAID;
	private String billNo;
	
	
	public Bill() {
		
	}
	
	public Bill(int id, int connectionId,int rate, ConnectionModel connectionModel, int units, int amount, Date createDate) {
		this.id = id;
		this.connectionId = connectionId;
		this.connectionModel = connectionModel;
		this.units = units;
		this.amount = amount;
		this.createdAt = createDate;
		this.rate = rate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}
	public ConnectionModel getConnectionModel() {
		return connectionModel;
	}
	public void setConnectionModel(ConnectionModel connectionModel) {
		this.connectionModel = connectionModel;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	

	
}
