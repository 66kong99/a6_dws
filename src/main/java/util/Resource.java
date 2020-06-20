package util;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Resource {
    private static final Logger logger = Logger.getLogger(Resource.class.getName());

    private Resource(){
    }


    public static BufferedImage getResourceImage(String path){
        BufferedImage img = null;
        try{
            URL imageURL = Thread.currentThread().getContextClassLoader().getResource(path);
            img = ImageIO.read(imageURL);
        } catch(IOException e){
            logger.log(Level.WARNING, "Resource Thread Interrupted", e);
        }
        return img;
    }

    public static Font getFont(int size){
        Font font = null;
        try{
          font = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/scoreboard.ttf")).deriveFont(Font.PLAIN, size);

        }catch(IOException | FontFormatException e){
            logger.log(Level.WARNING, "Font Thread Interrupted", e);
        }
        return font;
    }
}