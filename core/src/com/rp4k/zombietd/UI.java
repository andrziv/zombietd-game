package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// class handling the ui of the game
public class UI {
    public static int money = 2000; // starting money for the player
    public static BitmapFont font = new BitmapFont();
    public static int cost = 40; // cost of the default tower

    // draws all of the ui elements on the screen for the user to see
    public static void draw(SpriteBatch batch)
    {
        if(ZombieTD.gameState.equals("Playing")) { // what is drawn when the game is in play mode
            // sets fonts colour, size, and draws the player's current money
            font.setColor(Color.BLACK);
            font.getData().setScale(2, 2);
            font.draw(batch, "" + money, 45, 530);

            // draws the current zombie stats: zombie wave, the amount of zombies destroyed / the amount of zombies that will spawn
            font.setColor(Color.DARK_GRAY);
            font.getData().setScale(2, 2);
            font.draw(batch, "" + (ZombieTD.zombieWave + 1), 925, 585);
            font.draw(batch, ZombieTD.zombiesKilledThisWave + "/" + (ZombieTD.zombieList.size() + ZombieTD.zombiesKilledThisWave), 925, 538);

            // draws the tower icons
            font.draw(batch, "" + 40, 150, 527); // normal cannon
            font.draw(batch, "" + 80, 255, 527); // fire cannon
            font.draw(batch, "" + 1000, 346, 527); // pulse tower
            font.draw(batch, "" + 200, 459, 527); // minigun
            font.draw(batch, "" + 150, 565, 527); // lightning tower

            // draws the player's current lives
            font.setColor(Color.PINK);
            font.draw(batch, "" + ZombieTD.playerLives, 814, 560);
        }
        else if(ZombieTD.gameState.equals("Game Over")) { // what is drawn when the game is over
            font.setColor(Color.RED);
            font.getData().setScale(4, 4);
            font.draw(batch, "GAME OVER - YOU LOSE", 200, 260);
        }
    }
}
