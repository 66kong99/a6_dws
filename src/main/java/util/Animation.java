package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private transient final List<BufferedImage> list;
    private transient final long deltaTime;
    private transient int currentFrame;
    private transient long previousTime;

    public Animation(int deltaTime){
        this.deltaTime = deltaTime;
        list = new ArrayList<BufferedImage>();
        previousTime = 0;
        currentFrame = 0;
    }

    public void updateFrame(){
        if(System.currentTimeMillis() - previousTime >= deltaTime){
            currentFrame++;
            if (currentFrame >= list.size()){
                currentFrame = 0;
            }
            previousTime = System.currentTimeMillis();
        }
    }

    public void addFrame(BufferedImage image){
        list.add(image);
    }

    public BufferedImage getFrame(){
        return list.get(currentFrame);
    }
}