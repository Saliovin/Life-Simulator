package Main;

import javafx.scene.paint.Color;
import java.util.Random;

public class Food extends Sprite {
    public Food(double radius, Color color, Random rng, double xBoundary, double yBoundary) {
        super(radius, color, rng, xBoundary, yBoundary);
    }
}
