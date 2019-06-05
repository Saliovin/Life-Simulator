package Entities;

import GUI.GUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public abstract class Sprite {
    Random rng;
    Color color;
    GUI gui;
    double xPos;
    double yPos;
    double radius;

    Sprite(double radius, Color color, Random rng, GUI gui){
        //Initialization
        this.rng = rng;
        this.gui = gui;
        this.color = color;
        this.radius = radius;
        setPos(rng.nextInt((int)gui.getCanvasWidth()), rng.nextInt((int)gui.getCanvasWidth()));
    }

    //Render sprite
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
