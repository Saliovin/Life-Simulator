import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private AnimationTimer gameLoop;
    private Random rng;
    private long prevTime;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        rng = new Random();
        prevTime = System.nanoTime();
        Group layout = new Group();
        Scene scene = new Scene(layout, 300, 300);
        List<Creature> mobs = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            mobs.add(new Creature(5, Color.RED, 2, rng, scene));
            layout.getChildren().add(mobs.get(i).getBody());
        }


        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if(currentTime - prevTime > 16666666) {
                    for(Creature c: mobs) {
                        c.move();
                    }
                }
            }
        };



        window.setScene(scene);
        window.show();

        gameLoop.start();
    }
}
