import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Creature extends Sprite{
    private int angleOfDir;
    private double speed;
    private boolean hasEaten;

    public Creature(double radius, Color color, double speed, Random rng, Canvas canvas) {
        super(radius, color, rng, canvas);
        this.speed = speed;
        angleOfDir = rng.nextInt(360);
        hasEaten = false;
    }

    public void move(double time) {
        angleOfDir += rng.nextInt(51) - 25;
        xPos += speed*Math.cos(Math.toRadians(angleOfDir))*time;
        yPos += speed*Math.sin(Math.toRadians(angleOfDir))*time;

        if(xPos + radius * 2 > canvas.getWidth()) {
            angleOfDir = rng.nextInt(181) + 90;
            xPos = canvas.getWidth() - radius * 2;
        }
        else if(xPos < 0) {
            angleOfDir = rng.nextInt(181) - 90;
            xPos = 0;
        }
        if(yPos + radius * 2 > canvas.getHeight()) {
            angleOfDir = rng.nextInt(181) + 180;
            yPos = canvas.getHeight() - radius * 2;
        }
        else if(yPos < 0) {
            angleOfDir = rng.nextInt(181);
            yPos = 0;
        }
    }

    public boolean hasEaten() {
        return hasEaten;
    }

    public void setHasEaten(boolean b) {
        hasEaten = b;
    }

    public boolean collidesWith(Sprite s) {
        return Math.sqrt(Math.pow((xPos + radius) - (s.xPos + s.radius), 2) + Math.pow((yPos + radius) - (s.yPos + s.radius), 2))  <= (radius + s.radius);
    }
}
