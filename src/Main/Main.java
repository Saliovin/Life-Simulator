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
    private static long prevTime;
    private static Random rng;
    public static boolean pause;
    private static List<Creature> mobs;
    private static List<Food> foods;
    private static GUI gui;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        prevTime = System.nanoTime();
        rng = new Random();
        pause = true;
        mobs = new ArrayList<>();
        foods = new ArrayList<>();
        gui = new GUI(window);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if(pause) { //Simulation paused
                    return;
                }

                List<Creature> tempMobs = new ArrayList<>(mobs);
                for(Creature c: tempMobs) {
                    c.move(0.016666666, mobs, foods);

                    if(c.getEnergy() <= 0) {
                        mobs.remove(c);
                    }
                }

                if(currentTime - prevTime > 1000000000) {
                    gui.updateStatistics(mobs);
                    prevTime = currentTime;
                    createEntity(1, 60);
                }

                gui.render(mobs, foods);
            }
        };

        gameLoop.start();
    }

    public static void reset(int creatureCount, int foodCount) {
        mobs.clear();
        foods.clear();
        resetPrevTime();
        createEntity(0, creatureCount);
        createEntity(1, foodCount);
    }

    public static void resetPrevTime() {
        prevTime = System.nanoTime();
    }

    private static void createEntity(int type, int number) {
        if(type == 0) {
            for(int i = 0; i < number; i++) {
                mobs.add(new Creature(3, 20, 100, Color.rgb(127, 127, 127), rng, gui));
            }
        }
        else if(type == 1){
            for(int i = 0; i < number; i++) {
                foods.add(new Food(1,Color.rgb(0, 255, 0), rng, gui));
            }
        }
    }
}


