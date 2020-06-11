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
            URL imageURL = Resource.class.getClassLoader().getResource(path);
            System.out.println(imageURL);
            img = ImageIO.read(imageURL);
        } catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }

    public static Font getFont(String path, int size){
        Font font = null;
        try{
          font = Font.createFont(Font.TRUETYPE_FONT, Resource.class.getClassLoader().getResourceAsStream(path)).deriveFont(Font.PLAIN, size);

        }catch(Exception e){
            e.printStackTrace();
        }
        return font;
    }
}