import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public abstract class Sprite {
    private Image image;
    private double xPos;
    private double yPos;
    private double height;
    private double width;
    private double velocity;
    private int angleOfDir;
    private Random rng;

    public Sprite(double xPos, double yPos, double velocity, Random rng, Image image){
        this.xPos = xPos;
        this.yPos = yPos;
        this.image = image;
        this.velocity = velocity;
        this.rng = rng;
        height = image.getHeight();
        width = image.getWidth();
        angleOfDir = rng.nextInt(360);
    }

    public void update(double time, int sceneWidth, int sceneHeight)
    {
        angleOfDir += rng.nextInt(51) - 25;
        xPos += velocity*Math.cos(Math.toRadians(angleOfDir)) * time;
        yPos += velocity*Math.sin(Math.toRadians(angleOfDir)) * time;
        if(xPos > sceneWidth) {
            angleOfDir = rng.nextInt(181) + 90;
            xPos = sceneWidth;
        }
        else if(xPos < 0) {
            angleOfDir = rng.nextInt(181) - 90;
            xPos = 0;
        }
        if(yPos > sceneHeight) {
            angleOfDir = rng.nextInt(181) + 180;
            yPos = sceneHeight;
        }
        else if(yPos < 0) {
            angleOfDir = rng.nextInt(181);
            yPos = 0;
        }
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, xPos, yPos );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(xPos, yPos, width, height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }
}
