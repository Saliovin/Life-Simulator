import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Creature extends Sprite{
    private int angleOfDir;
    private double speed;
    private boolean hasEaten;

    public Creature(double radius, Color color, double speed, Random rng, Scene scene) {
        super(radius, color, rng, scene);
        this.speed = speed;
        angleOfDir = rng.nextInt(360);
        hasEaten = false;
    }

    public void move(double time) {
        angleOfDir += rng.nextInt(51) - 25;
        double newXPos = body.getCenterX() + speed*Math.cos(Math.toRadians(angleOfDir))*time;
        double newYPos = body.getCenterY() + speed*Math.sin(Math.toRadians(angleOfDir))*time;

        if(newXPos > scene.getWidth()) {
            angleOfDir = rng.nextInt(181) + 90;
            newXPos = scene.getWidth();
        }
        else if(newXPos < 0) {
            angleOfDir = rng.nextInt(181) - 90;
            newXPos = 0;
        }
        if(newYPos > scene.getHeight()) {
            angleOfDir = rng.nextInt(181) + 180;
            newYPos = scene.getHeight();
        }
        else if(newYPos < 0) {
            angleOfDir = rng.nextInt(181);
            newYPos = 0;
        }

        body.setCenterX(newXPos);
        body.setCenterY(newYPos);
    }

    public boolean willLive() {
        return hasEaten;
    }

    public void setHasEaten(boolean b) {
        hasEaten = b;
    }

    public boolean collidesWith(Sprite s) {
        return Math.sqrt(Math.pow(body.getCenterX() - s.body.getCenterX(), 2) +
                Math.pow(body.getCenterY() - s.body.getCenterY(), 2))  <= (body.getRadius() + s.body.getRadius());
    }

    public int getxPos() {
        return (int)body.getCenterX();
    }

    public int getyPos() {
        return (int)body.getCenterY();
    }

    public Circle getBody() {
        return body;
    }
}
