import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public abstract class Sprite {
    Random rng;
    Canvas canvas;
    Color color;
    double xPos;
    double yPos;
    double radius;

    public Sprite(double radius, Color color, Random rng, Canvas canvas){
        this.rng = rng;
        this.canvas = canvas;
        this.color = color;
        this.radius = radius;
        xPos = rng.nextInt((int)canvas.getWidth());
        yPos = rng.nextInt((int)canvas.getHeight());
    }

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeOval(xPos, yPos, radius * 2, radius * 2);
        graphicsContext.fillOval(xPos, yPos, radius * 2, radius * 2);
    }
}
