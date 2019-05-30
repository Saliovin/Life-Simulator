package GUI;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import Entities.*;
import java.util.List;

public class Statistics {
    private TextArea textArea;
    private ScrollPane scrollPane;

    public Statistics() {
        textArea = new TextArea();
        scrollPane = new ScrollPane(textArea);
        textArea.setEditable(false);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(150);
    }

    public void printStatistics(List<Creature> mobs, int time) {
        textArea.clear();
        textArea.appendText("Time: " + getTime(time) + "\n");
        textArea.appendText("\tAlive: " + mobs.size() + "\n");
        textArea.appendText("\tMax Speed: " + getMaxSpeed(mobs) + "\n");
        textArea.appendText("\tMin Speed: " + getMinSpeed(mobs) + "\n");
        textArea.appendText("\tAve Speed: " + getAveSpeed(mobs) + "\n");
    }

    public ScrollPane getPane() {
        return scrollPane;
    }

    public void clearStatistics() {
        textArea.clear();
    }

    private String getTime(int time) {
        int minutes = time / 60;
        time = time % 60;
        return minutes + ":" + time;
    }

    private double getMaxSpeed(List<Creature> mobs) {
        double maxSpeed = 0;
        for(Creature c: mobs) {
            if(maxSpeed < c.getSpeed()) {
                maxSpeed = c.getSpeed();
            }
        }
        return maxSpeed;
    }

    private double getMinSpeed(List<Creature> mobs) {
        double minSpeed = 9999;
        for(Creature c: mobs) {
            if(minSpeed > c.getSpeed()) {
                minSpeed = c.getSpeed();
            }
        }
        return minSpeed;
    }

    private double getAveSpeed(List<Creature> mobs) {
        double total = 0;
        for(Creature c: mobs) {
            total += c.getSpeed();
        }
        return total / mobs.size();
    }

}

