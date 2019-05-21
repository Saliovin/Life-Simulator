import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public abstract class Sprite {
    Circle body;
    Random rng;
    Scene scene;

    public Sprite(double radius, Color color, Random rng, Scene scene){
        this.rng = rng;
        this.scene = scene;
        body = new Circle();
        body.setRadius(radius);
        body.setFill(color);
        body.setCenterX(rng.nextInt((int)scene.getWidth()));
        body.setCenterY(rng.nextInt((int)scene.getHeight()));
    }
}
