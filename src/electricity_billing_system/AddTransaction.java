package electricity_billing_system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import electricity_billing_system.Components.Topbar;
import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.Bill;
import electricity_billing_system.database.Models.Customer;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddTransaction extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldPayment;
	DbHelper dbHelper;
	ArrayList<Bill> bills;
	JComboBox comboBoxBills;
	private JTextField textFieldAmount;
	
	public AddTransaction() {
		dbHelper = new DbHelper();
		dbHelper.connect();
		init();
		initData();
	}
	
	void initData() {
		bills = dbHelper.getUnpaidBills();
		
		DefaultComboBoxModel<Object> comboBoxModel = new DefaultComboBoxModel<>();
		
		bills.forEach(b ->{
			Customer customer = b.getConnectionModel().getCustomer();
			comboBoxModel.addElement(b.getBillNo() + " / " + customer.getFirstname() + " " + customer.getLastname());
		});
		
		comboBoxBills.setModel(comboBoxModel);
		
		if(bills.size() > 0) {
			comboBoxBills.setSelectedIndex(0);
		}
		
		((PlainDocument) textFieldPayment.getDocument()).setDocumentFilter(new IntFilter());
	}
	
	private void init() {
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 611, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblNewLabel = new JLabel("Add Transaction");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		Topbar topbar = new Topbar();
		
		JLabel lblNewLabel_1 = new JLabel("Bill:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		comboBoxBills = new JComboBox();
		comboBoxBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bills.size() > 0) {
					Bill bill = bills.get(comboBoxBills.getSelectedIndex());
					textFieldAmount.setText("P "+bill.getAmount()+"");
				}
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Payment:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		textFieldPayment = new JTextField();
		textFieldPayment.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Bill Amount:");
		
		textFieldAmount = new JTextField();
		textFieldAmount.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(topbar, GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(259)
					.addComponent(lblNewLabel)
					.addContainerGap(223, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addGap(510))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addGap(514))
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(textFieldAmount, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
								.addComponent(textFieldPayment, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
								.addComponent(comboBoxBills, Alignment.LEADING, 0, 529, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1, Alignment.LEADING))
							.addGap(23))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBoxBills, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(25)
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldAmount, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textFieldPayment, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(52, Short.MAX_VALUE))
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
						if(bills.size() == 0) {
							MessageDialog.ShowPlainMessage("You need to select a bill!");
							return;
						}else if(textFieldPayment.getText().isBlank()) {
							MessageDialog.ShowPlainMessage("You need to fill all the boxes!");
							return;
						}
						
						int billId = bills.get(comboBoxBills.getSelectedIndex()).getId();
						int amount = Integer.parseInt(textFieldPayment.getText());
						
						Boolean added = dbHelper.addTransaction(billId, amount);
						
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
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
