import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.util.*;

import util.Resource;

public class Hurdle {
    private Timer hurdleTimer;
    private int hurdlePosX;
    private int hurdlePosY;

    private Random rand;
    private BufferedImage catcus1;
    private BufferedImage cactus2;
    private List<Cactus> cactusList;
    private List<Bird> birdList;

    public Hurdle() {
        rand = new Random();
        cactis1 = Resource.getResourceImage("data/cactis1.png");
        cactis2 = Resource.getResourceImage("data/cactis2.png");
        cactusList = new ArrayList<Cactus>();
        birdList = new ArrayList<Bird>();
    }
    public void requestHurdle() {
        // TODO implement here
        

        }

    public void waitRandomSec() {
        // TODO implement here
        return null;
    }

    public void decideRandomHurdle() {
        // TODO implement here
        return null;
    }

    public void changeHurdlePos() {
        // TODO implement here
        return null;
    }

}