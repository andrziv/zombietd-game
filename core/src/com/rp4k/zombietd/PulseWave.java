package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

// the bullet effect for the PulseTower defense tower
public class PulseWave extends Bullet{
    int i = 1;
    int j = 0;

    // creates a the pulse at
    // x and y coordinates
    // with tex texture
    public PulseWave(float x, float y, Texture tex)
    {
        super(x, y, tex, true, ZombieTD.zombieList.get(0));
        initTextures();
        bulletLifetime = 50;
        damage = 500;
    }

    // initializes the textures for the bullet
    public void initTextures()
    {
        bulletTexture = new Sprite(Resources.pulseWaveTexture);
    }

    // draws the bullet onto the screen for the user to see
    @Override
    public void draw(SpriteBatch batch)
    {
        bulletTexture.setScale(i, i);
        bulletTexture.setCenter(bulletXPosition, bulletYPosition);
        bulletTexture.draw(batch);
        if (j%3 == 0)
        {
            i++;
        }
        j++;
    }

    // returns the bullet area
    public Circle getHitbox()
    {
        bulletWidth = bulletTexture.getWidth()* bulletTexture.getScaleY();
        return new Circle(bulletXPosition, bulletYPosition, bulletWidth/2);
    }

    @Override
    public void update()
    {
        bulletLifetime--;
    }
}
