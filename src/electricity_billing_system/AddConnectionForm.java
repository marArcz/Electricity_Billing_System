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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import electricity_billing_system.Components.Topbar;
import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.ConnectionModel;
import electricity_billing_system.database.Models.Customer;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class AddConnectionForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldMeterNumber;
	private ArrayList<Customer> customers;
	private JComboBox comboBoxCustomers,comboBoxDates,comboBoxMonths,comboBoxYear;
	private DbHelper dbHelper;
	
	public AddConnectionForm() {
		setResizable(false);
		setModal(true);
		dbHelper = new DbHelper();
		dbHelper.connect();
		init();
		initData();
		loadComboBoxes();
	}
	
	private void initData() {
		customers = dbHelper.getCustomersWithNoActiveConnection();
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		customers.forEach(c ->{
			comboBoxModel.addElement(c.getFirstname() + " " + c.getLastname());
		});
		
		comboBoxCustomers.setModel(comboBoxModel);
		if(comboBoxModel.getSize() > 0) comboBoxCustomers.setSelectedIndex(0);
	}
	
	private void loadComboBoxes() {
		//months
		DefaultComboBoxModel<Integer> monthsModel = new DefaultComboBoxModel<>();
		for(int x=1;x<=12;x++) {
			monthsModel.addElement(x);
		}
		//days
		DefaultComboBoxModel<Integer> datesModel = new DefaultComboBoxModel<>();
		for(int x=1;x<=31;x++) {
			datesModel.addElement(x);
		}
		//months
	   DefaultComboBoxModel<Integer> yearsModel = new DefaultComboBoxModel<>();
	   int currentYear = LocalDate.now().getYear();
	   for(int x=2000;x<=currentYear;x++) {
		    yearsModel.addElement(x);
	   }
	   comboBoxMonths.setModel(monthsModel);
	   comboBoxDates.setModel(datesModel);
	   comboBoxYear.setModel(yearsModel);
	   
	   comboBoxMonths.setSelectedIndex(0);
	   comboBoxDates.setSelectedIndex(0);
	   comboBoxYear.setSelectedIndex(0);
		
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
		comboBoxCustomers.setBackground(Color.WHITE);
		textFieldMeterNumber = new JTextField();
		textFieldMeterNumber.setColumns(10);
		
		Topbar topbar = new Topbar();
		
		JLabel lblNewLabel_2_1 = new JLabel("Connection Date:");
		
		comboBoxMonths = new JComboBox();
		comboBoxMonths.setBackground(new Color(255, 255, 255));
		
		comboBoxDates = new JComboBox();
		comboBoxDates.setBackground(Color.WHITE);
		
		comboBoxYear = new JComboBox();
		comboBoxYear.setBackground(Color.WHITE);
		
		JLabel lblNewLabel_3 = new JLabel("Month");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_3_1 = new JLabel("Date");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_3_2 = new JLabel("Year");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(topbar, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(232, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addGap(202))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(36, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2_1)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(textFieldMeterNumber)
						.addComponent(comboBoxCustomers, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(comboBoxMonths, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxDates, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBoxYear, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
					.addGap(60))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(186)
					.addComponent(lblNewLabel_3)
					.addGap(77)
					.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(78)
					.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(83, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addGap(30)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxMonths, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2_1)
						.addComponent(comboBoxDates, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_3_1)
						.addComponent(lblNewLabel_3_2))
					.addGap(32))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.setBackground(new Color(238, 238, 238));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(comboBoxCustomers.getSelectedIndex() < 0) {
							MessageDialog.ShowPlainMessage("You need to select a customer!");
							return;
						}
						
						int meterNumber = Integer.parseInt(textFieldMeterNumber.getText());
						int customerId = customers.get(comboBoxCustomers.getSelectedIndex()).getId();
						
						int month = (int)comboBoxMonths.getSelectedItem();
						int date = (int)comboBoxDates.getSelectedItem();
						int year = (int)comboBoxYear.getSelectedItem();
						
						DbHelper dbHelper = new DbHelper();
						dbHelper.connect();
						LocalDate localDate = LocalDate.of(year, month, date);
						java.sql.Date connectionDate = java.sql.Date.valueOf(localDate);
						
						Boolean added = dbHelper.AddConnection(meterNumber, customerId,connectionDate);
						
						if(added) {
							MessageDialog.ShowPlainMessage("Successfully added!");
						}else {
							MessageDialog.ShowDefaultErrorMessage();
						}
						
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBackground(new Color(255, 255, 255));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
