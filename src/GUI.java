import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class GUI {
    private Canvas canvas;
    private TextArea textArea;

    public GUI(Stage window) {
        textArea = new TextArea();
        canvas = new Canvas(300, 300);
        Group root = new Group();
        Scene scene = new Scene(root);
        BorderPane layout = new BorderPane();
        ScrollPane scrollPane = new ScrollPane(textArea);
        VBox settingsLayout = new VBox();


        layout.setCenter(canvas);
        layout.setRight(scrollPane);
        layout.setLeft(settingsLayout);

        textArea.setEditable(false);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setPrefWidth(150);

        settingsLayout.setPadding(new Insets(10));
        settingsLayout.setSpacing(8);
        settingsLayout.getChildren().add(new Text("Settings"));
        settingsLayout.getChildren().add(new Text("\tSpeed:"));
        settingsLayout.getChildren().add(new TextField());
        Button reset = new Button("Reset");
        reset.setOnAction(event -> {
            Main.reset();
            reset();
        });
        settingsLayout.getChildren().add(reset);
        settingsLayout.setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(layout);

        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public void render(List<Creature> mobs, List<Food> foods) {
        canvas.getGraphicsContext2D().clearRect(0, 0, 300, 300);
        for(Creature c: mobs) {
            c.render(canvas.getGraphicsContext2D());
        }
        for(Food f: foods) {
            f.render(canvas.getGraphicsContext2D());
        }
    }

    public void printResults(List<Creature> mobs, int days, int deathCounter) {
        textArea.appendText("Day " + days + " results:\n");
        textArea.appendText("\tSurvivors: " + mobs.size() + "\n");
        textArea.appendText("\tDead: " + deathCounter + "\n");
        textArea.appendText("\tNewborn: " + (mobs.size() / 2) + "\n");
        textArea.appendText("\tLiving: " + (mobs.size() + mobs.size() / 2) + "\n");
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, 300, 300);
    }

    private void reset() {
        clear();
        textArea.clear();
    }
}
