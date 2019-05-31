package Entities;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import GUI.GUI;

public class Creature extends Sprite {
    private int angleOfDir;
    private double speed;
    private double energy;
    private double sight;

    public Creature(double radius, double sight, double speed, Color color, Random rng, GUI gui) {
        super(radius, color, rng, gui);
        this.speed = speed;
        this.sight = sight;
        angleOfDir = rng.nextInt(360);
        energy = 50000;
    }

    public void move(double time, List<Creature> mobs, List<Food> foods) {
        xPos += speed*Math.cos(Math.toRadians(angleOfDir))*time;
        yPos += speed*Math.sin(Math.toRadians(angleOfDir))*time;

        if(xPos + radius * 2 > gui.getCanvasWidth()) {
            angleOfDir = rng.nextInt(181) + 90;
            xPos = gui.getCanvasWidth() - radius * 2;
        }
        else if(xPos < 0) {
            angleOfDir = rng.nextInt(181) - 90;
            xPos = 0;
        }
        if(yPos + radius * 2 > gui.getCanvasHeight()) {
            angleOfDir = rng.nextInt(181) + 180;
            yPos = gui.getCanvasHeight() - radius * 2;
        }
        else if(yPos < 0) {
            angleOfDir = rng.nextInt(181);
            yPos = 0;
        }

        energy -= speed * radius / 3 + sight;

        changeDirection(rng.nextInt(51) - 25);
        List<Creature> tempMobs = new ArrayList<>(mobs);
        List<Food> tempFoods = new ArrayList<>(foods);
        for(Food f: tempFoods) {
            if(collidesWith(f)) {
                foods.remove(f);
                addEnergy();
                if(getEnergy() > 200000.0) {
                    energy /= 2.0;
                    createOffspring(mobs);
                }
            }
            else if(Math.sqrt(Math.pow((xPos + radius) - (f.xPos + f.radius), 2) + Math.pow((yPos + radius) - (f.yPos + f.radius), 2)) < (f.radius + radius + sight)) {
                changeDirection((int)(getPolarAngle(f.xPos, f.yPos) - angleOfDir));
            }
        }
        for(Creature c: tempMobs) {
            if(collidesWith(c) && !this.equals(c)) {
                if(radius > c.radius * 1.2) {
                    mobs.remove(c);
                    addEnergy();
                    if(getEnergy() > 200000.0) {
                        energy /= 2.0;
                        createOffspring(mobs);
                    }
                }
            }
            else if(c.radius > radius * 1.2 &&
                    Math.sqrt(Math.pow((xPos + radius) - (c.xPos + c.radius), 2) + Math.pow((yPos + radius) - (c.yPos + c.radius), 2)) < (c.radius + radius + sight)) {
                changeDirection((int)(getPolarAngle(c.xPos, c.yPos) + 180.0 - angleOfDir));
            }
            else if(radius > c.radius * 1.2 &&
                    Math.sqrt(Math.pow((xPos + radius) - (c.xPos + c.radius), 2) + Math.pow((yPos + radius) - (c.yPos + c.radius), 2)) < (c.radius + radius + sight)) {
                changeDirection((int)(getPolarAngle(c.xPos, c.yPos) - angleOfDir));
            }
        }
    }

    private void addEnergy() {
        energy += 30000.0;
    }

    private boolean collidesWith(Sprite s) {
        return Math.sqrt(Math.pow((xPos + radius) - (s.xPos + s.radius), 2) + Math.pow((yPos + radius) - (s.yPos + s.radius), 2))  <= (radius + s.radius);
    }

    public double getEnergy() {
        return energy;
    }

    public double getSpeed() {
        return speed;
    }

    private void createOffspring(List<Creature> mobs) {
        if(rng.nextDouble() < 0.1) {
            List<Integer> enabledMutations = gui.getEnabledMutations();
            if(!enabledMutations.isEmpty()) {
                switch(enabledMutations.get(rng.nextInt(enabledMutations.size()))) {
                    case 0:
                        mobs.add(new Creature(radius, sight, speed * 1.1, getOffspringColor(0.05, 0.0, 0.0), rng, gui));
                        break;
                    case 1:
                        mobs.add(new Creature(radius, sight, speed / 1.1, getOffspringColor(-0.05, 0.0, 0.0), rng, gui));
                        break;
                    case 2:
                        mobs.add(new Creature(radius * 1.1, sight, speed, getOffspringColor(0.0, 0.05, 0.0), rng, gui));
                        break;
                    case 3:
                        mobs.add(new Creature(radius / 1.1, sight, speed, getOffspringColor(0.0, -0.05, 0.0), rng, gui));
                        break;
                    case 4:
                        mobs.add(new Creature(radius, sight * 1.1, speed, getOffspringColor(0.0, 0.0, 0.05), rng, gui));
                        break;
                    case 5:
                        mobs.add(new Creature(radius, sight / 1.1, speed, getOffspringColor(0.0, 0.0, -0.05), rng, gui));
                        break;
                }
            }
        }
        else {
            mobs.add(new Creature(radius, sight, speed, color, rng, gui));
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

    private void changeDirection(int angleChange) {
        angleOfDir += angleChange;
        if(angleOfDir < 0) {
            angleOfDir += 360;
        }
        else if(angleOfDir > 359) {
            angleOfDir -= 360;
        }
    }

    private double getPolarAngle(double xPos2, double yPos2) {
        double deltaX = xPos2 - xPos;
        double deltaY = yPos2 - yPos;
        double polarAngle = Math.toDegrees(Math.atan(deltaY / deltaX));
        if(deltaX < 0) {
            polarAngle += 180.0;
        }
        else if(deltaY < 0) {
            polarAngle += 360;
        }

        return polarAngle;
    }
}
