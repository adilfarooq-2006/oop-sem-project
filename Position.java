import java.math.*;

public class Position {
    private int xPos;
    private int yPos;

    public Position(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    //getters
    public int getX() {
        return xPos;
    }
    public int getY() {
        return yPos;
    }
    
    //setters
    public void setX(int x) {
        xPos = x;
    }
    public void setY(int y) {
        yPos = y;
    }

    //methods
    public void showPosition() {
        System.out.println("(" + this.getX() + "," + this.getY() + ")");
    }

    public double getDistance(Position otherCharacter) {
        //get distance by Pythagoras Theoram
        int distanceX = this.getX();
        int distanceY = this.getY();

        // c^2 =  a^2 + b^2
        // distance = x^2 + y^2
        double distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

        return distance;
    }
}
