import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.Random;

public class Food extends Sprite{
    public Food(double radius, Color color, Random rng, Canvas canvas) {
        super(radius, color, rng, canvas);
    }
}
