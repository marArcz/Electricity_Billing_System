package electricity_billing_system;

import javax.swing.JOptionPane;

public class MessageDialog {
	public static void ShowPlainMessage(String message) {
		JOptionPane.showInternalMessageDialog(null, message, "",JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public static void ShowDefaultErrorMessage() {
		ShowPlainMessage("Something went wrong, please try again later!");
	}
}