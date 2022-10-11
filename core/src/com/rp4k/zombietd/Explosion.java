package com.rp4k.zombietd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// class for an explosion effect
public class Explosion {
    public Texture explosionTexture;

    public float explXPos;
    public float explYPos;
    public float explWidth;
    public float explHeight;

    public int life;
    public int numAnimRow;
    public int numAnimColumns;

    public boolean explActive;

    public Animation explosionAnim;
    public TextureRegion[] explFrames;
    public TextureRegion currentFrame;
    public float frameTime;

    // creates an explosion at
    // x and y position
    public Explosion (float x, float y)
    {
        explosionTexture = Resources.explosionTexture;
        explXPos = x;
        explYPos = y;
        explWidth = explosionTexture.getWidth()/4;
        explHeight = explosionTexture.getHeight();
        initAnimations();
        explActive = true;
        life = 30;
    }

    // initializes the animated texture for the explosion
    public void initAnimations()
    {
        numAnimRow = 1;
        numAnimColumns = 6;

        TextureRegion[][] tempSheet = TextureRegion.split(explosionTexture, explosionTexture.getWidth() / numAnimColumns, explosionTexture.getHeight() / numAnimRow);
        explFrames = new TextureRegion[numAnimRow * numAnimColumns];
        int frameIndex = 0;
        for (int i = 0; i < numAnimRow; i++)
        {
            for (int j = 0; j < numAnimColumns; j++)
            {
                explFrames[frameIndex++] = tempSheet[i][j];
            }
        }

        explosionAnim = new Animation(0.2f, explFrames);
    }

    // draws the explosion onto the screen
    public void draw(SpriteBatch batch)
    {
        frameTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) explosionAnim.getKeyFrame(frameTime, true);
        batch.draw(currentFrame, explXPos - explWidth/2, explYPos - explHeight/2);
    }

    // updates the explosion
    // checks to see if the explosion's runtime has finished
    // if it has, remove the explosion effect
    public void update()
    {
        life--;
        if (life < 0)
        {
            explActive = false;
            ZombieTD.explosionList.remove(this);
        }
    }
}
