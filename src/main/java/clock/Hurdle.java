package clock;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Resource;

public class Hurdle {
    private BufferedImage cactus1;
    private BufferedImage cactus2;
    private Random rand;

    private List<Cactus> Hurdles;
    private Dinosaur dinosaur;

    public Hurdle(Dinosaur dinosaur){
        rand = new Random();
        cactus1 = Resource.getResourceImage("resources/cactus1.png");
        cactus2 = Resource.getResourceImage("resources/cactus2.png");
        Hurdles = new ArrayList<Cactus>();
        this.dinosaur = dinosaur;
        Hurdles.add(createCactus());
    }

    public void update(){
        for(Cactus h : Hurdles){
            h.update();
        }
        Cactus hurdle = Hurdles.get(0);
        if(hurdle.isOutOfScreen()){
            dinosaur.upScore();
            Hurdles.clear();
            Hurdles.add(createCactus());
        }
    }

    public void draw(Graphics g){
        for(Cactus h : Hurdles){
            h.draw(g);
        }
    }

    private Cactus createCactus(){
        int type= rand.nextInt(2);
        if (type == 0){
            return new Cactus(dinosaur, 800, cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1);
        } else {
            return new Cactus(dinosaur, 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
        }
    }

    public boolean isCollision(){
        for(Cactus h : Hurdles){
            if(dinosaur.getBound().intersects(h.getBound())){
                return true;
            }
        }
        return false;
    }

    public void reset(){
        Hurdles.clear();
        Hurdles.add(createCactus());
    }
}
