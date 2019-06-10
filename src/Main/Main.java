package Main;

import GUI.GUI;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Entities.*;

public class Main extends Application {
    public static boolean pause;
    private static long prevTime;
    private static Random rng;
    private static List<Creature> mobs;
    private static List<Food> foods;
    private static GUI gui;
    private static int foodSpawnRate;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        //Initialization
        window.setTitle("Life Simulator");
        prevTime = System.nanoTime();
        rng = new Random();
        pause = true;
        mobs = new ArrayList<>();
        foods = new ArrayList<>();
        gui = new GUI(window);
        foodSpawnRate = gui.getFoodSpawnRate();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                //Skip if simulation is paused
                if(pause) {
                    return;
                }

                //Move all creatures and kill those with 0 energy
                List<Creature> tempMobs = new ArrayList<>(mobs);
                for(Creature c: tempMobs) {
                    c.move(0.016666666, mobs, foods);

                    if(c.getEnergy() <= 0) {
                        mobs.remove(c);
                    }
                }

                //Update statistics and spawn food every second
                if(currentTime - prevTime > 1000000000) {
                    gui.updateStatistics(mobs);
                    prevTime = currentTime;
                    createFood(foodSpawnRate);
                }

                gui.render(mobs, foods);
            }
        };

        gameLoop.start();
    }

    //Reset variables and start a new simulation
    public static void reset(int creatureCount, int foodCount, double size, double speed, double sight) {
        mobs.clear();
        foods.clear();
        foodSpawnRate = gui.getFoodSpawnRate();
        resetPrevTime();
        createCreature(creatureCount, size, speed, sight);
        createFood(foodCount);
    }

    public static void resetPrevTime() {
        prevTime = System.nanoTime();
    }

    private static void createCreature(int number, double size, double speed, double sight) {
        for(int i = 0; i < number; i++) {
            mobs.add(new Creature(size, sight, speed, Color.rgb(127, 127, 127), rng, gui));
        }
    }

    private static void createFood(int number) {
        for(int i = 0; i < number; i++) {
            foods.add(new Food(1,Color.rgb(0, 255, 0), rng, gui));
        }
    }
}


