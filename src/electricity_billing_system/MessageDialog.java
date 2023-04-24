package electricity_billing_system;

import javax.swing.JOptionPane;

public class MessageDialog {
	public static void ShowPlainMessage(String message) {
		JOptionPane.showInternalMessageDialog(null, message, "",JOptionPane.PLAIN_MESSAGE);
	}
}