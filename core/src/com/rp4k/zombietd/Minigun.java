package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.g2d.Sprite;

// specialized defence tower that shoots faster than the normal cannon
public class Minigun extends Towers{
    public Minigun(float x, float y)
    {
        super(x, y, new Sprite(Resources.minigunTexture));

        costToDeploy = 200;
        delayTimer = 3;
    }

    @Override
    public void initTextures()
    {
        towerSprite = new Sprite(Resources.minigunTexture);
    }

    public void fireBullet()
    {
        if (!ZombieTD.zombieList.isEmpty())
        {
            ZombieTD.bulletList.add(new Bullet(towerXPos + towerWidth/2, towerYPos + towerHeight/2, Resources.bulletTexture, false, retargetEnemy()));
            towerShoot.play((float) 15);
        }
    }
}
