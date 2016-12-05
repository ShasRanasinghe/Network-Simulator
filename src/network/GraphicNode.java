package network;

import static network.Constants.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

/**
 * GraphicNode represents a node in a graph.
 */
public class GraphicNode {

    private String nodeID;
	private Point p;
    private int r;
    private Color color;
    private boolean selected = false;
    private Rectangle b = new Rectangle();
    
    private ArrayList<JDialog> dialogList;

	/**
	 * @param nodeID Node id of the node to be drawn
	 * @param p The location of the panel where the node will be drawn
	 * @param r the radius of the node
	 * @param color Color of the node
	 * 
	 * Construct a new node given params
	 */
	public GraphicNode(String nodeID, Point p, int r, Color color) {
        this.nodeID = nodeID;
		this.p = p;
        this.r = r;
        this.color = color;
        setBoundary(b);
    }
	
    
	/**
	 * @param nodeID Node id of the node to be drawn 
	 * 
	 * Draws a temporary node
	 */
	public GraphicNode(String nodeID) {
        this.nodeID = nodeID;
    }

    /**
     * Calculate this node's rectangular boundary.
     */
    private void setBoundary(Rectangle b) {
        b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
    }
    
    /**
     * Returns the image used for Charles' face
     */
    private BufferedImage getCharlesImage() throws IOException {
    	return ImageIO.read(new File(CHARLESFACEPATH));
    }

    /**
     * @param g Graphics object
     */
    public void draw(Graphics g){
    	try {
			
	    	if(this.getNodeID().equals(CHARLESLOWER)||this.getNodeID().equals(CHARLESUPPER) || this.getNodeID().equals(CHARLESALLUPPER)){
	    	    g.drawImage(getCharlesImage(), b.x, b.y, b.width, b.height, null);
	    	    easterEggHunt();
	    	}
	    	else{
	    		//Draw Oval
	    		g.setColor(this.color);
	    		g.fillOval(b.x, b.y, b.width, b.height);
	        
	    		// Draw centered text
	    		FontMetrics fm = g.getFontMetrics();
	    		double textWidth = fm.getStringBounds(nodeID, g).getWidth();
	    		g.setColor(Color.WHITE);
	    		g.drawString(nodeID, (int) (b.x - (textWidth/2) + r), (int) (b.y + (fm.getMaxAscent() / 2)) +r);  
	    	}
	        if (selected) {
	            g.setColor(Color.darkGray);
	            g.drawRect(b.x, b.y, b.width, b.height);
	        }
		} catch (IOException e) {}
    }

	/**
     * @return the node ID
     */
    public String getNodeID() {
        return nodeID;
    }

    /**
     * @return the node location
     */
    public Point getLocation() {
        return p;
    }

    /**
     * @param p the location of the node
     * @return true of contains, false otherwise
     */
    public boolean contains(Point p) {
        return b.contains(p);
    }

    /**
     * @return true if selected, false otherwise
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected if selected or not
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param list list of graphic nodes
     * @param selected list of selected nodes
     * Collected all the selected nodes in list.
     */
    public static void getSelected(List<GraphicNode> list, List<GraphicNode> selected) {
        selected.clear();
        for (GraphicNode n : list) {
            if (n.isSelected()) {
                selected.add(n);
            }
        }
    }

    /**
     * @param list list of graphic nodes
     */
    public static void selectNone(List<GraphicNode> list) {
        for (GraphicNode n : list) {
            n.setSelected(false);
        }
    }

    /**
     * @param list list of graphic nodes
     * @param p the point selected
     * @return true if not already selected
     * Select a single node and returns true if not already selected.
     */
    public static boolean selectOne(List<GraphicNode> list, Point p) {
        for (GraphicNode n : list) {
            if (n.contains(p)) {
                if (!n.isSelected()) {
                    GraphicNode.selectNone(list);
                    n.setSelected(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @param list list of graphic nodes
     * @param r rectangle of selected area
     * Select each node in r.
     */
    public static void selectRect(List<GraphicNode> list, Rectangle r) {
        for (GraphicNode n : list) {
            n.setSelected(r.contains(n.p));
        }
    }

    /**
     * @param list list of graphic nodes
     * @param p the point selected
     * Toggle selected state of each node containing p.
     */
    public static void selectToggle(List<GraphicNode> list, Point p) {
        for (GraphicNode n : list) {
            if (n.contains(p)) {
                n.setSelected(!n.isSelected());
            }
        }
    }

    /**
     * @param list list of graphic nodes
     * @param d positing being updated to
     * Update each node's position by d (delta).
     */
    public static void updatePosition(List<GraphicNode> list, Point d) {
        for (GraphicNode n : list) {
            if (n.isSelected()) {
                n.p.x += d.x;
                n.p.y += d.y;
                n.setBoundary(n.b);
            }
        }
    }
    
    
	/**
	 * Edit Node ID to given ID
	 * @param nodeID new ID of node
	 */
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	
	/**
	 * Produces 50 dialogs of an image
	 */
	private void easterEggHunt() {
		Random random = new Random();
 	    dialogList = new ArrayList<>();
 	    for(int i = 0;i<50;i++){
 	    	dialogList.add(openPicDialog(random));
 	    }
 	    int response = JOptionPane.showConfirmDialog(null,EASTER_EGG_MESSAGE,"Easter Egg",JOptionPane.PLAIN_MESSAGE,JOptionPane.CLOSED_OPTION);
 	    if(response == JOptionPane.OK_OPTION || response == JOptionPane.CLOSED_OPTION){
 	    	scaryProgressBar();
 	    }
	}
	
	/**
	 * @param random Random number generator
	 * @return return a dialog at a random location on the screen
	 */
	private JDialog openPicDialog(Random random){
		JDialog dialog = new JDialog();
	    JLabel label;
		try {
			label = new JLabel( new ImageIcon(getCharlesImage()) );
			dialog.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    dialog.pack();
	    dialog.setVisible(true);
	    dialog.setLocation(new Point(random.nextInt(SCREEN_DIMENTIONS.width),random.nextInt(SCREEN_DIMENTIONS.height)));
	    return dialog;
	}
	
	/**
	 * Create a fake progress bar
	 */
	private void scaryProgressBar(){
		JFrame f = new JFrame("WARNING");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container content = f.getContentPane();
	    JProgressBar progressBar = new JProgressBar(0,100);
	    progressBar.setStringPainted(true);
	    Border border = BorderFactory.createTitledBorder("Deleting C:\\ Drive...");
	    progressBar.setBorder(border);
	    content.add(progressBar, BorderLayout.NORTH);
	    f.setLocation(600,400);
	    f.setSize(300, 100);
	    f.setVisible(true);
		
	    new ProgressWorker(progressBar, 40).execute();
	}
	
	/**
	 * @author Shasthra Ranasinghe
	 * Allows for component to run on the background
	 *
	 */
	public class ProgressWorker extends SwingWorker<Void, Integer> {

        private int delay;
        private JProgressBar pb;

        public ProgressWorker(JProgressBar progressBar, int delay) {
            this.pb = progressBar;
            this.delay = delay;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Back in the EDT...
            pb.setValue(chunks.get(chunks.size() - 1)); // only care about the last one...
        }

        @Override
        protected Void doInBackground() throws Exception {
            for (int index = 0; index <= 50; index++) {
                publish(index*2);
                dialogList.get(index).setVisible(false);
                dialogList.get(index).dispose();
                Thread.sleep(delay);
            }
            return null;
        }

        @Override
        protected void done() {
            System.exit(0);
        }

    }
}