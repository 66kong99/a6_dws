package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Resource {
    private static final Logger LOGGER = Logger.getLogger(Resource.class.getName());

    public Resource(){
        LOGGER.log(Level.WARNING, "WRONG ACCESS");
    }


    public static BufferedImage getResourceImage(String path){
        BufferedImage img = null;
        try{
            URL imageURL = Thread.currentThread().getContextClassLoader().getResource(path);
            img = ImageIO.read(imageURL);
        } catch(IOException e){
            LOGGER.log(Level.WARNING, "Resource Thread Interrupted", e);
        }
        return img;
    }

    public static Font getFont(int size){
        Font font = null;
        try{
          font = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/scoreboard.ttf")).deriveFont(Font.PLAIN, size);

        }catch(IOException | FontFormatException e){
            LOGGER.log(Level.WARNING, "Font Thread Interrupted", e);
        }
        return font;
    }
}