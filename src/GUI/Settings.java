package GUI;

import Main.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

class Settings {
    private VBox settingsLayout;

    Settings() {
        settingsLayout = new VBox();
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
            Main.reset();
            GUI.reset();
        });
        Button pause = new Button("Pause");
        pause.setOnAction(event -> {
            if(Main.pause) {
                Main.pause = false;
                pause.setText("Pause");
            }
            else {
                Main.pause = true;
                pause.setText("Start");
            }
        });
        settingsLayout.getChildren().add(reset);
        settingsLayout.getChildren().add(pause);
        settingsLayout.setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    VBox getPane() {
        return settingsLayout;
    }
}
