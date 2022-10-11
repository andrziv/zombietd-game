package com.rp4k.zombietd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

// bullet class for tower projectiles
public class Bullet {
    Sprite bulletTexture;
    float bulletXPosition;
    float bulletYPosition;
    float bulletHeight;
    float bulletWidth;
    float bulletSpeed;
    float bulletAngle;

    int bulletLifetime;
    int damage;

    boolean bulletActive = true;

    public int numAnimRow;
    public int numAnimColumns;

    // bullet animations if there are multiple frames
    public Animation bulletAnim;
    public TextureRegion[] flyFrames;
    public TextureRegion currentFrame;
    public float frameTime;

    boolean animated;

    // bullet constructor
    // x and y are the bullet's starting position
    // tex is the bullet texture
    // anim allows the bullet texture to be animated
    // enemy tells the bullet what angle it needs to face
    public Bullet(float x, float y, Texture tex, boolean anim, Enemy enemy)
    {
        bulletXPosition = x;
        bulletYPosition = y;

        bulletTexture = new Sprite(tex);

        bulletWidth = bulletTexture.getWidth();
        bulletHeight = bulletTexture.getHeight();

        animated = anim;

        if (anim == true) {
            animated = anim;
            initAnimations();
        }

        getBulletAngle(enemy);

        damage = 2;
        bulletSpeed = 7;
        bulletLifetime = 250;
    }

    // draws the bullet texture
    public void draw(SpriteBatch batch)
    {
        if (animated == true) {
            frameTime += Gdx.graphics.getDeltaTime();
            currentFrame = (TextureRegion) bulletAnim.getKeyFrame(frameTime, true);
            batch.draw(currentFrame, bulletXPosition - bulletWidth/2, bulletYPosition - bulletHeight/2);
        }
        else {
            batch.draw(bulletTexture, bulletXPosition - bulletWidth / 2, bulletYPosition - bulletHeight / 2);
        }
    }

    // only used if the bullet was instantiated with animated tetxures
    // makes the bullet's texture cycle through the frames to appear animated
    public void initAnimations()
    {
        numAnimRow = 1;
        numAnimColumns = 6;

        TextureRegion[][] tempSheet = TextureRegion.split(bulletTexture.getTexture(), bulletTexture.getTexture().getWidth() / numAnimColumns, bulletTexture.getTexture().getHeight() / numAnimRow);
        flyFrames = new TextureRegion[numAnimRow * numAnimColumns];
        int frameIndex = 0;
        for (int i = 0; i < numAnimRow; i++)
        {
            for (int j = 0; j < numAnimColumns; j++)
            {
                flyFrames[frameIndex++] = tempSheet[i][j];
            }
        }

        bulletAnim = new Animation(0.2f, flyFrames);
    }

    // changes the bullet angle depending on the enemy's location is compared to where the bullet currently is
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

    // updates the bullet's states such as:
    // its position
    // how long it has left to be on the screen
    public void update()
    {
        bulletXPosition += Math.cos(bulletAngle) * bulletSpeed;
        bulletYPosition += Math.sin(bulletAngle) * bulletSpeed;

        bulletLifetime--;
    }

    // gets the bullets area
    public Circle getHitbox()
    {
        return new Circle(bulletXPosition, bulletYPosition, bulletWidth/2);
    }
}
