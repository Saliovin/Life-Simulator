import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private static AnimationTimer gameLoop;
    private static Random rng;
    private static long prevTime;
    private static int hoursPassed;
    private static int days;
    private static List<Creature> mobs;
    private static List<Food> foods;
    private static GUI gui;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        rng = new Random();
        prevTime = System.nanoTime();
        hoursPassed = 0;
        days = 0;
        mobs = new ArrayList<>();
        foods = new ArrayList<>();
        gui = new GUI(window);

        spawnEntity(0, 100);
        spawnEntity(1, 80);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                for(Creature c: mobs) {
                    c.move(0.016666666);
                    for(Food f: foods) {
                        if(!c.hasEaten() && c.collidesWith(f)) {
                            foods.remove(f);
                            c.setHasEaten(true);
                            break;
                        }
                    }
                }

                if((currentTime - prevTime) > 1000000000) {
                    hoursPassed++;
                    if(hoursPassed == 12) {
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
                        spawnEntity(0, mobs.size() / 2);

                        foods.clear();
                        spawnEntity(1, 80);

                        hoursPassed = 0;
                        days++;
                        gui.printResults(mobs, days);
                    }

                    prevTime = currentTime;
                }

                gui.render(mobs, foods);
            }
        };

        gameLoop.start();
    }

    static void reset() {
        mobs.clear();
        foods.clear();
        prevTime = System.nanoTime();
        hoursPassed = 0;
        days = 0;
        spawnEntity(0, 100);
        spawnEntity(1, 80);
    }

    private static void spawnEntity(int type, int number) {
        if(type == 0) {
            for(int i = 0; i < number; i++) {
                mobs.add(new Creature(5, Color.RED, 50, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
            }
        }
        else if(type == 1){
            for(int i = 0; i < number; i++) {
                foods.add(new Food(2, Color.GREEN, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
            }
        }
    }
}


