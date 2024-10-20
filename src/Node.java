import java.awt.*;

public class Node {

    private int size, x, y;
    private final int weight;
    private Color color = Color.BLUE;

    public Node(int x, int y, int size, int weight){
        this.size = size;
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public int getXCoordinate(){
        return x + (size / 2);
    }
    public int getYCoordinate(){
        return y + (size / 2);
    }

    public int getSize(){
        return size;
    }

    public int getWeight(){
        return weight;
    }

    public void drawNode(Graphics2D g2){
        g2.setColor(color);
        g2.fillOval(x, y, size, size);
        g2.drawString(("%d").formatted(weight), x, y);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

}
