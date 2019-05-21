import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private double timeModifier = 4;
    private int days = 0;

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
        Group layout = new Group();
        Scene scene = new Scene(layout, 300, 300);


        for(int i = 0; i < 100; i++) {
            mobs.add(new Creature(5, Color.RED, 50, rng, scene));
            layout.getChildren().add(mobs.get(i).body);
        }

        for(int i = 0; i < 80; i++) {
            foods.add(new Food(2, Color.GREEN, rng, scene));
            layout.getChildren().add(foods.get(i).body);
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if(currentTime - prevFrameTime > 16666666) {
                    for(Creature c: mobs) {
                        c.move(0.01666666 * timeModifier);
                        for(Food f: foods) {
                            if(!c.willLive() && c.collidesWith(f)) {
                                layout.getChildren().remove(f.body);
                                foods.remove(f);
                                c.setHasEaten(true);
                                break;
                            }
                        }
                    }

                    prevFrameTime = currentTime;
                }

                if((currentTime - prevTime) * timeModifier > 1000000000) {
                    hoursPassed++;
                    if(hoursPassed == 12) {
                        List<Creature>testMobs = new ArrayList<>();
                        testMobs.addAll(mobs);
                        for(Creature c: testMobs) {
                            if(!c.willLive()) {
                                layout.getChildren().remove(c.body);
                                mobs.remove(c);
                            }
                            else {
                                c.setHasEaten(false);
                            }
                        }
                        for(int i = 0; i < mobs.size() / 2; i++) {
                            Creature newCreature = new Creature(5, Color.RED, 50, rng, scene);
                            mobs.add(newCreature);
                            layout.getChildren().add(newCreature.body);
                        }

                        for(Food f: foods) {
                            layout.getChildren().remove(f.body);
                        }
                        foods.clear();

                        for(int i = 0; i < 80; i++) {
                            foods.add(new Food(2, Color.GREEN, rng, scene));
                            layout.getChildren().add(foods.get(i).body);
                        }

                        hoursPassed = 0;
                        days++;
                        System.out.println("Day " + days + " results:");
                        System.out.println("Survived: " + mobs.size());
                        System.out.println();
                    }

                    prevTime = currentTime;
                }
            }
        };



        window.setScene(scene);
        window.show();

        gameLoop.start();
    }
}
