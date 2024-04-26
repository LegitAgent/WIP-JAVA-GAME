import java.awt.*;

public interface Enemy {
    public double getHealth(); // returns health of enemy
    public double getSpeed(); // return speed of enemy
    public double getStartPosX(); // return starting x position
    public double getStartPosY(); // return starting y position
    public int getDamage(); // return damage of enemy
    public String getEnemyName(); // returns enemy name
    public void move(); // moves enemy by its x and y coordinate
    public void getPlayerCoordinates(double xP1, double yP1, double xP2, double yP2); //gets player coordinates
    public void draw(Graphics2D g2d); //draws the enemy
}
