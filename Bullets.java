import java.awt.*;
import java.awt.geom.*;
/* TODO:
 * (X) Implement expandable bullet system (i.e rockets, lasers, etc.)
 * ( ) Implement different shapes for different bullets.
 * ( ) Collision and damage for each bullet.
 * ( ) (TENTATIVE) Friendly Fire
 */
public class Bullets {
    private String bulletName;
    private double rotation;
    // reload speed in miliseconds
    private int damage, reloadSpeed;
    private double xVelocity, yVelocity, x , y;
    private Float magnitude;
    private boolean inverseX, inverseY, infinite = false;
    // Slope = for every x there is this much y (x:slope)
    /* Calculate for the vector magnitude to ensure that as the slope -> infinity the velocity should not be faster
        in short we should use the pythagorean theorem to solve for the magnitude or the length of the slope
    */
    // (sqrt(1 ^ 2 + slope ^ 2)) or just sqrt(1 + slope^2). x = 1, y = slope, we do this because we want every distance to be equal to one another.
    /* We then divide the corresponding velocities with the magnitude, (w/o magnitude i.e yVelocity = slope * speed, this results as y -> infinity or basically as the line becomes more vertical, 
        it leads to very fast bullet speeds) doing this ensures that reagrdless of the slope, the bullet will travel at a constant speed.
    */ 
    // link to vector magnitudes (https://www.cuemath.com/magnitude-of-a-vector-formula/)
    // FOR EVERY 1 X THERE IS SLOPE AMOUNTS OF Y.
    public Bullets(int bulletType, double x, double y, double slope, boolean inverseX, boolean inverseY) {
        magnitude = (float) Math.sqrt(1 + slope * slope);
        if(magnitude.isInfinite()) {
            infinite = true;
        }
        this.inverseY = inverseY;
        this.inverseX = inverseX;
        // EXPANDABLE BULLET SYSTEM
        switch(bulletType) {
            case 1:
                this.x = x;
                this.y = y;
                bulletName = "startBullet";
                reloadSpeed = 1000;
                damage = 10;
                //base velocity = 7
                calculateSpeed(7, slope, infinite);
                break;

        }


    }

    public String getBulletName() {
        return bulletName;
    }

    public int getBulletDamage() {
        return damage;
    }

    public int getReloadSpeed() {
        return reloadSpeed;
    }

    public void calculateSpeed(int speed, double slope, boolean infinity) {
        if(infinity) {
            if(inverseY) {
                yVelocity = speed;
                xVelocity = 0;
            }
            else {
                yVelocity = -speed;
                xVelocity = 0;
            }
        }
        else {
            yVelocity = (speed / magnitude) * slope;
            xVelocity = speed / magnitude;
        }

    }

    public void move() {
        if(inverseX) {
            x += xVelocity * -1;
        }
        else {
            x += xVelocity;
        }
        y += yVelocity;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    // bullet drawings.
    // for more complex drawings, just draw it inside of the draw method since a method only returns 1 object type (if too long, just make a class).
    // placeholder
    public Ellipse2D startingBullet() {
        Ellipse2D r2 = new Ellipse2D.Double(x,y,10,5);
        return r2;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform reset = new AffineTransform();
        if(!inverseY && ! inverseX || inverseY && inverseX) {
            rotation = -Math.acos(1/magnitude);
        }
        else {
            rotation = Math.acos(1/magnitude);
        }
        switch(bulletName) {
            case "startBullet":
                g2d.rotate(rotation, x, y);
                g2d.fill(startingBullet());
        }
        g2d.setTransform(reset);
    }
}
