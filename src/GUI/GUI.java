package GUI;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Entities.*;
import java.util.List;

public class GUI {
    private Canvas canvas;
    private Statistics statisticsWindow;
    private int time;

    public GUI(Stage window) {
        time = 0;
        canvas = new Canvas(600, 600);
        statisticsWindow = new Statistics();
        Group root = new Group();
        Scene scene = new Scene(root);
        BorderPane layout = new BorderPane();
        VBox settingsLayout = new VBox();


        layout.setCenter(canvas);
        layout.setRight(statisticsWindow.getPane());
        layout.setLeft(settingsLayout);

        settingsLayout.setPadding(new Insets(10));
        settingsLayout.setSpacing(8);
        settingsLayout.getChildren().add(new Text("Mutations:"));
        CheckBox speed = new CheckBox("Speed");
        settingsLayout.getChildren().add(speed);
        speed.setOnAction(event -> {
            if(speed.isSelected()) {

            }
        });
        Button reset = new Button("Reset/Apply");
        reset.setOnAction(event -> {
            Entities.reset();
            reset();
        });
        Button pause = new Button("Pause");
        pause.setOnAction(event -> {
            if(Entities.pause) {
                Entities.pause = false;
                pause.setText("Pause");
            }
            else {
                Entities.pause = true;
                pause.setText("Start");
            }
        });
        settingsLayout.getChildren().add(reset);
        settingsLayout.getChildren().add(pause);
        settingsLayout.setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"), CornerRadii.EMPTY, Insets.EMPTY)));
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
        statisticsWindow.printStatistics(mobs, time);
    }
    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, 600, 600);
    }

    private void reset() {
        clear();
        statisticsWindow.clearStatistics();
        time = 0;
    }

}
