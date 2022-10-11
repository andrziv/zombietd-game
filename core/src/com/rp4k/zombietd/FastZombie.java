package com.rp4k.zombietd;

// class for an enemy that is faster and harder to destroy than the basic enemy
public class FastZombie extends Enemy{
    public FastZombie(float x, float y)
    {
        super(x, y, Resources.fastZombieTexture);

        enemyHealth = 4;
        enemySpeed = 2;
    }

    @Override
    public void initTextures()
    {
        enemyTexture = Resources.fastZombieTexture;
    }
}
