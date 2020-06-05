import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.util.*;

import util.Resource;

public class Hurdle {
    private Timer hurdleTimer;
    //private int hurdlePosX;
    //private int hurdlePosY;

    private Random rand;
    private BufferedImage cactus1;
    private BufferedImage cactus2;
    private BufferedImage bird;
    private List<Cactus> cactusList;
    private List<Bird> birdList;

    private Dinosaur dinosaur;

    public Hurdle() {
        rand = new Random();
        cactus1 = Resource.getResourceImage("data/cactus1.png");
        cactus2 = Resource.getResourceImage("data/cactus2.png");
        bird = Resource.getResourceImage("data/bird.png");

        cactusList = new ArrayList<Cactus>();
        birdList = new ArrayList<Bird>();
    }
    public void requestHurdle() {
        int type = rand.nextInt(2);
        if (type == 0){
            type = rand.nextInt(2);
            if (type == 0) {
                return new Cactus(dinosaur, 400, cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1);
            } else if (type == 1) {
                return new Cactus(dinosaur, 400, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
            } else {
                return new Bird(dinosaur, 400, bird.getWidth() - 10, bird.getHeight() - 10, bird);
            }
        }

    }

    public void waitRandomSec() {

    }

    public void decideRandomHurdle() {

    }

    public void changeHurdlePos() {

    }

}