import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class Creature extends Sprite{
    private int angleOfDir;
    private double speed;
    private int energy;

    public Creature(double radius, Color color, double speed, Random rng, double xBoundary, double yBoundary) {
        super(radius, color, rng, xBoundary, yBoundary);
        this.speed = speed;
        angleOfDir = rng.nextInt(360);
        energy = 100;
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

        energy -= 1;

        for(Food f: foods) {
            if(collidesWith(f)) {
                foods.remove(f);
                addEnergy();
                if(getEnergy() > 100) {
                    energy -= 50;
                    mobs.add(new Creature(3, Color.RED, 100, rng, xBoundary, yBoundary));
                    mobs.get(mobs.size() - 1).setPos(xPos, yPos);
                }
                break;
            }
        }
    }

    public void addEnergy() {
        energy += 20;
    }

    public boolean collidesWith(Sprite s) {
        return Math.sqrt(Math.pow((xPos + radius) - (s.xPos + s.radius), 2) + Math.pow((yPos + radius) - (s.yPos + s.radius), 2))  <= (radius + s.radius);
    }

    public int getEnergy() {
        return energy;
    }
}
