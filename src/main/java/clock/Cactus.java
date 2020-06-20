package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cactus {
    public static final int Y_LAND = 575;

    private transient int cactusPosX;
    private transient final int width;
    private transient final int height;

    private transient final BufferedImage CactusImage;
    private transient final Dinosaur dinosaur;

    private transient Rectangle rectBound;

    public Cactus(Dinosaur dinosaur, int posX, int width, int height, BufferedImage image) {
        this.cactusPosX = posX;
        this.width = width;
        this.height = height;
        this.CactusImage = image;
        this.dinosaur = dinosaur;
        rectBound = new Rectangle();
    }

    public void update(){
        cactusPosX -= dinosaur.getSpeedX();
    }

    public void draw(Graphics g){
        g.drawImage(CactusImage, cactusPosX, Y_LAND - CactusImage.getHeight(), null);
        g.setColor(Color.red);
    }

    public Rectangle getBound(){
        rectBound = new Rectangle();
        rectBound.x = cactusPosX + (CactusImage.getWidth() - width)/2;
        rectBound.y = Y_LAND - CactusImage.getHeight() + (CactusImage.getHeight() - height) / 2;
        rectBound.width = width;
        rectBound.height = height;
        return rectBound;
    }

    public int getCactusPosX() {
        return cactusPosX;
    }

    public void setCactusPosX(int cactusPosX) {
        this.cactusPosX = cactusPosX;
    }

    public boolean isOutOfScreen(){
        return cactusPosX < -CactusImage.getWidth();
    }
}