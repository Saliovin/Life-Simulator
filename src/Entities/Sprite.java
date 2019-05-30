package Entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public abstract class Sprite {
    Random rng;
    Color color;
    double xPos;
    double yPos;
    double xBoundary;
    double yBoundary;
    double radius;

    Sprite(double radius, Color color, Random rng, double xBoundary, double yBoundary){
        this.rng = rng;
        this.xBoundary = xBoundary;
        this.yBoundary = yBoundary;
        this.color = color;
        this.radius = radius;
        setPos(rng.nextInt((int)xBoundary), rng.nextInt((int)yBoundary));
    }

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeOval(xPos, yPos, radius * 2, radius * 2);
        graphicsContext.fillOval(xPos, yPos, radius * 2, radius * 2);
    }

    void setPos(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
