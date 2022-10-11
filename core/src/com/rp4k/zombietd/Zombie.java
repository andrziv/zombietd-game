package com.rp4k.zombietd;

// class for the basic zombie
public class Zombie extends Enemy{
    public Zombie(float x, float y)
    {
        super(x, y, Resources.zombieTexture);

        enemyHealth = 6;
        enemySpeed = 1;
    }
}