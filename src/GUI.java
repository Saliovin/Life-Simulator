import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class GUI {
    private Group root;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Scene scene;
    private BorderPane layout;
    private TextArea textArea;
    private ScrollPane scrollPane;
    private Stage window;

    public GUI(Stage window) {
        this.window = window;
        root = new Group();
        canvas = new Canvas(300, 300);
        graphicsContext = canvas.getGraphicsContext2D();
        scene = new Scene(root);
        layout = new BorderPane();
        textArea = new TextArea();
        scrollPane = new ScrollPane(textArea);

        layout.setCenter(canvas);
        layout.setRight(scrollPane);
        textArea.setEditable(false);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setPrefWidth(300);
        root.getChildren().add(layout);

        window.setScene(scene);
        window.show();
    }

    public void render(List<Creature> mobs, List<Food> foods) {
        graphicsContext.clearRect(0, 0, 300, 300);
        for(Creature c: mobs) {
            c.render(graphicsContext);
        }
        for(Food f: foods) {
            f.render(graphicsContext);
        }
    }

    public void printResults(List<Creature> mobs, int days) {
        textArea.appendText("Day " + days + " results:\n");
        textArea.appendText("Survivors: " + mobs.size() + "\n");
    }
    public Canvas getCanvas() {
        return canvas;
    }
}
