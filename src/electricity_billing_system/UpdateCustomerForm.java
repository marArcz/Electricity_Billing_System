package electricity_billing_system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import electricity_billing_system.Components.Topbar;
import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.Customer;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateCustomerForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldPhone;
	private JTextField textFieldFirstname;
	private JTextField textFieldLastname;
	private JTextField textFieldAddress;
	private int customerId;
	private Customer customer;
	private DbHelper dbHelper;

	public UpdateCustomerForm(int customerId) {
		dbHelper = new DbHelper();
		dbHelper.connect();
		this.customerId = customerId;
		init();
		initData();
	}
	
	private void initData() {
		customer = dbHelper.FindCustomer(customerId);
		
		textFieldFirstname.setText(customer.getFirstname());
		textFieldLastname.setText(customer.getLastname());
		textFieldAddress.setText(customer.getAddress());
		textFieldPhone.setText(customer.getPhone());
		
	}
	
	private void init() {
		setBounds(100, 100, 737, 469);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		Topbar topbar = new Topbar();
		JLabel lblNewLabel_1 = new JLabel("Update Customer");
		lblNewLabel_1.setForeground(new Color(255, 96, 0));
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		JLabel lblNewLabel_2 = new JLabel("Firstname:");
		lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		JLabel lblNewLabel_2_2 = new JLabel("Phone:");
		lblNewLabel_2_2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		textFieldPhone = new JTextField();
		textFieldPhone.setColumns(10);
		textFieldFirstname = new JTextField();
		textFieldFirstname.setColumns(10);
		JLabel lblNewLabel_2_2_1 = new JLabel("Address:");
		lblNewLabel_2_2_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		textFieldLastname = new JTextField();
		textFieldLastname.setColumns(10);
		JLabel lblNewLabel_2_1 = new JLabel("Lastname:");
		lblNewLabel_2_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		textFieldAddress = new JTextField();
		textFieldAddress.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
					.addGap(3))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(42)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addGap(286)
							.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNewLabel_2_2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addGap(286)
							.addComponent(lblNewLabel_2_2_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textFieldFirstname)
								.addComponent(textFieldPhone, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
							.addGap(50)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldAddress)
								.addComponent(textFieldLastname, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(63, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap(327, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(283))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(47)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldLastname, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldFirstname, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2_2_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldAddress, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldPhone, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(62, Short.MAX_VALUE))
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
						customer.setFirstname(textFieldFirstname.getText());
						customer.setLastname(textFieldLastname.getText());
						customer.setAddress(textFieldAddress.getText());
						customer.setPhone(textFieldPhone.getText());
						
						if(dbHelper.UpdateCustomer(customer)) {
							MessageDialog.ShowPlainMessage("Successfully updated!");
							setVisible(false);
						}
						
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
