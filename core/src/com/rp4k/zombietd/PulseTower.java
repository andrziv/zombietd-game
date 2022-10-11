package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.g2d.Sprite;

// class for a tower that is slow firing but does a lot of damage
public class PulseTower extends Towers{
    public PulseTower(float x, float y)
    {
        super(x, y, new Sprite(Resources.pulseTowerTexture));
        costToDeploy = 1000;
        delayTimer = 5000;
    }

    @Override
    public void initTextures()
    {
        towerSprite = new Sprite(Resources.pulseTowerTexture);
    }

    public void fireBullet()
    {
        if (!ZombieTD.zombieList.isEmpty())
        {
            ZombieTD.bulletList.add(new PulseWave(towerXPos + towerWidth/2, towerYPos + towerHeight/2, Resources.pulseWaveTexture));
            towerShoot.play((float) 10);
        }
    }

    public void update() {
        if(counter++ >= delayTimer)
        {
            fireBullet();
            counter = 0;
        }
    }
}
