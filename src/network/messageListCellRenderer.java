package network;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class messageListCellRenderer extends JLabel implements ListCellRenderer<Object>{

	private static final Color RUNNING_HIGHLIGHT_COLOR = Color.GREEN;
	private static final Color COMPLETE_HIGHLIGHT_COLOR = Color.RED;
	private static final Color SELECTION_HIGHLIGHT_COLOR = Color.BLUE;
	
	public messageListCellRenderer(){
		setOpaque(true);
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(((Message) value).toString());
		if(!isSelected){
			if(((Message) value).isRunning()){
				setBackground(RUNNING_HIGHLIGHT_COLOR);
			}else{
				setBackground(COMPLETE_HIGHLIGHT_COLOR);
			}
		}else{
			setBackground(SELECTION_HIGHLIGHT_COLOR);
		}
		return this;
	}

}
