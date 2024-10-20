import java.awt.*;
import java.util.*;
import javax.swing.Timer;

public class Graph {
    private HashMap<Node, ArrayList<Node>> collection;
    private ArrayList<Node> nodes = new ArrayList<>();

    Random rand = new Random();

    public Graph(){
        collection = new HashMap<>();
    }

//    public void addNode(int x, int y, int size){
//        collection.put(new Node(x, y ,size, 10), new ArrayList<>());
//    }

    public void addEdge(Node node1, Node node2){
        // Para grafos no dirigidos ambos necesitan saber que son vecinos del otro
        collection.putIfAbsent(node1, new ArrayList<>());
        collection.get(node1).add(node2);

        collection.putIfAbsent(node2, new ArrayList<>());
        collection.get(node2).add(node1);
    }


    public void setGraph(int nodesAmount, int randomX, int randomY){
        for(int i = 0; i < nodesAmount; i++){
            nodes.add(new Node(rand.nextInt((int)(randomX * 0.7)) + (int)(randomX * 0.1), rand.nextInt((int)(randomY * 0.7)) + (int)(randomY * 0.1), 20, i + 1));
        }

        for(Node node : nodes){
            addEdge(node, nodes.get(rand.nextInt(nodes.size())));
            addEdge(node, nodes.get(rand.nextInt(nodes.size())));
        }
    }

    public void drawGraph(Graphics2D g2){
        for(Map.Entry<Node, ArrayList<Node>> entry : collection.entrySet()){
            for(Node aux : entry.getValue()){
                entry.getKey().drawNode(g2);
                aux.drawNode(g2);
                g2.drawLine(entry.getKey().getXCoordinate(), entry.getKey().getYCoordinate(), aux.getXCoordinate(), aux.getYCoordinate());
            }
        }
    }

    public Node getRandomNode(){
        return nodes.get(rand.nextInt(collection.size()));
    }

    public ArrayList<Node> getNodes(){
        return nodes;
    }

    public void bfs(Window.DrawingPanel panel, Node startingNode){

        Queue<Node> queue = new LinkedList<>();
        ArrayList<Node> visited = new ArrayList<>();
        queue.add(startingNode);
        visited.add(startingNode);

        Timer timer = new Timer(800, null);
        timer.addActionListener(e -> {
            if(!queue.isEmpty()){
                Node current = queue.poll();
                current.setColor(Color.RED);
                panel.repaint();
                ArrayList<Node> neighbours = collection.get(current);
                if(neighbours != null){
                    for(Node neighbour : neighbours){
                        if(!visited.contains(neighbour)){
                            visited.add(neighbour);
                            queue.add(neighbour);
                        }
                    }
                }

            } else {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    public void dfs(Window.DrawingPanel panel, Node startingNode) {
        Stack<Node> stack = new Stack<>();
        ArrayList<Node> visited = new ArrayList<>();
        stack.push(startingNode);
        visited.add(startingNode);

        // Usamos un Timer para procesar los nodos progresivamente
        Timer timer = new Timer(800, null); // Temporizador con retraso de 800ms

        timer.addActionListener(e -> {
            if (!stack.isEmpty()) {
                Node currentNode = stack.pop();
                currentNode.setColor(Color.RED);  // Cambiar el color del nodo actual
                panel.repaint();  // Redibujar el panel

                // Obtener los vecinos del nodo actual
                ArrayList<Node> neighbours = collection.get(currentNode);
                if (neighbours != null) {
                    for (Node neighbour : neighbours) {
                        if (!visited.contains(neighbour)) {
                            stack.push(neighbour);
                            visited.add(neighbour);
                        }
                    }
                }
            } else {
                ((Timer) e.getSource()).stop();  // Detener el temporizador cuando se haya procesado todo
            }
        });

        timer.setRepeats(true);  // Repetir el temporizador hasta que todos los nodos se hayan procesado
        timer.start();  // Iniciar el temporizador
    }
}
