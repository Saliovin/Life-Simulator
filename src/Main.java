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

                List<Creature> tempMobs = new ArrayList<>();
                tempMobs.addAll(mobs);
                for(Creature c: tempMobs) {
                    c.move(0.016666666, mobs, foods);

                    if(c.getEnergy() <= 0) {
                        mobs.remove(c);
                    }
                }

                createEntity(1, 3);

                gui.render(mobs, foods);
            }
        };

        gameLoop.start();
    }

    static void reset() {
        mobs.clear();
        foods.clear();
        createEntity(0, 50);
        createEntity(1, 50);
    }

    private static void createEntity(int type, int number) {
        if(type == 0) {
            for(int i = 0; i < number; i++) {
                mobs.add(new Creature(3, Color.RED, 100, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
            }
        }
        else if(type == 1){
            for(int i = 0; i < number; i++) {
                foods.add(new Food(1, Color.GREEN, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
            }
        }
    }
}


