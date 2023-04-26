package electricity_billing_system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import electricity_billing_system.Components.Topbar;
import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.Bill;
import electricity_billing_system.database.Models.ConnectionModel;
import electricity_billing_system.database.Models.Customer;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateBillForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldMeterNumber;
	private JTextField textFieldRate;
	private JTextField textFieldUnits;
	private JTextField textFieldAmount;
	ArrayList<Customer> customers;
	JComboBox comboBoxCustomers;
	DbHelper dbHelper;
	int id;
	Bill bill;

	public UpdateBillForm(int id) {
		setResizable(false);
		setModal(true);
		dbHelper = new DbHelper();
		dbHelper.connect();
		this.id = id;
		bill = dbHelper.findBill(id);
		
		init();
		initData();
		
		((PlainDocument) textFieldRate.getDocument()).setDocumentFilter(new IntFilter());
		((PlainDocument) textFieldMeterNumber.getDocument()).setDocumentFilter(new IntFilter());
		((PlainDocument) textFieldUnits.getDocument()).setDocumentFilter(new IntFilter());
	}
	

	void initData() {
		DefaultComboBoxModel<Object> comboBoxModel = new DefaultComboBoxModel<>();
		customers = dbHelper.getCustomersWithActiveConnection();
		
		customers.forEach(c ->{
			comboBoxModel.addElement(c.getFirstname() + c.getLastname());
			
		});
		comboBoxCustomers.setModel(comboBoxModel);
		
		
		if(customers.size() > 0) {
			
			int index = 0;
			
			for(int x = 0;x<customers.size();x++) {
				if(customers.get(x).getId() == bill.getConnectionModel().getCustomerId()) {
					index = x;
					break;
				}
			}
			
			comboBoxCustomers.setSelectedIndex(index);
			
			Customer customer = customers.get(comboBoxCustomers.getSelectedIndex());
			
			ConnectionModel connectionModel = dbHelper.findConnectionByCustomer(customer.getId());
			
			textFieldMeterNumber.setText(connectionModel.getMeterNumber()+"");
			
			textFieldRate.setText(bill.getRate()+"");
			textFieldUnits.setText(bill.getUnits()+"");
			textFieldAmount.setText(bill.getAmount()+"");
		}
		
		
	}
	
	void computeAmount() {
		try {
			if(!textFieldRate.getText().isBlank() && !textFieldUnits.getText().isBlank()) {
				int rate = Integer.parseInt(textFieldRate.getText());
				int units = Integer.parseInt(textFieldUnits.getText());
				
				int amount = rate * units;
				
				textFieldAmount.setText(amount + "");
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}
	void init() {
		setBounds(100, 100, 606, 474);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		Topbar topbar = new Topbar();
		
		JLabel lblNewLabel = new JLabel("Update Bill");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1 = new JLabel("Customer:");
		
		JLabel lblMeterNumber = new JLabel("Meter Number:");
		
		textFieldMeterNumber = new JTextField();
		textFieldMeterNumber.setEditable(false);
		textFieldMeterNumber.setColumns(10);
		textFieldMeterNumber.setBackground(Color.LIGHT_GRAY);
		
		comboBoxCustomers = new JComboBox();
		comboBoxCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(customers.size() > 0) {
					Customer customer = customers.get(comboBoxCustomers.getSelectedIndex());
					
					ConnectionModel connectionModel = dbHelper.findConnectionByCustomer(customer.getId());
					
					textFieldMeterNumber.setText(connectionModel.getMeterNumber()+"");
				}
			}
		});
		comboBoxCustomers.setBackground(Color.WHITE);
		
		JLabel lblRate = new JLabel("Rate:");
		
		textFieldRate = new JTextField();
		textFieldRate.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				computeAmount();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				computeAmount();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				computeAmount();
			}
		});
		textFieldRate.setColumns(10);
		
		textFieldUnits = new JTextField();
		textFieldUnits.setColumns(10);
		
		textFieldUnits.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				computeAmount();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				computeAmount();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				computeAmount();
			}
		});
		
		JLabel lblUnits = new JLabel("Units");
		
		JLabel lblAmount = new JLabel("Amount");
		
		textFieldAmount = new JTextField();
		textFieldAmount.setEditable(false);
		textFieldAmount.setColumns(10);
		textFieldAmount.setBackground(Color.LIGHT_GRAY);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(topbar, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMeterNumber, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblRate, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(72)
							.addComponent(lblUnits, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(69)
							.addComponent(lblAmount, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(textFieldRate, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(textFieldUnits, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textFieldAmount, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(29, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(264)
					.addComponent(lblNewLabel)
					.addContainerGap(264, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(28)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblMeterNumber, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRate, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUnits, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAmount, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldRate, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldUnits, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAmount, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(72, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
//						check if all fields are filled
						if(comboBoxCustomers.getSelectedIndex() < 0) {
							MessageDialog.ShowPlainMessage("Please select a customer!");
							return;
						}else if(textFieldRate.getText().isBlank() || textFieldUnits.getText().isBlank()) {
							MessageDialog.ShowPlainMessage("Please fill all the fields!");
							return;
						}
						else {
							computeAmount();
						}
						
						if(customers.size() > 0) {
							int customerId = customers.get(comboBoxCustomers.getSelectedIndex()).getId();
							ConnectionModel connectionModel = dbHelper.findConnectionByCustomer(customerId);
							
							int connectionId = connectionModel.getId();
							int rate  = Integer.parseInt(textFieldRate.getText());
							int units  = Integer.parseInt(textFieldUnits.getText());
							int amount  = Integer.parseInt(textFieldAmount.getText());
							
							bill.setConnectionId(connectionId);
							bill.setRate(rate);
							bill.setUnits(units);
							bill.setAmount(amount);
							
							Boolean updated = dbHelper.UpdateBill(bill);
							
							if(updated) {
								MessageDialog.ShowPlainMessage("Successfully updated!");
							}else {
								MessageDialog.ShowDefaultErrorMessage();
							}
							setVisible(false);
						}
					}
				});
				okButton.setBackground(Color.WHITE);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setBackground(Color.WHITE);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
