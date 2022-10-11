package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// class for the buttons
public class Buttons {
    public Texture buttonTexure;
    float buttonXPos;
    float buttonYPos;
    float buttonWidth;
    float buttonHeight;

    public Rectangle buttonBox;

    // creates a button at
    // x and y location
    // with tex texture
    public Buttons(float x, float y, Texture tex)
    {
        buttonTexure = tex;
        buttonXPos = x;
        buttonYPos = y;
        buttonHeight = tex.getHeight();
        buttonWidth = tex.getWidth();
        buttonBox = new Rectangle(x, y, buttonWidth, buttonHeight);
    }

    // draws the bullet on the screen
    public void draw(SpriteBatch batch)
    {
        batch.draw(buttonTexure, buttonXPos, buttonYPos);
    }

    // checks if the button has been clicked by the user
    public boolean isClicked(float x, float y)
    {
        return buttonBox.contains(x,y);
    }
}
