import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Creature {
    private Circle body;
    private int angleOfDir;
    private int speed;
    private Random rng;
    private Scene scene;

    public Creature(double radius, Color color, int speed, Random rng, Scene scene) {
        this.speed = speed;
        this.rng = rng;
        this.scene = scene;
        angleOfDir = 0;
        body = new Circle();
        body.setRadius(radius);
        body.setFill(color);
        body.setCenterX(rng.nextInt((int)scene.getWidth()));
        body.setCenterX(rng.nextInt((int)scene.getHeight()));
    }

    public void move() {
        angleOfDir += rng.nextInt(51) - 25;
        double newXPos = body.getCenterX() + speed*Math.cos(Math.toRadians(angleOfDir));
        double newYPos = body.getCenterY() + speed*Math.sin(Math.toRadians(angleOfDir));

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
