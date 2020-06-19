package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cactus {
    public static final int Y_LAND = 575;

    public int CactusPosX;
    private final int width;
    private final int height;

    private final BufferedImage CactusImage;
    private final Dinosaur dinosaur;

    private Rectangle rectBound;

    public Cactus(Dinosaur dinosaur, int posX, int width, int height, BufferedImage image) {
        this.CactusPosX = posX;
        this.width = width;
        this.height = height;
        this.CactusImage = image;
        this.dinosaur = dinosaur;
        rectBound = new Rectangle();
    }

    public void update(){
        CactusPosX -= dinosaur.getSpeedX();
    }

    public void draw(Graphics g){
        g.drawImage(CactusImage, CactusPosX, Y_LAND - CactusImage.getHeight(), null);
        g.setColor(Color.red);
    }

    public Rectangle getBound(){
        rectBound = new Rectangle();
        rectBound.x = CactusPosX + (CactusImage.getWidth() - width)/2;
        rectBound.y = Y_LAND - CactusImage.getHeight() + (CactusImage.getHeight() - height) / 2;
        rectBound.width = width;
        rectBound.height = height;
        return rectBound;
    }

    public boolean isOutOfScreen(){
        return CactusPosX < -CactusImage.getWidth();
    }
}