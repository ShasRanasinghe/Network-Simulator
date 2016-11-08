package network;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author Shasthra Ranasinghe
 * 
 *Class used to set the color of the Jlist of messages,
 *to show if the message is running or completed
 */
@SuppressWarnings("serial")
public class MessageListCellRenderer extends JLabel implements ListCellRenderer<Object>{

	private static final Color RUNNING_HIGHLIGHT_COLOR = Color.GREEN;
	private static final Color COMPLETE_HIGHLIGHT_COLOR = Color.RED;
	private static final Color SELECTION_HIGHLIGHT_COLOR = Color.BLUE;
	
	public MessageListCellRenderer(){
		setOpaque(true);
		
	}
	
	@Override
	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(((Message) value).toString());
		if(!isSelected){
			if(((Message) value).isRunning()){
				setBackground(RUNNING_HIGHLIGHT_COLOR); //if the message is still running set highlight to GREEN
			}else{
				setBackground(COMPLETE_HIGHLIGHT_COLOR); //if the message is no longer running set highlight to RED
			}
		}else{
			setBackground(SELECTION_HIGHLIGHT_COLOR); //if message is selected, highlight it BLUE
		}
		return this;
	}

}
