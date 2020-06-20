package clock;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;

import util.Resource;

public class Hurdle {
    private transient final BufferedImage cactus1;
    private transient final BufferedImage cactus2;

    private transient final SecureRandom rand;
    private transient final List<Cactus> Hurdles;
    private transient final Dinosaur dinosaur;

    public Hurdle(Dinosaur dinosaur){
        rand = new SecureRandom();
        cactus1 = Resource.getResourceImage("resources/cactus1.png");
        cactus2 = Resource.getResourceImage("resources/cactus2.png");
        Hurdles = new ArrayList<Cactus>();
        this.dinosaur = dinosaur;
        Hurdles.add(createCactus());
    }

    public void update(double speed){
        for(Cactus h : Hurdles){
            h.update();
        }
        Cactus hurdle = Hurdles.get(0);
        if(hurdle.getCactusPosX() <= (double)255+speed && hurdle.getCactusPosX() >250)
            Hurdles.add(createCactus());
        if(hurdle.isOutOfScreen()){
            dinosaur.upScore();
            Hurdles.remove(0);
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
            return new Cactus(dinosaur, 600 + rand.nextInt(600), cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1);
        } else {
            return new Cactus(dinosaur, 600 + rand.nextInt(600), cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
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
