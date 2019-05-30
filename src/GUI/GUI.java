package GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Entities.*;
import java.util.List;

public class GUI {
    private static Canvas canvas;
    private static Statistics statisticsPane;
    private static int time;

    public GUI(Stage window) {
        time = 0;
        canvas = new Canvas(600, 600);
        statisticsPane = new Statistics();
        Settings settingsPane = new Settings();
        Group root = new Group();
        Scene scene = new Scene(root);
        BorderPane layout = new BorderPane();


        layout.setCenter(canvas);
        layout.setRight(statisticsPane.getPane());
        layout.setLeft(settingsPane.getPane());

        root.getChildren().add(layout);

        window.setScene(scene);
        window.setResizable(false);
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

    public Canvas getCanvas() {
        return canvas;
    }

    public void updateStatistics(List<Creature> mobs) {
        time++;
        statisticsPane.printStatistics(mobs, time);
    }
    private static void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, 600, 600);
    }

    static void reset() {
        clear();
        statisticsPane.clearStatistics();
        time = 0;
    }

}
