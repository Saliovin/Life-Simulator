import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private AnimationTimer gameLoop;
    private Random rng;
    private long prevTime;
    private int hoursPassed;
    private int days = 0;
    private List<Creature> mobs;
    private List<Food> foods;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        rng = new Random();
        prevTime = System.nanoTime();
        hoursPassed = 0;
        mobs = new ArrayList<>();
        foods = new ArrayList<>();
        GUI gui = new GUI(window);

        for(int i = 0; i < 100; i++) {
            mobs.add(new Creature(5, Color.RED, 50, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
        }

        for(int i = 0; i < 80; i++) {
            foods.add(new Food(2, Color.GREEN, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
        }

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
                        for(int i = 0; i < mobs.size() / 2; i++) {
                            Creature newCreature = new Creature(5, Color.RED, 50, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight());
                            mobs.add(newCreature);
                        }

                        foods.clear();

                        for(int i = 0; i < 80; i++) {
                            foods.add(new Food(2, Color.GREEN, rng, gui.getCanvas().getWidth(), gui.getCanvas().getHeight()));
                        }

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
}


