package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.g2d.Sprite;

// tower that stops enenemies from moving briefly
public class LightningTower extends Towers{
    public LightningTower(float x, float y)
    {
        super(x, y, new Sprite(Resources.lightningTowerTexture));

        costToDeploy = 150;
        delayTimer = 400;
    }

    @Override
    public void initTextures()
    {
        towerSprite = new Sprite(Resources.lightningTowerTexture);
    }

    @Override
    public Enemy retargetEnemy()
    {
        Enemy closestEnemy = ZombieTD.zombieList.get(0);

        for (int i = 0; i < ZombieTD.zombieList.size(); i++)
        {
            if (ZombieTD.zombieList.get(i).debuffState.equals("None"))
            {
                closestEnemy = ZombieTD.zombieList.get(i);
            }
        }

        return closestEnemy;
    }

    @Override
    public void fireBullet()
    {
        if (!ZombieTD.zombieList.isEmpty())
        {
            ZombieTD.bulletList.add(new shockBullet(towerXPos + towerWidth/2, towerYPos + towerHeight/2, Resources.lightningShotTexture, retargetEnemy()));
            towerShoot.play((float) 0);
        }
    }

    @Override
    public void update()
    {
        towerSprite.setRotation(towerAngle);

        if(counter++ >= delayTimer)
        {
            fireBullet();
            counter = 0;
        }
    }
}
