package Entities;

import GUI.GUI;
import javafx.scene.paint.Color;
import java.util.Random;

public class Food extends Sprite {
    public Food(double radius, Color color, Random rng, GUI gui) {
        super(radius, color, rng, gui);
    }
}
