package electricity_billing_system.Components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Topbar extends JPanel {

	/**
	 * Create the panel.
	 */
	public Topbar() {
		setBackground(new Color(62, 62, 62));
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Electricity Billing System");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(62, 62, 62));
		lblNewLabel.setIconTextGap(6);
		lblNewLabel.setIcon(new ImageIcon(Topbar.class.getResource("/electricity_billing_system/assets/logo.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel);

	}

}
