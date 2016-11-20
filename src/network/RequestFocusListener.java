package network;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

/**
 * Solution taken from comment section in:
 * https://tips4java.wordpress.com/2010/03/14/dialog-focus/
 * Author: Prasad Said
 * 
 * Solution used as it is simple and works in linux and all java versions
 */
public class RequestFocusListener implements HierarchyListener 
{
	public RequestFocusListener() {}

	@Override
	public void hierarchyChanged(HierarchyEvent e) 
	{
		final Component c = e.getComponent();
		if (c.isShowing() && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) 
		{
			final Window toplevel = SwingUtilities.getWindowAncestor(c);
			toplevel.addWindowFocusListener(new WindowAdapter() 
			{
				@Override
				public void windowGainedFocus(WindowEvent e) 
				{
					c.requestFocus();
					toplevel.removeWindowFocusListener(this);
				}
			});
			c.removeHierarchyListener(this);
		}
	}
}