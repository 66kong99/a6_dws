package clock;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import util.Animation;
import util.Resource;

public class Dinosaur {
    public static final int LAND_POSY = 530;
    public static final float GRAVITY = 0.4f;

    private static final int NORMAL_RUN = 0;
    private static final int JUMPING = 1;
    private static final int DEATH = 2;

    private transient final float posX;
    private transient float posY;
    private transient float speedX;
    private transient float speedY;
    private transient Rectangle rectBound;

    private transient int gameScore = 0;

    private transient int state = NORMAL_RUN;

    private transient final Animation normalRunAnim;
    private transient final BufferedImage jumpImage;
    private transient final BufferedImage deathImage;

    private transient final Buzzer scoreUpSound;

    public Dinosaur() {
        posX = 150;
        posY = LAND_POSY;
        rectBound = new Rectangle();
        normalRunAnim = new Animation(90);
        normalRunAnim.addFrame(Resource.getResourceImage("resources/main-character1.png"));
        normalRunAnim.addFrame(Resource.getResourceImage("resources/main-character2.png"));
        jumpImage = Resource.getResourceImage("resources/main-character3.png");
        deathImage = Resource.getResourceImage("resources/main-character4.png");

        scoreUpSound = new Buzzer();
    }
    public float getSpeedX(){
        return speedX;
    }

    public void setSpeedX(int speedX){
        this.speedX = Math.min(speedX+4, 10);
    }

    public void draw(Graphics g){
        switch (state){
            case NORMAL_RUN:
                g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
                break;
            case JUMPING:
                g.drawImage(jumpImage, (int) posX, (int) posY, null);
                break;
            case DEATH:
                g.drawImage(deathImage, (int) posX, (int) posY, null);
                break;
            default:
                break;
        }
    }

    public void update(){
        normalRunAnim.updateFrame();
        if (posY >= LAND_POSY){
            posY = LAND_POSY;
            state = NORMAL_RUN;
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
        gameScore = 0;
    }

    public void upScore(){
        gameScore += 20;
        if(gameScore % 100 == 0){
            scoreUpSound.beep();
        }
    }

    public int getGameScore(){
        return gameScore;
    }

}