package electricity_billing_system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import electricity_billing_system.Components.Topbar;
import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.ConnectionModel;
import electricity_billing_system.database.Models.Customer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateConnectionForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldMeterNumber;

	private ArrayList<Customer> customers;
	private JComboBox comboBoxCustomers,comboBoxDates,comboBoxMonths,comboBoxYear;
	private DbHelper dbHelper;
	private int id;
	private ConnectionModel connectionModel;
	
	public UpdateConnectionForm(int id) {
		setResizable(false);
		setModal(true);
		dbHelper = new DbHelper();
		dbHelper.connect();
		this.id = id;
		this.connectionModel = dbHelper.findConnection(id);
		
		init();
		initData();
		loadComboBoxes(); 
	}
	
	private void initData() {
		customers = dbHelper.getCustomers();
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		customers.forEach(c ->{
			comboBoxModel.addElement(c.getFirstname() + " " + c.getLastname());
		});
		
		comboBoxCustomers.setModel(comboBoxModel);
		if(comboBoxModel.getSize() > 0) comboBoxCustomers.setSelectedIndex(0);
		
		textFieldMeterNumber.setText(connectionModel.getMeterNumber()+"");
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
	   
	   Date connectionDate = connectionModel.getConnectionDate();
	   
	   LocalDate date = connectionDate.toLocalDate();
	   comboBoxMonths.setSelectedItem(date.getMonthValue());
	   comboBoxDates.setSelectedItem(date.getDayOfMonth());
	   comboBoxYear.setSelectedItem(date.getYear());
		
	}
	
	
	private void init() {
		setBounds(100, 100, 544, 422);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.WEST);
		
		JLabel lblUpdateConnection = new JLabel("Update Connection");
		lblUpdateConnection.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateConnection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_2 = new JLabel("Customer:");
		
		JLabel lblNewLabel_2_1 = new JLabel("Connection Date:");
		
		JLabel lblNewLabel_1 = new JLabel("Meter Number:");
		
		textFieldMeterNumber = new JTextField();
		textFieldMeterNumber.setColumns(10);
		
		comboBoxCustomers = new JComboBox();
		comboBoxCustomers.setBackground(Color.WHITE);
		
		 comboBoxMonths = new JComboBox();
		comboBoxMonths.setBackground(Color.WHITE);
		
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
		
		Topbar topbar = new Topbar();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(topbar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(42)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(comboBoxMonths, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(comboBoxDates, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addGap(12)
								.addComponent(comboBoxYear, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(150)
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(77)
								.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addGap(78)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNewLabel_1))
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addGap(30)
										.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))
									.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
										.addGap(30)
										.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 179, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblUpdateConnection, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.addGap(116)))
					.addContainerGap(60, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(lblUpdateConnection, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldMeterNumber, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel_1)))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBoxCustomers, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblNewLabel_2)))
					.addGap(30)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNewLabel_2_1))
						.addComponent(comboBoxMonths, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxDates, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_3_1)
						.addComponent(lblNewLabel_3_2))
					.addContainerGap(43, Short.MAX_VALUE))
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
						connectionModel.setMeterNumber(Integer.parseInt(textFieldMeterNumber.getText()));;
						connectionModel.setCustomerId(customers.get(comboBoxCustomers.getSelectedIndex()).getId());
						
						int month = (int) comboBoxMonths.getSelectedItem();
						int day = (int) comboBoxDates.getSelectedItem();
						int year = (int) comboBoxYear.getSelectedItem();
						
						LocalDate localDate = LocalDate.of(year, month, day);
						java.sql.Date connectionDate = java.sql.Date.valueOf(localDate);
						
						connectionModel.setConnectionDate(connectionDate);
						
						
						Boolean updated = dbHelper.UpdateConnection(connectionModel);
						if(updated) {
							MessageDialog.ShowPlainMessage("Successfully updated!");
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
