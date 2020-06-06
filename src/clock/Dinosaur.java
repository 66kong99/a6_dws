package clock;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import util.Animation;
import util.Resource;

public class Dinosaur {
    public static final int LAND_POSY = 80;
    public static final float GRAVITY = 0.4f;

    private static final int NORMAL_RUN = 0;
    private static final int JUMPING = 1;
    private static final int DEATH = 2;

    private float posX;
    private float posY;
    private float speedX;
    private float speedY;
    private Rectangle rectBound;

    public int score = 0;

    private int state = NORMAL_RUN;

    private Animation normalRunAnim;
    private BufferedImage jumping;
    private BufferedImage deathImage;

    private Buzzer scoreUpSound;

    public Dinosaur() {
        posX = 50;
        posY = LAND_POSY;
        rectBound = new Rectangle();
        normalRunAnim = new Animation(90);
        normalRunAnim.addFrame(Resource.getResourceImage(("data/main-character1.png")));
        normalRunAnim.addFrame(Resource.getResourceImage(("data/main-character2.png")));
        jumping = Resource.getResourceImage(("data/main-character3.png"));
        deathImage = Resource.getResourceImage(("data/main-character4.png"));

        scoreUpSound = new Buzzer();
    }
    public float getSpeedX(){
        return speedX;
    }

    public void setSpeedX(int speedX){
        this.speedX = speedX;
    }

    public void draw(Graphics g){
        switch (state){
            case NORMAL_RUN:
                g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
                break;
            case JUMPING:
                g.drawImage(jumping, (int) posX, (int) posY, null);
                break;
            case DEATH:
                g.drawImage(deathImage, (int) posX, (int) posY, null);
                break;
        }
        Rectangle bound = getBound();
        g.setColor(Color.red);
        g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    public void update(){
        normalRunAnim.updateFrame();
        if (posY >= LAND_POSY){
            posY = LAND_POSY;
        }else {
            speedY += GRAVITY;
            posY +=speedY;
        }
    }

    public void jump(){
        if (posY >= LAND_POSY){
            speedY = -7.5f;
            posY +=speedY;
            state = JUMPING;
        }
    }

    public Rectangle getBound(){
        rectBound = new Rectangle();
        rectBound.x = (int) posX + 5;
        rectBound.y = (int) posY;
        rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
        rectBound.height = normalRunAnim.getFrame().getHeight();
        return rectBound;
    }

    public void dead(boolean isDeath){
        if(isDeath){
            state = DEATH;
        }else{
            state = NORMAL_RUN;
        }
    }

    public void reset(){
        posY = LAND_POSY;
    }

    public void upScore(){
        score += 20;
        if(score % 100 == 0){
            scoreUpSound.beep(0);
        }
    }

}