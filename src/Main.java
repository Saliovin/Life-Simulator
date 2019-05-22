import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private AnimationTimer gameLoop;
    private Random rng;
    private long prevFrameTime;
    private long prevTime;
    private int hoursPassed;
    private List<Creature> mobs;
    private List<Food> foods;
    private double timeModifier = 1;
    private int days = 0;
    int loopCount = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        rng = new Random();
        prevFrameTime = System.nanoTime();
        prevTime = System.nanoTime();
        hoursPassed = 0;
        mobs = new ArrayList<>();
        foods = new ArrayList<>();
        Group root = new Group();
        Canvas canvas = new Canvas(300, 300);
        Scene scene = new Scene(root);
        root.getChildren().add(canvas);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        for(int i = 0; i < 100; i++) {
            mobs.add(new Creature(5, Color.RED, 50, rng, canvas));
        }

        for(int i = 0; i < 80; i++) {
            foods.add(new Food(2, Color.GREEN, rng, canvas));
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                for(Creature c: mobs) {
                    c.move(((currentTime - prevFrameTime) / 1000000000.0) * timeModifier);
                    for(Food f: foods) {
                        if(!c.hasEaten() && c.collidesWith(f)) {
                            foods.remove(f);
                            c.setHasEaten(true);
                            break;
                        }
                    }
                }

                prevFrameTime = currentTime;

                if((currentTime - prevTime) * timeModifier > 1000000000) {
                    hoursPassed++;
                    if(hoursPassed == 12) {
                        System.out.println(loopCount);
                        loopCount = 0;
                        List<Creature>testMobs = new ArrayList<>();
                        testMobs.addAll(mobs);
                        for(Creature c: testMobs) {
                            if(!c.hasEaten()) {
                                mobs.remove(c);
                            }
                            else {
                                c.setHasEaten(false);
                            }
                        }
                        for(int i = 0; i < mobs.size() / 2; i++) {
                            Creature newCreature = new Creature(5, Color.RED, 50, rng, canvas);
                            mobs.add(newCreature);
                        }

                        foods.clear();

                        for(int i = 0; i < 80; i++) {
                            foods.add(new Food(2, Color.GREEN, rng, canvas));
                        }

                        hoursPassed = 0;
                        days++;
                        System.out.println("Day " + days + " results:");
                        System.out.println("Survived: " + mobs.size());
                        System.out.println();
                    }

                    prevTime = currentTime;
                }

                graphicsContext.clearRect(0, 0, 300, 300);
                for(Creature c: mobs) {
                    c.render(graphicsContext);
                }
                for(Food f: foods) {
                    f.render(graphicsContext);
                }
            }
        };



        window.setScene(scene);
        window.show();

        gameLoop.start();
    }
}
