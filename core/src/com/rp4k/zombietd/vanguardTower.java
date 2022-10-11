package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.g2d.Sprite;

// the class for the tower that doesn't shoot, but creates money for the player
public class vanguardTower extends Towers{
    public vanguardTower(float x, float y)
    {
        super(x, y, new Sprite(Resources.lightningTowerTexture));

        costToDeploy = 30;
        delayTimer = 500;
    }

    @Override
    public void initTextures()
    {
        towerSprite = new Sprite(Resources.moneyMakingTowerTexture);
    }


    @Override
    public void fireBullet()
    {
    }

    @Override
    public void update()
    {
        if(counter++ >= delayTimer)
        {
            UI.money+=5;
            counter = 0;
        }
    }
}
