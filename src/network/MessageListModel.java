package network;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class MessageListModel<T> extends DefaultListModel<Message> {

	public void update(int index)
    {
        fireContentsChanged(this, index, index);
    }
}
