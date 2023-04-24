package electricity_billing_system;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import electricity_billing_system.database.DbHelper;
import electricity_billing_system.database.Models.Customer;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

	private JFrame frame;
	private JTable tableCustomers;
	JPanel panelCard;
	JPanel panelHeader;
	private JTable table_1;
	ArrayList<Customer> customers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		initData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initData() {
		DbHelper dbHelper = new DbHelper();
		dbHelper.connect();
		this.customers = dbHelper.getCustomers();
		
		loadCustomersTable();
	}
	
	private void loadCustomersTable() {
		String[] columns = {"id","firstname","lastname","phone","address"};
		DefaultTableModel tbModel = new DefaultTableModel(columns,0);
		tbModel.setRowCount(0);
		customers.forEach(c -> {
			tbModel.addRow(new Object[] {
				c.getId(),
				c.getFirstname(),
				c.getLastname(),
				c.getPhone(),
				c.getAddress(),
			});
		});
		
		tableCustomers.setModel(tbModel);
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 944, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelHeader = new JPanel();
		panelHeader.setBackground(new Color(255, 96, 0));

		panelCard = new JPanel();
		panelCard.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelCard, GroupLayout.DEFAULT_SIZE, 937, Short.MAX_VALUE)
				.addComponent(panelHeader, GroupLayout.DEFAULT_SIZE, 937, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelCard, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
		);
		panelCard.setLayout(new CardLayout(0, 0));

		JPanel panelCustomers = new JPanel();
		panelCustomers.setBackground(new Color(255, 255, 255));
		panelCard.add(panelCustomers, "customers_panel");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		scrollPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});

		JButton btnUpdateCustomer = new JButton("Update");
		btnUpdateCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddConnectionForm updateCustomer = new AddConnectionForm();
				updateCustomer.setVisible(true);
			}
		});
		btnUpdateCustomer.setEnabled(false);
		btnUpdateCustomer.setForeground(Color.WHITE);
		btnUpdateCustomer.setBackground(new Color(116, 116, 116));

		JButton btnAddCustomer = new JButton("Add New");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCustomerForm addCustomer = new AddCustomerForm();
				addCustomer.setModal(true);
				addCustomer.setVisible(true);
				
			}
		});
		btnAddCustomer.setForeground(Color.WHITE);
		btnAddCustomer.setBackground(new Color(255, 165, 89));

		JButton btnDeleteCustomer = new JButton("Delete");
		btnDeleteCustomer.setEnabled(false);
		btnDeleteCustomer.setForeground(Color.WHITE);
		btnDeleteCustomer.setBackground(new Color(62, 62, 62));
		GroupLayout gl_panelCustomers = new GroupLayout(panelCustomers);
		gl_panelCustomers.setHorizontalGroup(
			gl_panelCustomers.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCustomers.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelCustomers.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panelCustomers.createSequentialGroup()
							.addComponent(btnAddCustomer, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnUpdateCustomer, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDeleteCustomer, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelCustomers.setVerticalGroup(
			gl_panelCustomers.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCustomers.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelCustomers.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDeleteCustomer, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddCustomer, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdateCustomer, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
					.addContainerGap())
		);

		tableCustomers = new JTable();
		tableCustomers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnUpdateCustomer.setEnabled(tableCustomers.getSelectedRowCount() == 1);
				btnDeleteCustomer.setEnabled(tableCustomers.getSelectedRowCount() > 0);
			}
		});
		tableCustomers.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "FIRSTNAME", "LASTNAME", "PHONE", "ADRESS" }));
		tableCustomers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tableCustomers);
		panelCustomers.setLayout(gl_panelCustomers);

		JPanel panelConnections = new JPanel();
		panelConnections.setBackground(new Color(255, 255, 255));
		panelCard.add(panelConnections, "connections_panel");

		JScrollPane scrollPane_1 = new JScrollPane();

		JButton btnNewButton_1_1 = new JButton("Add New");
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setBackground(new Color(255, 165, 89));

		JButton btnUpdate_1 = new JButton("Update");
		btnUpdate_1.setForeground(Color.WHITE);
		btnUpdate_1.setBackground(new Color(116, 116, 116));

		JButton btnDelete_1 = new JButton("Delete");
		btnDelete_1.setForeground(Color.WHITE);
		btnDelete_1.setBackground(new Color(62, 62, 62));
		GroupLayout gl_panelConnections = new GroupLayout(panelConnections);
		gl_panelConnections.setHorizontalGroup(gl_panelConnections.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panelConnections.createSequentialGroup().addContainerGap().addGroup(gl_panelConnections
						.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
						.addGroup(gl_panelConnections.createSequentialGroup()
								.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 146,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnUpdate_1, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
								.addGap(12).addComponent(btnDelete_1, GroupLayout.PREFERRED_SIZE, 146,
										GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_panelConnections.setVerticalGroup(gl_panelConnections.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelConnections.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelConnections.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 37,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnUpdate_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDelete_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE).addContainerGap()));

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Customer", "Connection Type", "Connection Date", "Status" }));
		scrollPane_1.setViewportView(table_1);
		panelConnections.setLayout(gl_panelConnections);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(62, 62, 62));

		JButton btnCustomers = new JButton("Customers");
		btnCustomers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeTab((JButton) e.getSource());
			}
		});
		btnCustomers.setBorder(null);
		btnCustomers.setBackground(new Color(255, 255, 255));

		JButton btnConnections = new JButton("CONNECTIONS");
		btnConnections.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConnections.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeTab((JButton) e.getSource());
			}
		});

		btnConnections.setForeground(new Color(255, 255, 255));
		btnConnections.setBorder(null);
		btnConnections.setBackground(new Color(255, 96, 0));

		JButton btnBilling = new JButton("BILLING");
		btnBilling.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBilling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeTab((JButton) e.getSource());
			}
		});
		btnBilling.setForeground(Color.WHITE);
		btnBilling.setBorder(null);
		btnBilling.setBackground(new Color(255, 96, 0));

		JButton btnReadings = new JButton("READINGS");
		btnReadings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeTab((JButton) e.getSource());
			}
		});
		btnReadings.setForeground(Color.WHITE);
		btnReadings.setBorder(null);
		btnReadings.setBackground(new Color(255, 96, 0));
		GroupLayout gl_panelHeader = new GroupLayout(panelHeader);
		gl_panelHeader.setHorizontalGroup(
			gl_panelHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addComponent(btnCustomers, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConnections, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnBilling, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnReadings, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(434, Short.MAX_VALUE))
				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 925, Short.MAX_VALUE)
		);
		gl_panelHeader.setVerticalGroup(
			gl_panelHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCustomers, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBilling, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnConnections, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReadings, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
		);

		JLabel lblNewLabel = new JLabel("Electricity Billing System");
		lblNewLabel.setIconTextGap(16);
		lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("/electricity_billing_system/assets/logo.png")));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));

		JLabel lblNewLabel_1 = new JLabel("Admin");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(359)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(243)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panelHeader.setLayout(gl_panelHeader);
		frame.getContentPane().setLayout(groupLayout);
	}

	private void ChangeTab(JButton btn) {
		for (Component b : panelHeader.getComponents()) {
			if (b instanceof JButton) {
				b.setBackground(Color.getColor("#FF6000"));
				b.setForeground(Color.WHITE);
			}
		}
		btn.setBackground(Color.WHITE);
		btn.setForeground(Color.getColor("#454545"));

		CardLayout cl = (CardLayout) panelCard.getLayout();
		switch (btn.getText().toLowerCase()) {
		case "customers":
			cl.show(panelCard, "customers_panel");
			break;
		case "connections":
			cl.show(panelCard, "connections_panel");
			break;
		}
	}

	private static class __Tmp {
		private static void __tmp() {
			javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
