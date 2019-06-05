package GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Entities.*;
import java.util.List;

public class GUI {
    private static Stage window;
    private static Canvas canvas;
    private static Statistics statisticsPane;
    private static Settings settingsPane;
    private static int time;

    public GUI(Stage window) {
        GUI.window = window;
        time = 0;
        canvas = new Canvas(600, 600);
        statisticsPane = new Statistics();
        settingsPane = new Settings();
        Group root = new Group();
        Scene scene = new Scene(root);
        BorderPane layout = new BorderPane();


        layout.setCenter(canvas);
        layout.setRight(statisticsPane.getPane());
        layout.setLeft(settingsPane.getPane());

        root.getChildren().add(layout);

        window.setScene(scene);
        window.setResizable(false);
        window.sizeToScene();
        window.show();
    }

    public void render(List<Creature> mobs, List<Food> foods) {
        canvas.getGraphicsContext2D().clearRect(0, 0, 600, 600);
        for(Creature c: mobs) {
            c.render(canvas.getGraphicsContext2D());
        }
        for(Food f: foods) {
            f.render(canvas.getGraphicsContext2D());
        }
    }

    public double getCanvasWidth() {
        return canvas.getWidth();
    }

    public double getCanvasHeight() {
        return canvas.getHeight();
    }

    public List<Integer> getEnabledMutations() {
        return settingsPane.enabledMutations;
    }

    public int getFoodSpawnRate() {
        return Integer.parseInt(settingsPane.foodSpawnRateTF.getText());
    }

    public double getFoodEnergy() {
        return settingsPane.foodEnergy;
    }

    public double getCreatureEnergy() {
        return settingsPane.creatureEnergy;
    }

    public double getReplicationThreshold() {
        return settingsPane.replicationThreshold;
    }

    public void updateStatistics(List<Creature> mobs) {
        time++;
        statisticsPane.printStatistics(mobs, time);
    }
    private static void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, 600, 600);
    }

    static void reset(double areaWidth, double areaHeight) {
        clear();
        canvas.setWidth(areaWidth);
        canvas.setHeight(areaHeight);
        window.sizeToScene();
        statisticsPane.clearStatistics();
        time = 0;
    }

}
