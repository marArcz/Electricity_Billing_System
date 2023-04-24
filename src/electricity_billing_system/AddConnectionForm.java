package electricity_billing_system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import electricity_billing_system.Components.Topbar;
import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.Customer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddConnectionForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldMeterNumber;
	private ArrayList<Customer> customers;
	private JComboBox comboBoxCustomers;
	public AddConnectionForm() {
		init();
		initData();
	}
	
	private void initData() {
		DbHelper dbHelper = new DbHelper();
		dbHelper.connect();
		
		customers = dbHelper.getCustomersWithNoActiveConnection();
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		customers.forEach(c ->{
			comboBoxModel.addElement(c.getFirstname() + " " + c.getLastname());
		});
		
		comboBoxCustomers.setModel(comboBoxModel);
	}
	
	private void init() {
		setBounds(100, 100, 538, 385);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblNewLabel = new JLabel("Add Connection");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNewLabel_1 = new JLabel("Meter Number:");
		JLabel lblNewLabel_2 = new JLabel("Customer:");
		comboBoxCustomers = new JComboBox();
		comboBoxCustomers.setSelectedIndex(0);
		textFieldMeterNumber = new JTextField();
		textFieldMeterNumber.setColumns(10);
		
		Topbar topbar = new Topbar();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(topbar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(45, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(40)
							.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)))
					.addGap(42))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(232, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addGap(202))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblNewLabel_1))
						.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(9)
							.addComponent(lblNewLabel_2))
						.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(109, Short.MAX_VALUE))
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
						int meterNumber = Integer.parseInt(textFieldMeterNumber.getText());
						int customerId = customers.get(comboBoxCustomers.getSelectedIndex()).getId();
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
