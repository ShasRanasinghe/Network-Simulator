package network;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.*;

/**
 * GraphPanel creates the panel that allows a visual of the network to be displayed
 * The graph is displayed with dots and edges connecting them and can be resized
 * Functionalities are: Add node, delete node, add edge and clear graph
 *  
 * @author Mohamed Dahrouj, Andrew Ward
 * Highly refactored to meet project needs from Dr.John B. Matthews implementation(https://sites.google.com/site/drjohnbmatthews/graphpanel)
 */
public class GraphPanel extends JComponent {

    private static final int WIDE = 640;
    private static final int HIGH = 480;
    //Color set to blue
    public static final Color color = new Color(0x1E12FF);
    private ControlPanel control = new ControlPanel();
    public static final int radius = 20;
    public List<GraphicNode> graphicNodes = new ArrayList<GraphicNode>();
    private List<GraphicNode> selected = new ArrayList<GraphicNode>();
    public List<GraphicEdge> graphicEdges = new ArrayList<GraphicEdge>();
    public Point mousePt = new Point(WIDE / 2, HIGH / 2);
    private Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;

    /**
     * Runs the GraphPanel
     * @param args Arguments for main method
     * @throws Exception when error is found
     */
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("GraphPanel");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphPanel gp = new GraphPanel();
                f.add(new JScrollPane(gp), BorderLayout.CENTER);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }

    
    /**
     * Constructor for GraphPanel
     */
    public GraphPanel() {
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (GraphicEdge e : graphicEdges) {
            e.draw(g);
        }
        for (GraphicNode n : graphicNodes) {
            n.draw(g);
        }
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                mouseRect.width, mouseRect.height);
        }
    }

    /**
     * Mouse Handler internal class 
     *
     */
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (e.isShiftDown()) {
                GraphicNode.selectToggle(graphicNodes, mousePt);
            } else if (e.isPopupTrigger()) {
                GraphicNode.selectOne(graphicNodes, mousePt);
                showPopup(e);
            } else if (GraphicNode.selectOne(graphicNodes, mousePt)) {
                selecting = false;
            } else {
                GraphicNode.selectNone(graphicNodes);
                selecting = true;
            }
            e.getComponent().repaint();
        }

        private void showPopup(MouseEvent e) {
            control.popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    /**
     * Internal class that handles mouse motion
     *
     */
    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                    Math.min(mousePt.x, e.getX()),
                    Math.min(mousePt.y, e.getY()),
                    Math.abs(mousePt.x - e.getX()),
                    Math.abs(mousePt.y - e.getY()));
                GraphicNode.selectRect(graphicNodes, mouseRect);
            } else {
                delta.setLocation(
                    e.getX() - mousePt.x,
                    e.getY() - mousePt.y);
                GraphicNode.updatePosition(graphicNodes, delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }

	/**
	 * Control Panel internal class that creates the pop up menu when right clicking
	 *
	 */
	private class ControlPanel extends JToolBar {

        private Action newNode = new NewNodeAction("New");
        private Action clearAll = new ClearAction("Clear");
        private Action connect = new ConnectAction("Connect");
        private Action delete = new DeleteAction("Delete Node");
        private JPopupMenu popup = new JPopupMenu();

        /**
         * Constructor for right click pop up menu
         */
        ControlPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);

            popup.add(new JMenuItem(newNode));
            popup.add(new JMenuItem(connect));
            popup.add(new JMenuItem(delete));
            popup.add(new JMenuItem(clearAll));
        }

    }
	
    /**
     * Controller for New Node Action that adds a node
     *
     */
    public class NewNodeAction extends AbstractAction {

        /**
         * @param name action name
         */
        public NewNodeAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            GraphicNode.selectNone(graphicNodes);
            Point p = mousePt.getLocation();
            GraphicNode n = new GraphicNode(p, radius, color);
            n.setSelected(true);
            graphicNodes.add(n);
            repaint();
        }
    }
    
    /**
     * Controller for Connect Action that connects two nodes
     *
     */
    private class ConnectAction extends AbstractAction {

        /**
         * @param name action name
         */
        public ConnectAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            GraphicNode.getSelected(graphicNodes, selected);
            if (selected.size() > 1) {
                for (int i = 0; i < selected.size() - 1; ++i) {
                    GraphicNode n1 = selected.get(i);
                    GraphicNode n2 = selected.get(i + 1);
                    graphicEdges.add(new GraphicEdge(n1, n2));
                }
            }
            repaint();
        }
    }

    /**
     * Controller for Delete Action that deletes the nodes
     *
     */
    private class DeleteAction extends AbstractAction {

        /**
         * @param name action name
         */
        public DeleteAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            ListIterator<GraphicNode> iter = graphicNodes.listIterator();
            while (iter.hasNext()) {
                GraphicNode n = iter.next();
                if (n.isSelected()) {
                    deleteEdges(n);
                    iter.remove();
                }
            }
            repaint();
        }

        /**
         * Deletes edges that are connected to the node to be deleted
         * @param n node to be deleted
         */
        private void deleteEdges(GraphicNode n) {
            ListIterator<GraphicEdge> iter = graphicEdges.listIterator();
            while (iter.hasNext()) {
                GraphicEdge e = iter.next();
                if (e.n1 == n || e.n2 == n) {
                    iter.remove();
                }
            }
        }
    }
    
    /**
     * Controller for Clear Action that clears the entire panel
     *
     */
    private class ClearAction extends AbstractAction {

        public ClearAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphicNodes.clear();
            graphicEdges.clear();
            repaint();
        }
    }

    /**
     * GraphicEdge is a pair of Nodes.
     */
    private static class GraphicEdge {

        private GraphicNode n1;
        private GraphicNode n2;

        public GraphicEdge(GraphicNode n1, GraphicNode n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        public void draw(Graphics g) {
            Point p1 = n1.getLocation();
            Point p2 = n2.getLocation();
            g.setColor(Color.darkGray);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * GraphicNode represents a node in a graph.
     */
    public static class GraphicNode {

        private Point p;
        private int r;
        private Color color;
        private boolean selected = false;
        private Rectangle b = new Rectangle();

        /**
         * Construct a new node.
         */
		public GraphicNode(Point p, int r, Color color) {
            this.p = p;
            this.r = r;
            this.color = color;
            setBoundary(b);
        }

        /**
         * Calculate this node's rectangular boundary.
         */
        private void setBoundary(Rectangle b) {
            b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
        }

        /**
         * Draw this node.
         */
        public void draw(Graphics g) {
        	//Draw Oval
        	g.setColor(this.color);
            g.fillOval(b.x, b.y, b.width, b.height);
            
            // Draw centered text
            //TODO Allow GraphicNode to have an ID variable 
            String text = "A";
            FontMetrics fm = g.getFontMetrics();
            double textWidth = fm.getStringBounds(text, g).getWidth();
            g.setColor(Color.WHITE);
            g.drawString(text, (int) (b.x - (textWidth/2) + r), (int) (b.y + (fm.getMaxAscent() / 2)) +r);  
            
            if (selected) {
                g.setColor(Color.darkGray);
                g.drawRect(b.x, b.y, b.width, b.height);
            }
        }

        /**
         * Return this node's location.
         */
        public Point getLocation() {
            return p;
        }

        /**
         * Return true if this node contains p.
         */
        public boolean contains(Point p) {
            return b.contains(p);
        }

        /**
         * Return true if this node is selected.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Mark this node as selected.
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
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
         * Select no nodes.
         */
        public static void selectNone(List<GraphicNode> list) {
            for (GraphicNode n : list) {
                n.setSelected(false);
            }
        }

        /**
         * Select a single node; return true if not already selected.
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
         * Select each node in r.
         */
        public static void selectRect(List<GraphicNode> list, Rectangle r) {
            for (GraphicNode n : list) {
                n.setSelected(r.contains(n.p));
            }
        }

        /**
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
    }

}