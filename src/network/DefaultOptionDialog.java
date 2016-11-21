package network;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Pop up dialog that prompts the user to use a default or new network
 *
 */
@SuppressWarnings("serial")
class DefaultOptionDialog extends JDialog {
	private JLabel label = new JLabel("Would you like to use the Default or New Network?");
	private JButton defaultButton = new JButton("Default");
	private JButton newNetworkButton = new JButton("New Network");
   
	/**
	 * Constructor 
	 * @param frame Frame the dialog is added to
	 * @param title Title of Dialog box
	 */
	public DefaultOptionDialog(JFrame frame, String title) {
		super(frame, title, false);
		this.setLayout(new BorderLayout(10,10));
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(defaultButton);
		panel.add(newNetworkButton);
		add(label,BorderLayout.CENTER);
		add(panel,BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(frame);
	}
	
	public void addButtonActionListener(ActionListener listener) {
		defaultButton.addActionListener(listener);
		newNetworkButton.addActionListener(listener);
	}
	
	public void putDefaultClientProperty(Object key, Object value){
		defaultButton.putClientProperty(key, value);
	}
	
	public void putNewNetworkClientProperty(Object key, Object value){
		newNetworkButton.putClientProperty(key, value);
	}
	   
}