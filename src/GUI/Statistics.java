package GUI;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import Entities.*;
import java.util.List;

class Statistics {
    private TextArea textArea;
    private ScrollPane scrollPane;
    private StringBuilder statistics;
    private double maxSpeed;
    private double minSpeed;
    private double avgSpeed;
    private double maxSize;
    private double minSize;
    private double avgSize;
    private double maxSight;
    private double minSight;
    private double avgSight;

    Statistics() {
        //Initialization
        textArea = new TextArea();
        scrollPane = new ScrollPane(textArea);
        statistics = new StringBuilder();

        //UI settings
        textArea.setEditable(false);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(150);
    }

    //Clears and reprints the statistics
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

    //Converts time in seconds to time in minutes and seconds
    private String getTime(int time) {
        int minutes = time / 60;
        time = time % 60;
        return minutes + ":" + time;
    }

    private void updateStatistics(int mobSize, int time) {
        statistics.setLength(0); //Clear text
        statistics.append("Time: " + getTime(time) + "\n\n" +
                "Alive: " + mobSize + "\n\n" +
                "Max Speed: " + maxSpeed + "\n" +
                "Min Speed: " + minSpeed + "\n" +
                "Ave Speed: " + avgSpeed + "\n\n" +
                "Max Size: " + maxSize + "\n" +
                "Min Size: " + minSize + "\n" +
                "Ave Size: " + avgSize + "\n\n" +
                "Max Sight: " + maxSight + "\n" +
                "Min Sight: " + minSight + "\n" +
                "Ave Sight: " + avgSight);
    }

    private void updateStatValues(List<Creature> mobs) {
        //Resetting values;
        maxSpeed = 0.0;
        maxSize = 0.0;
        maxSight = 0.0;
        avgSpeed = 0.0;
        avgSize = 0.0;
        avgSight = 0.0;
        minSpeed = 999.0; //Initial reference for minimum speed
        minSize = 999.0; //Initial reference for minimum size
        minSight = 999.0; //Initial reference for minimum sight

        for(Creature c: mobs) {
            if(maxSpeed < c.getSpeed()) { //Checking for max speed
                maxSpeed = c.getSpeed();
            }
            if(minSpeed > c.getSpeed()) { //Checking for min speed
                minSpeed = c.getSpeed();
            }
            avgSpeed += c.getSpeed(); //Summing speed values

            if(maxSize < c.getSize()) { //Checking for max size
                maxSize = c.getSize();
            }
            if(minSize > c.getSize()) { //Checking for min size
                minSize = c.getSize();
            }
            avgSize += c.getSize(); //Summing size values

            if(maxSight < c.getSight()) { //Checking for max sight
                maxSight = c.getSight();
            }
            if(minSight > c.getSight()) { //Checking for min sight
                minSight = c.getSight();
            }
            avgSight += c.getSight(); //Summing sight values
        }

        //Get averages
        avgSize /= mobs.size();
        avgSpeed /= mobs.size();
        avgSight /= mobs.size();
    }
}

