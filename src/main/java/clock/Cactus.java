package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cactus {
    public static final int Y_LAND = 675;

    private int CactusposX;
    private int width;
    private int height;

    private BufferedImage CactusImage;
    private Dinosaur dinosaur;

    private Rectangle rectBound;

    public Cactus(Dinosaur dinosaur, int posX, int width, int height, BufferedImage image) {
        this.CactusposX = posX;
        this.width = width;
        this.height = height;
        this.CactusImage = image;
        this.dinosaur = dinosaur;
        rectBound = new Rectangle();
    }

    public void update(){
        CactusposX -= dinosaur.getSpeedX();
    }

    public void draw(Graphics g){
        g.drawImage(CactusImage, CactusposX, Y_LAND - CactusImage.getHeight(), null);
        g.setColor(Color.red);
    }

    public Rectangle getBound(){
        rectBound = new Rectangle();
        rectBound.x = (int)CactusposX + (CactusImage.getWidth() - width)/2;
        rectBound.y = Y_LAND - CactusImage.getHeight() + (CactusImage.getHeight() - height) / 2;
        rectBound.width = width;
        rectBound.height = height;
        return rectBound;
    }

    public boolean isOutOfScreen(){
        if(CactusposX < -CactusImage.getWidth()){
            return true;
        }
        return false;
    }
}