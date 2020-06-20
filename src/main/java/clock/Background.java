package clock;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.security.SecureRandom;

import util.Resource;

public class Background {
    public static final int LAND_POSY = 553;

    private transient final List<ImageBackground> listBackground;
    private transient final BufferedImage land1;
    private transient final BufferedImage land2;
    private transient final BufferedImage land3;

    private transient SecureRandom rand;

    private transient final Dinosaur dinosaur;

    public Background(int width, Dinosaur dinosaur){
        this.dinosaur = dinosaur;
        land1 = Resource.getResourceImage("resources/land1.png");
        land2 = Resource.getResourceImage("resources/land2.png");
        land3 = Resource.getResourceImage("resources/land3.png");
        int numberOfImage = width / land1.getWidth() + 2;
        listBackground = new ArrayList<ImageBackground>();
        for(int i = 0; i < numberOfImage; i++){
            ImageBackground imageBackground = new ImageBackground();
            imageBackground.posX = (double)i * land1.getWidth();
            setImageBackground(imageBackground);
            listBackground.add(imageBackground);
        }

        rand = new SecureRandom();
    }

    public void update(){
        Iterator<ImageBackground> itr = listBackground.iterator();
        ImageBackground firstElement = itr.next();
        firstElement.posX -= dinosaur.getSpeedX();
        double previousPosX = firstElement.posX;
        while(itr.hasNext()){
            ImageBackground element = itr.next();
            element.posX = (double)previousPosX + land1.getWidth();
            previousPosX = element.posX;
        }
        if (firstElement.posX <= -land1.getWidth()){
            listBackground.remove(firstElement);
            firstElement.posX = (double)previousPosX + land1.getWidth();
            setImageBackground(firstElement);
            listBackground.add(firstElement);
        }
    }

    private void setImageBackground(ImageBackground imageBackground){
        int typeBackground = getTypeOfBackground();
        if(typeBackground == 1){
            imageBackground.image = land1;
        } else if(typeBackground == 3){
            imageBackground.image = land3;
        } else{
            imageBackground.image = land2;
        }
    }

    public void draw(Graphics g){
        for(ImageBackground imageBackground : listBackground){
            g.drawImage(imageBackground.image, (int) imageBackground.posX + 100, LAND_POSY, null);
        }
    }

    private int getTypeOfBackground(){
        rand = new SecureRandom();
        int type = rand.nextInt(10);
        if(type == 1){
            return 1;
        }else if (type == 9){
            return 3;
        } else{
            return 2;
        }
    }



    private static class ImageBackground{
        private transient double posX;
        private transient BufferedImage image;

        public ImageBackground(){
            posX = 0;
        }
    }
}