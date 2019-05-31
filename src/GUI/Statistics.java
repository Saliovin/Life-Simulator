package GUI;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import Entities.*;
import java.util.List;

class Statistics {
    private TextArea textArea;
    private ScrollPane scrollPane;
    private StringBuilder statistics;
    private double[] statValues;

    Statistics() {
        textArea = new TextArea();
        scrollPane = new ScrollPane(textArea);
        statistics = new StringBuilder();

        textArea.setEditable(false);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(150);
    }

    void printStatistics(List<Creature> mobs, int time) {
        updateStatValues(mobs);
        updateStatistics(mobs.size(), time);
        textArea.clear();
        textArea.appendText(statistics.toString());
    }

    ScrollPane getPane() {
        return scrollPane;
    }

    void clearStatistics() {
        textArea.clear();
    }

    private String getTime(int time) {
        int minutes = time / 60;
        time = time % 60;
        return minutes + ":" + time;
    }

    private void updateStatistics(int mobSize, int time) {
        statistics.setLength(0); //Clear text
        statistics.append("Time: " + getTime(time) + "\n\n" +
                "Alive: " + mobSize + "\n\n" +
                "Max Speed: " + statValues[0] + "\n" +
                "Min Speed: " + statValues[1] + "\n" +
                "Ave Speed: " + statValues[2] + "\n\n" +
                "Max Size: " + statValues[3] + "\n" +
                "Min Size: " + statValues[4] + "\n" +
                "Ave Size: " + statValues[5] + "\n\n" +
                "Max Sight: " + statValues[6] + "\n" +
                "Min Sight: " + statValues[7] + "\n" +
                "Ave Sight: " + statValues[8]);
    }

    private void updateStatValues(List<Creature> mobs) {
        statValues = new double[9];
        statValues[1] = 999; //Initial reference for minimum speed
        statValues[4] = 999; //Initial reference for minimum size
        statValues[7] = 999; //Initial reference for minimum sight

        for(Creature c: mobs) {
            if(statValues[0] < c.getSpeed()) { //Checking for max speed
                statValues[0] = c.getSpeed();
            }
            if(statValues[1] > c.getSpeed()) { //Checking for min speed
                statValues[1] = c.getSpeed();
            }
            statValues[2] += c.getSpeed(); //Summing speed values;

            if(statValues[3] < c.getSize()) { //Checking for max size
                statValues[3] = c.getSize();
            }
            if(statValues[4] > c.getSize()) { //Checking for min size
                statValues[4] = c.getSize();
            }
            statValues[5] += c.getSize(); //Summing size values;

            if(statValues[6] < c.getSight()) { //Checking for max sight
                statValues[6] = c.getSight();
            }
            if(statValues[7] > c.getSight()) { //Checking for min sight
                statValues[7] = c.getSight();
            }
            statValues[8] += c.getSight(); //Summing sight values;
        }

        statValues[2] /= mobs.size();
        statValues[5] /= mobs.size();
        statValues[8] /= mobs.size();
    }
}

