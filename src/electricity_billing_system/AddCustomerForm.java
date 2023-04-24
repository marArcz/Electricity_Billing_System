package electricity_billing_system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.Customer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class AddCustomerForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstname;
	private JTextField textFieldLastname;
	private JTextField textFieldPhone;
	private JTextField textFieldAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddCustomerForm dialog = new AddCustomerForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddCustomerForm() {
		setBounds(100, 100, 743, 509);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(69, 69, 69));

		JLabel lblNewLabel_1 = new JLabel("Add New Customer");
		lblNewLabel_1.setForeground(new Color(255, 96, 0));
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));

		JLabel lblNewLabel_2 = new JLabel("Firstname:");
		lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		textFieldFirstname = new JTextField();
		textFieldFirstname.setColumns(10);

		textFieldLastname = new JTextField();
		textFieldLastname.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Lastname:");
		lblNewLabel_2_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		textFieldPhone = new JTextField();
		textFieldPhone.setColumns(10);

		JLabel lblNewLabel_2_2 = new JLabel("Phone:");
		lblNewLabel_2_2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		textFieldAddress = new JTextField();
		textFieldAddress.setColumns(10);

		JLabel lblNewLabel_2_2_1 = new JLabel("Address:");
		lblNewLabel_2_2_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup().addGap(291).addComponent(lblNewLabel_1)
						.addContainerGap(291, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup().addGap(27).addGroup(gl_contentPanel
						.createParallelGroup(Alignment.LEADING, false).addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_2_2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldPhone, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
						.addComponent(textFieldFirstname)).addGap(39)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2_2_1, GroupLayout.PREFERRED_SIZE, 70,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(textFieldLastname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 313,
												Short.MAX_VALUE)
										.addComponent(lblNewLabel_2_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
												70, GroupLayout.PREFERRED_SIZE)
										.addComponent(textFieldAddress, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 313,
												Short.MAX_VALUE)))
						.addGap(31)));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
				.createSequentialGroup().addComponent(panel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(lblNewLabel_1).addGap(33)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false).addComponent(textFieldLastname)
						.addComponent(textFieldFirstname, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
				.addGap(44)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2_2_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldPhone, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAddress, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(120, Short.MAX_VALUE)));
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Electricity Billing System");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.CENTER);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String firstname = textFieldFirstname.getText();
						String lastname = textFieldLastname.getText();
						String address = textFieldAddress.getText();
						String phone = textFieldPhone.getText();
						
						if(firstname.isBlank() || lastname.isBlank() || address.isBlank() || phone.isBlank()) {
							return;
						}
						
						Customer newCustomer = new Customer();
						newCustomer.setFirstname(firstname);
						newCustomer.setLastname(lastname);
						newCustomer.setAddress(address);
						newCustomer.setPhone(phone);
						
						DbHelper dbHelper = new DbHelper();
						dbHelper.connect();
						
						if(dbHelper.AddCustomer(newCustomer)) {
							MessageDialog.ShowPlainMessage("Successfully added!");
						}
					}
				});
				okButton.setForeground(new Color(255, 255, 255));
				okButton.setBackground(new Color(255, 96, 0));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBackground(new Color(209, 209, 209));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
