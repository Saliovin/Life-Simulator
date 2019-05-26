import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import sun.plugin2.util.ColorUtil;

import javax.swing.text.AttributeSet;
import java.util.List;
import java.util.Random;

public class Creature extends Sprite{
    private int angleOfDir;
    private double speed;
    private double energy;

    public Creature(double radius, Color color, double speed, Random rng, double xBoundary, double yBoundary) {
        super(radius, color, rng, xBoundary, yBoundary);
        this.speed = speed;
        angleOfDir = rng.nextInt(360);
        energy = 100.0;
    }

    public void move(double time, List<Creature> mobs, List<Food> foods) {
        angleOfDir += rng.nextInt(51) - 25;
        xPos += speed*Math.cos(Math.toRadians(angleOfDir))*time;
        yPos += speed*Math.sin(Math.toRadians(angleOfDir))*time;

        if(xPos + radius * 2 > xBoundary) {
            angleOfDir = rng.nextInt(181) + 90;
            xPos = xBoundary - radius * 2;
        }
        else if(xPos < 0) {
            angleOfDir = rng.nextInt(181) - 90;
            xPos = 0;
        }
        if(yPos + radius * 2 > xBoundary) {
            angleOfDir = rng.nextInt(181) + 180;
            yPos = xBoundary - radius * 2;
        }
        else if(yPos < 0) {
            angleOfDir = rng.nextInt(181);
            yPos = 0;
        }

        energy -= speed / 100.0;

        for(Food f: foods) {
            if(collidesWith(f)) {
                foods.remove(f);
                addEnergy();
                if(getEnergy() > 100) {
                    energy -= 50.0;
                    createOffspring(mobs);
                }
                break;
            }
        }
    }

    public void addEnergy() {
        energy += 20.0;
    }

    public boolean collidesWith(Sprite s) {
        return Math.sqrt(Math.pow((xPos + radius) - (s.xPos + s.radius), 2) + Math.pow((yPos + radius) - (s.yPos + s.radius), 2))  <= (radius + s.radius);
    }

    public double getEnergy() {
        return energy;
    }

    public double getSpeed() {
        return speed;
    }

    private void createOffspring(List<Creature> mobs) {
        if(rng.nextDouble() <= 0.1) {
            switch(rng.nextInt(2)) {
                case 0:
                    mobs.add(new Creature(3, getOffspringColor(0.05, 0.0, 0.0), speed * 1.1, rng, xBoundary, yBoundary));
                    break;
                case 1:
                    mobs.add(new Creature(3, getOffspringColor(-0.05, 0.0, 0.0), speed / 1.1, rng, xBoundary, yBoundary));
                    break;
            }
        }
        else {
            mobs.add(new Creature(3, this.color, this.speed, rng, xBoundary, yBoundary));
        }

        mobs.get(mobs.size() - 1).setPos(xPos, yPos);
    }

    private Color getOffspringColor(double rChange, double gChange, double bChange) {
        double newRed = color.getRed() + rChange;
        if(newRed < 0) {
            newRed = 0;
        }
        else if(newRed > 1.0) {
            newRed = 1.0;
        }

        double newGreen = color.getGreen() + gChange;
        if(newGreen < 0) {
            newGreen = 0;
        }
        else if(newGreen > 1.0) {
            newGreen = 1.0;
        }

        double newBlue = color.getBlue() + bChange;
        if(newBlue < 0) {
            newBlue = 0;
        }
        else if(newRed > 1.0) {
            newBlue = 1.0;
        }

        return Color.color(newRed, newGreen, newBlue);
    }
}
