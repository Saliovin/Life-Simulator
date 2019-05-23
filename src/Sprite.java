import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public abstract class Sprite {
    protected Random rng;
    protected Color color;
    protected double xPos;
    protected double yPos;
    protected double xBoundary;
    protected double yBoundary;
    protected double radius;

    public Sprite(double radius, Color color, Random rng, double xBoundary, double yBoundary){
        this.rng = rng;
        this.xBoundary = xBoundary;
        this.color = color;
        this.radius = radius;
        xPos = rng.nextInt((int)xBoundary);
        yPos = rng.nextInt((int)yBoundary);
    }

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeOval(xPos, yPos, radius * 2, radius * 2);
        graphicsContext.fillOval(xPos, yPos, radius * 2, radius * 2);
    }
}
