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
        //Initialization
        super(radius, color, rng, gui);
        this.speed = speed;
        this.sight = sight;
        angleOfDir = rng.nextInt(360);
        energy = gui.getReplicationThreshold() / 2.0;
    }

    public void move(double time, List<Creature> mobs, List<Food> foods) {
        //Moves the creature
        xPos += speed*Math.cos(Math.toRadians(angleOfDir))*time; //speed*cos(angle)
        yPos += speed*Math.sin(Math.toRadians(angleOfDir))*time; //speed*sin(angle)

        //Checks if the creature is out of bounds and moves it back if true
        if(xPos + radius * 2 > gui.getCanvasWidth()) { //Out of bounds at the right
            angleOfDir = rng.nextInt(181) + 90;
            xPos = gui.getCanvasWidth() - radius * 2;
        }
        else if(xPos < 0) { //Out of bounds at the left
            angleOfDir = rng.nextInt(181) - 90;
            xPos = 0;
        }
        if(yPos + radius * 2 > gui.getCanvasHeight()) { //Out of bounds at the bottom
            angleOfDir = rng.nextInt(181) + 180;
            yPos = gui.getCanvasHeight() - radius * 2;
        }
        else if(yPos < 0) { //Out of bounds at the top
            angleOfDir = rng.nextInt(181);
            yPos = 0;
        }

        energy -= speed * radius / 3 + sight; //Deducts movement energy cost

        changeDirection(rng.nextInt(51) - 25); //New random direction within 50 degrees of current one
        List<Creature> tempMobs = new ArrayList<>(mobs);
        List<Food> tempFoods = new ArrayList<>(foods);
        //Checks if creature collides with food
        for(Food f: tempFoods) {
            if(collidesWith(f)) {
                foods.remove(f);
                addEnergy(gui.getFoodEnergy());
                //replicates if energy is sufficient
                if(getEnergy() > gui.getReplicationThreshold()) {
                    energy /= 2.0;
                    createOffspring(mobs);
                }
            }
            //Checks if food is withing sight range and changes direction towards it if true
            else if(Math.sqrt(Math.pow((xPos + radius) - (f.xPos + f.radius), 2) + Math.pow((yPos + radius) - (f.yPos + f.radius), 2)) < (f.radius + radius + sight)) { //Distance formula
                changeDirection((int)(getPolarAngle(f.xPos, f.yPos) - angleOfDir));
            }
        }
        //Checks if creature collides with a smaller creature
        for(Creature c: tempMobs) {
            if(collidesWith(c) && !this.equals(c)) {
                if(radius > c.radius * 1.2) {
                    mobs.remove(c);
                    addEnergy(gui.getCreatureEnergy());
                    if(getEnergy() > gui.getReplicationThreshold()) {
                        energy /= 2.0;
                        createOffspring(mobs);
                    }
                }
            }
            //Checks if a bigger creature is withing sight range and changes direction away it if true
            else if(c.radius > radius * 1.2 &&
                    Math.sqrt(Math.pow((xPos + radius) - (c.xPos + c.radius), 2) + Math.pow((yPos + radius) - (c.yPos + c.radius), 2)) < (c.radius + radius + sight)) {
                changeDirection((int)(getPolarAngle(c.xPos, c.yPos) + 180.0 - angleOfDir));
            }
            //Checks if a smaller creature is withing sight range and changes direction towards it if true
            else if(radius > c.radius * 1.2 &&
                    Math.sqrt(Math.pow((xPos + radius) - (c.xPos + c.radius), 2) + Math.pow((yPos + radius) - (c.yPos + c.radius), 2)) < (c.radius + radius + sight)) {
                changeDirection((int)(getPolarAngle(c.xPos, c.yPos) - angleOfDir));
            }
        }
    }

    private void addEnergy(double energyToBeAdded) {
        energy += energyToBeAdded;
    }

    //Returns true if creature collides with the entity
    private boolean collidesWith(Sprite s) {
        return Math.sqrt(Math.pow((xPos + radius) - (s.xPos + s.radius), 2) + Math.pow((yPos + radius) - (s.yPos + s.radius), 2))  <= (radius + s.radius);
    }

    public double getEnergy() {
        return energy;
    }

    public double getSpeed() {
        return speed;
    }

    public double getSight() {
        return sight;
    }

    public double getSize() {
        return radius;
    }

    //Creates an offspring with a chance of a mutation happening
    private void createOffspring(List<Creature> mobs) {
        if(rng.nextDouble() < 0.1) { //10% chance at getting a mutation
            List<Integer> enabledMutations = gui.getEnabledMutations(); //Gets a list of enabled mutations
            if(!enabledMutations.isEmpty()) {
                switch(enabledMutations.get(rng.nextInt(enabledMutations.size()))) {
                    case 0: //Speed increase mutation
                        mobs.add(new Creature(radius, sight, speed * 1.1, getOffspringColor(0.05, 0.0, 0.0), rng, gui));
                        break;
                    case 1: //Speed decrease mutation
                        mobs.add(new Creature(radius, sight, speed / 1.1, getOffspringColor(-0.05, 0.0, 0.0), rng, gui));
                        break;
                    case 2: //Size increase mutation
                        mobs.add(new Creature(radius * 1.1, sight, speed, getOffspringColor(0.0, 0.05, 0.0), rng, gui));
                        break;
                    case 3: //Size decrease mutation
                        mobs.add(new Creature(radius / 1.1, sight, speed, getOffspringColor(0.0, -0.05, 0.0), rng, gui));
                        break;
                    case 4: //Sight increase mutation
                        mobs.add(new Creature(radius, sight * 1.1, speed, getOffspringColor(0.0, 0.0, 0.05), rng, gui));
                        break;
                    case 5: //Sight decrease mutation
                        mobs.add(new Creature(radius, sight / 1.1, speed, getOffspringColor(0.0, 0.0, -0.05), rng, gui));
                        break;
                }
            }
        }
        else {
            mobs.add(new Creature(radius, sight, speed, color, rng, gui)); //Creates an exact copy of the creature
        }

        mobs.get(mobs.size() - 1).setPos(xPos, yPos); //Moves the offspring to the same location as the creature
    }

    //Returns a new color based on the mutation
    private Color getOffspringColor(double rChange, double gChange, double bChange) {
        double newRed = color.getRed() + rChange;
        //Checks if over the limits and moves them back if true
        if(newRed < 0) {
            newRed = 0;
        }
        else if(newRed > 1.0) {
            newRed = 1.0;
        }

        double newGreen = color.getGreen() + gChange;
        //Checks if over the limits and moves them back if true
        if(newGreen < 0) {
            newGreen = 0;
        }
        else if(newGreen > 1.0) {
            newGreen = 1.0;
        }

        double newBlue = color.getBlue() + bChange;
        //Checks if over the limits and moves them back if true
        if(newBlue < 0) {
            newBlue = 0;
        }
        else if(newRed > 1.0) {
            newBlue = 1.0;
        }

        return Color.color(newRed, newGreen, newBlue);
    }

    //Changes the direction of the creature
    private void changeDirection(int angleChange) {
        angleOfDir += angleChange;
        //Makes sure that values are within 0-360
        if(angleOfDir < 0) {
            angleOfDir += 360;
        }
        else if(angleOfDir > 359) {
            angleOfDir -= 360;
        }
    }

    //Gets the polar angle between 2 points
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
