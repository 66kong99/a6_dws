package clock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import util.Resource;

public class Background {
    public static final int LAND_POSY = 553;

    private final List<ImageBackground> listBackground;
    private final BufferedImage land1;
    private final BufferedImage land2;
    private final BufferedImage land3;

    private Random rand;

    private final Dinosaur dinosaur;

    public Background(int width, Dinosaur dinosaur){
        this.dinosaur = dinosaur;
        land1 = Resource.getResourceImage("resources/land1.png");
        land2 = Resource.getResourceImage("resources/land2.png");
        land3 = Resource.getResourceImage("resources/land3.png");
        int numberOfImageBackground = width / land1.getWidth() + 2;
        listBackground = new ArrayList<ImageBackground>();
        for(int i = 0; i < numberOfImageBackground; i++){
            ImageBackground imageBackground = new ImageBackground();
            imageBackground.posX = (float)i * land1.getWidth();
            setImageBackground(imageBackground);
            listBackground.add(imageBackground);
        }
        rand = new Random();
    }

    public void update(){
        Iterator<ImageBackground> itr = listBackground.iterator();
        ImageBackground firstElement = itr.next();
        firstElement.posX -= dinosaur.getSpeedX();
        float previousPosX = firstElement.posX;
        while(itr.hasNext()){
            ImageBackground element = itr.next();
            element.posX = previousPosX + land1.getWidth();
            previousPosX = element.posX;
        }
        if (firstElement.posX <= -land1.getWidth()){
            listBackground.remove(firstElement);
            firstElement.posX = previousPosX + land1.getWidth();
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
        rand = new Random();
        int type = rand.nextInt(10);
        if(type == 1){
            return 1;
        }else if (type == 9){
            return 3;
        } else{
            return 2;
        }
    }



    private class ImageBackground{
        float posX;
        BufferedImage image;

        public ImageBackground(){
            posX = 0;
            image = null;
        }
    }
}