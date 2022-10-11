package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.g2d.Sprite;

// class for the basic cannon
public class Cannon extends Towers{
    public Cannon(float x, float y)
    {
        super(x, y, new Sprite(Resources.cannonTexture));

        costToDeploy = 40;
        delayTimer = 80;
    }
}