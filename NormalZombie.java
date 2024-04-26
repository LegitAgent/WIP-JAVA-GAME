import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class NormalZombie implements Enemy {
    private int damage;
    private double speed, health, rotation;
    private double speedX, speedY;
    private String name;
    private double x, y;
    private double scaling;
    private boolean inverseX, inverseY, infinite;
    private Image zombieImage;

    public NormalZombie(double scaling, double x, double y) {
        speed = 0.33;
        health = 20;
        damage = 50;
        name = "Normal Zombie";
        this.x = x;
        this.y = y;
        this.scaling = scaling;
        zombieImage = new ImageIcon("placeholder.png").getImage();
        
    }

    @Override
    public double getHealth() {
        return health * scaling;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public double getStartPosX() {
        return x;
    }

    @Override
    public double getStartPosY() {
        return y;
    }

    @Override
    public String getEnemyName() {
        return name;
    }

    @Override
    public void move() {
        if(inverseX) {
            x += speedX * -1;
        }
        else {
            x += speedX;
        }
        y += speedY;
    }

    @Override
    public void getPlayerCoordinates(double xPlayer1, double yPlayer1, double xPlayer2, double yPlayer2) {
        // similar with bullets
        infinite = false;
        double slope;
        Float magnitude;
        // use distance formula, calculate which player is closer to the zombie.
        double player1Distance = Math.sqrt(Math.pow((xPlayer1 - x), 2) + Math.pow(yPlayer1 - y, 2));
        double player2Distance = Math.sqrt(Math.pow((xPlayer2 - x), 2) + Math.pow(yPlayer2 - y, 2));

        if(player1Distance < player2Distance)  {
            slope = (yPlayer1 - y) / (xPlayer1 - x);
            magnitude = (float) Math.sqrt(1 + slope * slope);
    
            if(xPlayer1 < x) {
                slope *= -1;
                inverseX = true;
            }
            else {
                inverseX = false;
            }
            if(yPlayer1 > y) {
                inverseY = true;
            }
            else {
                inverseY = false;
            }
        }
        else {
            slope = (yPlayer2 - y) / (xPlayer2 - x);
            magnitude = (float) Math.sqrt(1 + slope * slope);
    
            if(xPlayer2 < x) {
                slope *= -1;
                inverseX = true;
            }
            else {
                inverseX = false;
            }
            if(yPlayer2 > y) {
                inverseY = true;
            }
            else {
                inverseY = false;
            }
        }

        if(magnitude.isInfinite()) {
            infinite = true;
        }

        if(infinite) {
            if(inverseY) {
                speedY = speed;
                speedX = 0;
            }
            else {
                speedY = -speed;
                speedX = 0;
            }
        }
        else {
            speedY = (speed / magnitude) * slope;
            speedX = speed / magnitude;
        }

        // same rotation with bullet firing
        int degrees = (int) Math.toDegrees(Math.acos(1/magnitude));
        if(!inverseX && !inverseY) rotation = 90 - degrees; //Q1
        else if(inverseX && !inverseY) rotation = degrees - 90; // Q2
        else if(inverseX && inverseY) rotation = 270 - degrees; //Q3
        else if(!inverseX && inverseY) rotation = degrees - 270; //Q4
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform reset = new AffineTransform();
        g2d.rotate(Math.toRadians(rotation), x + 15, y + 15);
        g2d.drawImage(zombieImage, (int) x, (int) y, 30, 30, null);
        g2d.setTransform(reset);
    }


}
