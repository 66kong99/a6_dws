package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Resource {
    public static BufferedImage getResourceImage(String path){
        BufferedImage img = null;
        try{
            URL imageURL = Thread.currentThread().getContextClassLoader().getResource(path);
            img = ImageIO.read(imageURL);
        } catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }

    public static Font getFont(int size){
        Font font = null;
        try{
          font = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/scoreboard.ttf")).deriveFont(Font.PLAIN, size);

        }catch(Exception e){
            e.printStackTrace();
        }
        return font;
    }
}