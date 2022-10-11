package com.rp4k.zombietd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

// class for the enemy
public abstract class Enemy {
    public Texture enemyTexture;
    public Vector2 OFFSET_TEXTURE;
    float enemyXPosition;
    float enemyYPosition;
    float enemyWidth;
    float enemyHeight;
    float enemySpeed;
    int enemyHealth;

    boolean Active = true;

    public int numAnimRow;
    public int numAnimColumns;

    public Animation walkAnim;
    public TextureRegion[] walkFrames;
    public TextureRegion currentFrame;
    public float frameTime;

    public String debuffState = "None";
    public int debuffTime;
    public int timer = 0;
    public boolean isDebuffed = false;
    public int invincibilityTime = 30;

    ArrayList<Node> nodeList = new ArrayList<>();

    // creates an enemy at
    // x and y location
    // with tex texture
    public Enemy(float x, float y, Texture tex)
    {
        enemyXPosition = x;
        enemyYPosition = y;
        enemyWidth = tex.getWidth() / 4;
        enemyHeight = tex.getHeight();
        OFFSET_TEXTURE = new Vector2(enemyWidth/2, enemyHeight/2);
        initTextures();
        initAnimations(); // for animated textures

        nodeList.add(new Node(new Vector2(0, 0), null));
        nodeList.add(new Node(new Vector2(0, 0), null));
    }

    // gets the texture from a resource
    public void initTextures()
    {
        enemyTexture = Resources.zombieTexture;
    }

    // initializes the animations for the enemy
    public void initAnimations()
    {
        numAnimRow = 1;
        numAnimColumns = 4;

        TextureRegion[][] tempSheet = TextureRegion.split(enemyTexture, enemyTexture.getWidth() / numAnimColumns, enemyTexture.getHeight() / numAnimRow);
        walkFrames = new TextureRegion[numAnimRow * numAnimColumns];
        int frameIndex = 0;
        for (int i = 0; i < numAnimRow; i++)
        {
            for (int j = 0; j < numAnimColumns; j++)
            {
                walkFrames[frameIndex++] = tempSheet[i][j];
            }
        }

        walkAnim = new Animation(0.2f, walkFrames);
    }

    // draws the enemy onto the screen
    public void draw(SpriteBatch batch)
    {
        frameTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) walkAnim.getKeyFrame(frameTime, true);
        batch.draw(currentFrame, enemyXPosition - enemyWidth/2, enemyYPosition - enemyHeight/2);
    }

    // gets the area of the enemy's hitbox/size
    public Rectangle getHitbox()
    {
        return new Rectangle(enemyXPosition - enemyWidth/2, enemyYPosition - enemyHeight/2, enemyWidth, enemyHeight);
    }

    ArrayList<Node> followPath;

    // pathfinding algorithm to get around obstacles
    public void followPath()
    {
        rootFinder findPath = new rootFinder();

        followPath = findPath.findRoot(new Vector2(rootFinder.roundToFifty((int) enemyXPosition), rootFinder.roundToFifty((int) enemyYPosition)), new Vector2(rootFinder.roundToFifty(-50), rootFinder.roundToFifty(250)));
        float angle = 0;

        if (followPath.size() >= 3 && nodeList.size() >= 2)
        {
            if (followPath.get(1).position.equals(nodeList.get(0).position))
            {
                angle = (float) Math.toDegrees(Math.atan2(nodeList.get(1).position.y - rootFinder.roundToFifty((int) enemyYPosition), nodeList.get(1).position.x - rootFinder.roundToFifty((int) enemyXPosition)));
            }
            else if (followPath.get(2).position.equals(nodeList.get(1).position))
            {
                angle = (float) Math.toDegrees(Math.atan2(nodeList.get(1).position.y - rootFinder.roundToFifty((int) enemyYPosition), nodeList.get(1).position.x - rootFinder.roundToFifty((int) enemyXPosition)));
            }
            else
            {
                angle = (float) Math.toDegrees(Math.atan2(followPath.get(1).position.y - rootFinder.roundToFifty((int) enemyYPosition), followPath.get(1).position.x - rootFinder.roundToFifty((int) enemyXPosition)));
                nodeList = new ArrayList<>(followPath);
            }
        }
        else
        {
            if (followPath.size() >= 2) {
                angle = (float) Math.toDegrees(Math.atan2(followPath.get(1).position.y - rootFinder.roundToFifty((int) enemyYPosition), followPath.get(1).position.x - rootFinder.roundToFifty((int) enemyXPosition)));
                nodeList = new ArrayList<>(followPath);
            }
            else
            {
                enemyXPosition -= enemySpeed;
                return;
            }
        }

        enemyXPosition -= (-Math.cos(angle * Math.PI/180) * enemySpeed) ;
        enemyYPosition -= (-Math.sin(angle * Math.PI/180) * enemySpeed);
    }

    // updates various aspects about the enemy:
    // follows the pathfinding nodes
    // various timers like invincibility
    public void update()
    {
        followPath();

        invincibilityTime++;

        if ((isDebuffed) && timer != debuffTime)
        {
            timer++;
        }

        if ((isDebuffed) && timer == debuffTime)
        {
            undoDebuff();
            timer = 0;
        }
    }

    // various effects that the enemy can be under
    public void debuffState()
    {
        switch (debuffState)
        {
            case ("Shocked") :
                enemySpeed = (float) 0.1 * this.enemySpeed;
                debuffTime = 200;
                isDebuffed = true;
                break;
            case ("Burning") :
                debuffTime = 200;
                isDebuffed = true;
                break;
            case ("None") :
                enemySpeed = 1;
                isDebuffed = false;
                break;
            default :
                break;
        }
    }

    // removes any effects that the enemy was under
    public void undoDebuff()
    {
        debuffState = "None";
        enemySpeed = 1;
        isDebuffed = false;
    }
}
