package clock;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

class GameTest {

    @Test
    void start() {
        Game game = new Game();
        try{
            Field field = game.getClass().getDeclaredField("gameState");
            field.setAccessible(true);
            int value = (int)field.get(game);
            assertEquals(value, 0); // gameState = START_GAME_STATE checking
            game.buttonPressed(); // gmae start
            value = (int)field.get(game);
            assertEquals(value, 1); // gameState = GAME_PLAYING_STATE
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }
    }
}