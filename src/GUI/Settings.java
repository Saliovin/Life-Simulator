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
import java.util.ArrayList;
import java.util.List;

class Settings {
    List<Integer> enabledMutations;
    private VBox settingsLayout;
    private CheckBox speed;
    private CheckBox size;
    private CheckBox sight;

    Settings() {
        settingsLayout = new VBox();
        enabledMutations = new ArrayList<>();
        speed = new CheckBox("Speed");
        size = new CheckBox("Size");
        sight = new CheckBox("Sight");
        Button reset = new Button("Reset/Apply");
        Button pause = new Button("Pause");

        reset.setOnAction(event -> {
            Main.reset();
            GUI.reset();
            updateEnabledMutations();
        });
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

        settingsLayout.getChildren().add(new Text("Mutations:"));
        settingsLayout.getChildren().add(speed);
        settingsLayout.getChildren().add(size);
        settingsLayout.getChildren().add(sight);
        settingsLayout.getChildren().add(reset);
        settingsLayout.getChildren().add(pause);

        settingsLayout.setPadding(new Insets(10));
        settingsLayout.setSpacing(8);
        settingsLayout.setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    VBox getPane() {
        return settingsLayout;
    }

    private Boolean isSpeedSelected() {
        return speed.isSelected();
    }

    private Boolean isSizeSelected() {
        return size.isSelected();
    }

    private Boolean isSightSelected() {
        return sight.isSelected();
    }

    private void updateEnabledMutations() {
        enabledMutations.clear();
        if(isSpeedSelected()) {
            enabledMutations.add(0);
            enabledMutations.add(1);
        }
        if(isSizeSelected()) {
            enabledMutations.add(2);
            enabledMutations.add(3);
        }
        if(isSightSelected()) {
            enabledMutations.add(4);
            enabledMutations.add(5);
        }
    }
}
