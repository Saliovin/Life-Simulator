package GUI;

import Main.Main;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

class Settings {
    List<Integer> enabledMutations;
    double foodEnergy;
    double creatureEnergy;
    double replicationThreshold;
    double mutationChance;
    TextField foodSpawnRateTF;
    private TextField foodEnergyTF;
    private TextField creatureEnergyTF;
    private TextField replicationThresholdTF;
    private TextField mutationChanceTF;
    private ScrollPane scrollPane;
    private CheckBox speed;
    private CheckBox size;
    private CheckBox sight;

    Settings() {
        //Initialization
        VBox settingsLayout = new VBox();
        scrollPane = new ScrollPane(settingsLayout);
        enabledMutations = new ArrayList<>();
        speed = new CheckBox("Speed");
        size = new CheckBox("Size");
        sight = new CheckBox("Sight");
        foodSpawnRateTF = new TextField("30");
        foodEnergyTF = new TextField("5000");
        creatureEnergyTF = new TextField("5000");
        replicationThresholdTF = new TextField("50000");
        mutationChanceTF = new TextField("5");
        Button reset = new Button("Reset/Apply");
        Button pause = new Button("Start");
        TextField initialCreatureCount = new TextField("10");
        TextField initialFoodCount = new TextField("10");
        TextField areaWidth = new TextField("600");
        TextField areaHeight = new TextField("600");
        TextField initialSize = new TextField("3");
        TextField initialSpeed = new TextField("100");
        TextField initialSight = new TextField("20");
        Text entities = new Text("Entities:");
        Text environment = new Text("Environment:");
        Text initialValues = new Text("Initial Values:");
        Text mutations = new Text("Mutations:");

        //Text styles
        entities.setStyle("-fx-font-weight: bold");
        environment.setStyle("-fx-font-weight: bold");
        initialValues.setStyle("-fx-font-weight: bold");
        mutations.setStyle("-fx-font-weight: bold");

        //Text filter to limit input to numbers only
        UnaryOperator<TextFormatter.Change> filter = input -> {
            String text = input.getText();

            if (text.matches("[0-9]*")) {
                return input;
            }

            return null;
        };

        //Reset the program and update settings on button press
        reset.setOnAction(event -> {
            Main.reset(Integer.parseInt(initialCreatureCount.getText()), Integer.parseInt(initialFoodCount.getText()),
                    Double.parseDouble(initialSize.getText()), Double.parseDouble(initialSpeed.getText()),
                    Double.parseDouble(initialSight.getText()));
            GUI.reset(Double.parseDouble(areaWidth.getText()), Double.parseDouble(areaHeight.getText()));
            updateSettings();
        });
        //Changes the value of the boolean "pause" in the Main class on button press
        pause.setOnAction(event -> {
            //Resumes simulation
            if(Main.pause) {
                Main.pause = false;
                Main.resetPrevTime();
                pause.setText("Pause");
            }
            //Pauses simulation
            else {
                Main.pause = true;
                pause.setText("Start");
            }
        });

        //Applying filter
        initialCreatureCount.setTextFormatter(new TextFormatter<String>(filter));
        initialFoodCount.setTextFormatter(new TextFormatter<String>(filter));
        areaWidth.setTextFormatter(new TextFormatter<String>(filter));
        areaHeight.setTextFormatter(new TextFormatter<String>(filter));
        foodSpawnRateTF.setTextFormatter(new TextFormatter<String>(filter));
        foodEnergyTF.setTextFormatter(new TextFormatter<String>(filter));
        creatureEnergyTF.setTextFormatter(new TextFormatter<String>(filter));
        replicationThresholdTF.setTextFormatter(new TextFormatter<String>(filter));

        //Adding UI segments
        settingsLayout.getChildren().add(entities);
        settingsLayout.getChildren().add(new Text("\tFood energy:"));
        settingsLayout.getChildren().add(foodEnergyTF);
        settingsLayout.getChildren().add(new Text("\tPrey energy:"));
        settingsLayout.getChildren().add(creatureEnergyTF);
        settingsLayout.getChildren().add(new Text("\tReplication threshold:"));
        settingsLayout.getChildren().add(replicationThresholdTF);
        settingsLayout.getChildren().add(environment);
        settingsLayout.getChildren().add(new Text("\tArea width:"));
        settingsLayout.getChildren().add(areaWidth);
        settingsLayout.getChildren().add(new Text("\tArea height:"));
        settingsLayout.getChildren().add(areaHeight);
        settingsLayout.getChildren().add(new Text("\tFood spawn rate:"));
        settingsLayout.getChildren().add(foodSpawnRateTF);
        settingsLayout.getChildren().add(new Text("\tMutation chance:"));
        settingsLayout.getChildren().add(mutationChanceTF);
        settingsLayout.getChildren().add(initialValues);
        settingsLayout.getChildren().add(new Text("\tCreature count:"));
        settingsLayout.getChildren().add(initialCreatureCount);
        settingsLayout.getChildren().add(new Text("\tFood count:"));
        settingsLayout.getChildren().add(initialFoodCount);
        settingsLayout.getChildren().add(new Text("\tSize:"));
        settingsLayout.getChildren().add(initialSize);
        settingsLayout.getChildren().add(new Text("\tSpeed:"));
        settingsLayout.getChildren().add(initialSpeed);
        settingsLayout.getChildren().add(new Text("\tSight:"));
        settingsLayout.getChildren().add(initialSight);
        settingsLayout.getChildren().add(mutations);
        settingsLayout.getChildren().add(speed);
        settingsLayout.getChildren().add(size);
        settingsLayout.getChildren().add(sight);
        settingsLayout.getChildren().add(reset);
        settingsLayout.getChildren().add(pause);
        settingsLayout.setPadding(new Insets(10));
        settingsLayout.setSpacing(8);

        //Pane settings
        scrollPane.setPrefHeight(600);
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    ScrollPane getPane() {
        return scrollPane;
    }

    private void updateSettings() {
        //Updates enabledMutations to include those with ticks
        enabledMutations.clear();
        if(speed.isSelected()) {
            enabledMutations.add(0);
            enabledMutations.add(1);
        }
        if(size.isSelected()) {
            enabledMutations.add(2);
            enabledMutations.add(3);
        }
        if(sight.isSelected()) {
            enabledMutations.add(4);
            enabledMutations.add(5);
        }

        //Update energy related settings
        foodEnergy = Double.parseDouble(foodEnergyTF.getText());
        creatureEnergy = Double.parseDouble(creatureEnergyTF.getText());
        replicationThreshold = Double.parseDouble(replicationThresholdTF.getText());

        //Mutation chance
        mutationChance = Integer.parseInt(mutationChanceTF.getText()) / 100.0;
        if(mutationChance > 1) {
            mutationChanceTF.setText("100");
            mutationChance = 1.0;
        }
        else if(mutationChance < 0) {
            mutationChanceTF.setText("0");
            mutationChance = 0.0;
        }
    }
}
