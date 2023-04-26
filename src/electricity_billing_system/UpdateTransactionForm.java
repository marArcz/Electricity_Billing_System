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
import electricity_billing_system.database.Models.Customer;
import electricity_billing_system.database.Models.TransactionModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class UpdateTransactionForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldPayment;
	DbHelper dbHelper;
	ArrayList<Bill> bills;
	JComboBox comboBoxBills;
	int id;
	TransactionModel transaction;
	public UpdateTransactionForm(int id) {
		setModal(true);
		setResizable(false);
		dbHelper = new DbHelper();
		dbHelper.connect();
		this.id = id;
		transaction = dbHelper.findTransaction(id);
		init();
		initData();
		
		((PlainDocument) textFieldPayment.getDocument()).setDocumentFilter(new IntFilter());
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
			int index = 0;
			for(int x=0;x<bills.size();x++) {
				if(bills.get(x).getId() == this.id) {
					index = x;
					break;
				}
			}
			
			comboBoxModel.setSelectedItem(index);
		}
		
		textFieldPayment.setText(transaction.getAmount() + "");
	}
	
	void init() {
		setBounds(100, 100, 630, 424);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		Topbar topbar = new Topbar();
		
		JLabel lblNewLabel = new JLabel("Update Transaction");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblNewLabel_1 = new JLabel("Bill:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxBills = new JComboBox();
		JLabel lblNewLabel_2 = new JLabel("Payment:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldPayment = new JTextField();
		textFieldPayment.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(topbar, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(33)
					.addComponent(lblNewLabel_1)
					.addContainerGap(564, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(248)
					.addComponent(lblNewLabel)
					.addContainerGap(247, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel_2)
						.addComponent(comboBoxBills, 0, 554, Short.MAX_VALUE)
						.addComponent(textFieldPayment))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(topbar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(lblNewLabel_1)
					.addGap(11)
					.addComponent(comboBoxBills, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textFieldPayment, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(82, Short.MAX_VALUE))
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
						int billId = bills.get(comboBoxBills.getSelectedIndex()).getId();
						int amount = Integer.parseInt(textFieldPayment.getText());
						
						transaction.setBillId(billId);
						transaction.setAmount(amount);
						
						Boolean updated = dbHelper.updateTransaction(transaction);
						
						if(updated) {
							MessageDialog.ShowPlainMessage("Successfully updated!");
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
