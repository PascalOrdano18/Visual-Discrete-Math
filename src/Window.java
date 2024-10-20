import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Window extends JFrame {

    private final int width = 600;
    private final int height = 600;
    private Graph graph;
    private Timer timer;

    public Window(){
        super("Graphics");
        setSize(width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel para dibujar
        DrawingPanel panel = new DrawingPanel();
        add(panel);

        JButton colorButton = new JButton();
        colorButton.setSize(100, 30);
        colorButton.setVisible(true);
        colorButton.setText("Blue");
        panel.add(colorButton);

        colorButton.addActionListener(e -> {
            for(Node node : graph.getNodes()){
                node.setColor(Color.BLUE);
                repaint();
            }
        });

        JButton bfsButton = new JButton();
        bfsButton.setSize(100,30);
        bfsButton.setVisible(true);
        bfsButton.setText("BFS");
        panel.add(bfsButton);

        JButton dfsButton = new JButton();
        dfsButton.setSize(100,30);
        dfsButton.setVisible(true);
        dfsButton.setText("DFS");
        panel.add(dfsButton);

        bfsButton.addActionListener(e -> panel.startBFS());
        dfsButton.addActionListener(e -> panel.startDFS());

        JButton graphButton = new JButton();
        graphButton.setSize(100,30);
        graphButton.setVisible(true);
        graphButton.setText("New Graph");
        panel.add(graphButton);

        graphButton.addActionListener(e -> {
            new DrawingPanel();
            repaint();
        });

        setVisible(true);
    }

    // Panel donde se realiza el dibujo
    public class DrawingPanel extends JPanel {

        private Node selectedNode = null;

        public DrawingPanel(){
            graph = new Graph();
            graph.setGraph(6, width, height);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Node node = getNodeAt(e.getX(), e.getY());
                    if(node != null){
                        selectedNode = node;
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e){
                    if(selectedNode != null){
                        selectedNode.setX(e.getX());
                        selectedNode.setY(e.getY());
                        selectedNode = null;
                        repaint();

                    }
                }
            });


            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if(selectedNode != null){
                        selectedNode.setX(e.getX() - (selectedNode.getSize() / 2));
                        selectedNode.setY(e.getY() - (selectedNode.getSize() / 2));
                        repaint();
                    }
                }
            });

        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            graph.drawGraph(g2);
        }

        private Node getNodeAt(int x, int y){
            for(Node node : graph.getNodes()){
                int nodeX = node.getXCoordinate();
                int nodeY = node.getYCoordinate();
                int nodeSize = node.getSize();

                if((x >= (nodeX - nodeSize) && x <= (nodeX + nodeSize)) && (y >= (nodeY - nodeSize) && y <= (nodeY + nodeSize))){
                    return node;
                }
            }
            return null;
        }


        public void startBFS(){
            Node randomNode = graph.getRandomNode();
            graph.bfs(this, randomNode);

        }

        public void startDFS(){
            Node randomNode = graph.getRandomNode();
            graph.dfs(this, randomNode);
        }



    }



}
