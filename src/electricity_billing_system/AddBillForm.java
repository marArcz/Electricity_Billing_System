package electricity_billing_system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

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
import electricity_billing_system.database.Models.ConnectionModel;
import electricity_billing_system.database.Models.Customer;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AddBillForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldMeterNumber;
	private JTextField textFieldRate;
	private JTextField textFieldUnits;
	private JTextField textFieldAmount;
	private JComboBox comboBoxCustomers;
	ArrayList<Customer> customers;
	DbHelper dbHelper;
	private JTextField textFieldBillNo;
	
	public AddBillForm() {
		setResizable(false);
		setModal(true);
		dbHelper = new DbHelper();
		dbHelper.connect();
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
			comboBoxCustomers.setSelectedIndex(0);
			Customer customer = customers.get(comboBoxCustomers.getSelectedIndex());
			
			ConnectionModel connectionModel = dbHelper.findConnectionByCustomer(customer.getId());
			
			textFieldMeterNumber.setText(connectionModel.getMeterNumber()+"");
		}
		
		textFieldBillNo.setText(dbHelper.newBillNo());
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
		setBounds(100, 100, 612, 492);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		Topbar topbar = new Topbar();
		JLabel lblNewLabel = new JLabel("Customer:");
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
		JLabel lblMeterNumber = new JLabel("Meter Number:");
		textFieldMeterNumber = new JTextField();
		textFieldMeterNumber.setBackground(Color.LIGHT_GRAY);
		textFieldMeterNumber.setEditable(false);
		textFieldMeterNumber.setColumns(10);
		
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
		textFieldRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				computeAmount();
			}
		});
		
		
		textFieldRate.setColumns(10);
		
		textFieldUnits = new JTextField();
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
		textFieldUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		
		
		
		textFieldUnits.setColumns(10);
		
		JLabel lblUnits = new JLabel("Units");
		
		textFieldAmount = new JTextField();
		textFieldAmount.setBackground(Color.LIGHT_GRAY);
		textFieldAmount.setEditable(false);
		textFieldAmount.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount");
		
		textFieldBillNo = new JTextField();
		textFieldBillNo.setEditable(false);
		textFieldBillNo.setColumns(10);
		textFieldBillNo.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblBillNo = new JLabel("Bill No.");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
					.addGap(1))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBillNo, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addComponent(textFieldBillNo, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 464, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblMeterNumber, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 427, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
									.addComponent(textFieldMeterNumber, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
									.addComponent(comboBoxCustomers, 0, 530, Short.MAX_VALUE)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(lblRate, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
											.addComponent(textFieldRate, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
										.addGap(26)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(textFieldUnits, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblUnits, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(lblAmount, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
											.addComponent(textFieldAmount, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)))))))
					.addGap(35))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGap(18)
					.addComponent(lblBillNo)
					.addGap(6)
					.addComponent(textFieldBillNo, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblMeterNumber)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblRate)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldRate, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUnits)
								.addComponent(lblAmount))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textFieldUnits, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldAmount, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
					.addGap(66))
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
						int customerId = customers.get(comboBoxCustomers.getSelectedIndex()).getId();
						ConnectionModel connectionModel = dbHelper.findConnectionByCustomer(customerId);
						int amount = Integer.parseInt(textFieldAmount.getText());
						int rate = Integer.parseInt(textFieldRate.getText());
						int units = Integer.parseInt(textFieldUnits.getText());
						int connectionId = connectionModel.getId();
						String billNo = textFieldBillNo.getText();
						
						Boolean added = dbHelper.AddBill(connectionId, rate, units, amount,billNo);
						
						if(added) {
							MessageDialog.ShowPlainMessage("Successfully added!");
						}else {
							MessageDialog.ShowDefaultErrorMessage();
						}
						
						setVisible(false);
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
