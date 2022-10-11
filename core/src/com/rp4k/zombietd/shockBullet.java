package com.rp4k.zombietd;

import com.badlogic.gdx.graphics.Texture;

// a projectile fired by the lightning tower
public class shockBullet extends Bullet {
    public shockBullet(float x, float y, Texture tex, Enemy enemy)
    {
        super(x, y, tex, false, enemy);
        damage = 0;

        getBulletAngle(enemy);
    }

    public void getBulletAngle(Enemy enemy)
    {
        if (!ZombieTD.zombieList.isEmpty())
        {
            float bulletx = bulletXPosition;
            float bullety = bulletYPosition;
            float zombiex = enemy.enemyXPosition;
            float zombiey = enemy.enemyYPosition;
            float angle = (float) Math.atan((bullety - zombiey) / (bulletx - zombiex));

            if (bulletx >= zombiex)
            {
                angle += Math.PI;
            }
            bulletAngle = angle;
        }
    }
}
