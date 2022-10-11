package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.g2d.Sprite;

// specialized tower that shoots fire
public class FireCannon extends Towers{
    public FireCannon(float x, float y)
    {
        super(x, y, new Sprite(Resources.fireCannonTexture));

        costToDeploy = 80;
        delayTimer = 15;
    }

    @Override
    public void initTextures()
    {
        towerSprite = new Sprite(Resources.fireCannonTexture);
    }

    public void fireBullet()
    {
        if (!ZombieTD.zombieList.isEmpty())
        {
            ZombieTD.bulletList.add(new Bullet(towerXPos + towerWidth/2, towerYPos + towerHeight/2, Resources.fireBulletTexture, true, retargetEnemy()));
            towerShoot.play((float) 30);
        }
    }
}
