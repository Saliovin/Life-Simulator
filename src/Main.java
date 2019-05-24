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
    private static int days;
    private static int dayTime;
    private static int wait;
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
        days = 0;
        dayTime = 6;
        wait = -1;
        mobs = new ArrayList<>();
        foods = new ArrayList<>();
        gui = new GUI(window);

        createEntity(0, 50);
        createEntity(1, 50);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if(wait == 0) { //Simulation paused
                    return;
                }
                else if(wait == 1) { //Wait for next day
                    if((currentTime - prevTime) <= 1000000000) {
                        return;
                    }

                    createEntity(0, mobs.size() + (mobs.size() / 2));
                    createEntity(1, 50);
                    wait = -1;
                }

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

                if((currentTime - prevTime) > ((long)dayTime * 1000000000)) {
                    newDay(currentTime);
                    return;
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
        days = 0;
        createEntity(0, 50);
        createEntity(1, 50);
    }

    private static void createEntity(int type, int number) {
        if(type == 0) {
            for(int i = 0; i < number; i++) {
                mobs.add(new Creature(5, Color.RED, 100, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
            }
        }
        else if(type == 1){
            for(int i = 0; i < number; i++) {
                foods.add(new Food(2, Color.GREEN, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
            }
        }
    }

    private static void newDay(long currentTime) {
        List<Creature>testMobs = new ArrayList<>();
        int deathCounter = 0;

        testMobs.addAll(mobs);
        for(Creature c: testMobs) {
            if(!c.hasEaten()) {
                mobs.remove(c);
                deathCounter++;
            }
            else {
                c.setHasEaten(false);
                c.spawn();
            }
        }

        gui.clear();
        foods.clear();
        days++;
        gui.printResults(mobs, days, deathCounter);
        prevTime = currentTime;
        wait = 1;
    }
}


