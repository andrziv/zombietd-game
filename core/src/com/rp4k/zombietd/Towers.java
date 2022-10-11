package com.rp4k.zombietd;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// parent tower class
public abstract class Towers {
    public Sprite towerSprite;
    public Sound towerShoot;
    float towerXPos;
    float towerYPos;
    float towerWidth;
    float towerHeight;
    float towerAngle;
    int counter = 0;
    int delayTimer;

    static int costToDeploy; // cost required for the user to spend before placing

    public Towers(float x, float y, Sprite sprite)
    {
        initTextures();
        towerWidth = towerSprite.getWidth();
        towerHeight = towerSprite.getHeight();
        towerXPos = lockToGrid(x - towerWidth/2);
        towerYPos = lockToGrid(y - towerHeight/2);


        towerSprite.setPosition(towerXPos, towerYPos);
        delayTimer = 80;

        towerShoot = Resources.cannonShoot;
    }

    public void initTextures()
    {
        towerSprite = new Sprite (Resources.cannonTexture);
    }

    public void draw(SpriteBatch spriteB)
    {
        towerSprite.draw(spriteB);
    }

    public void fireBullet()
    {
        if (!ZombieTD.zombieList.isEmpty())
        {
            ZombieTD.bulletList.add(new Bullet(towerXPos + towerWidth/2, towerYPos + towerHeight/2, Resources.bulletTexture, false, retargetEnemy()));
            towerShoot.play((float) 30);
        }
    }

    // finds a new enemy for the tower to target
    // finds the closest enemy to the tower
    public Enemy retargetEnemy()
    {
        Enemy closestEnemy = ZombieTD.zombieList.get(0);

        for (int i = 0; i < ZombieTD.zombieList.size(); i++)
        {
            if (ZombieTD.zombieList.get(i).enemyXPosition < closestEnemy.enemyXPosition)
            {
                closestEnemy = ZombieTD.zombieList.get(i);
            }
        }

        return closestEnemy;
    }

    // sets the tower angle to face the closest enemy
    public void getTowerAngle()
    {
        if (!ZombieTD.zombieList.isEmpty())
        {
            float bulletx = towerXPos;
            float bullety = towerYPos;
            float zombiex = retargetEnemy().enemyXPosition;
            float zombiey = retargetEnemy().enemyYPosition;
            float angle = (float) Math.atan((bullety - zombiey) / (bulletx - zombiex));

            if (bulletx >= zombiex)
            {
                angle += Math.PI;
            }
            towerAngle = angle;
            this.towerAngle = (float) Math.toDegrees(towerAngle);
        }
    }

    public Rectangle getHitbox()
    {
        return new Rectangle(towerXPos, towerYPos, towerSprite.getWidth(), towerSprite.getHeight());
    }

    // positions the tower to be perfectly on the map tiles, which are 50x50px
    public float lockToGrid(float position)
    {
        return ((int) (position + 25) / 50) * 50;
    }

    // updates the tower's state, such as:
    // whether or not it can fire a bullet
    // what angle the tower should be facing
    public void update()
    {
        getTowerAngle();
        towerSprite.setRotation(towerAngle);

        if(counter++ >= delayTimer)
        {
            fireBullet();
            counter = 0;
        }
    }
}
