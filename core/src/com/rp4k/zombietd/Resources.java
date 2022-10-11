package com.rp4k.zombietd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

// all of the visual and sound resources used in the project
public class Resources
{
    public static Texture backgroundTexture = new Texture(Gdx.files.internal("Grassybackground.png"));
    public static Texture toolbarTexture = new Texture(Gdx.files.internal("Toolbar.png"));
    public static Texture coinCurrentMoneyTexture = new Texture(Gdx.files.internal("Coin.png"));
    public static Texture cannonTexture = new Texture(Gdx.files.internal("Cannon.png"));
    public static Texture fireCannonTexture = new Texture(Gdx.files.internal("Firecannon.png"));
    public static Texture pulseTowerTexture = new Texture(Gdx.files.internal("pulseTowerV2.png"));
    public static Texture minigunTexture = new Texture(Gdx.files.internal("Minigun.png"));
    public static Texture lightningTowerTexture = new Texture(Gdx.files.internal("TeslaTower.png"));
    public static Texture moneyMakingTowerTexture = new Texture(Gdx.files.internal("vanguardTower.png"));
    public static Texture zombieTexture = new Texture(Gdx.files.internal("Zombies.png"));
    public static Texture fastZombieTexture = new Texture(Gdx.files.internal("Fastzombies.png"));
    public static Texture bulletTexture = new Texture(Gdx.files.internal("Bullet.png"));
    public static Texture fireBulletTexture = new Texture(Gdx.files.internal("FireBullet.png"));
    public static Texture lightningShotTexture = new Texture(Gdx.files.internal("lightningBall.png"));
    public static Texture pulseWaveTexture = new Texture(Gdx.files.internal("pulseWave.png"));
    public static Texture explosionTexture = new Texture(Gdx.files.internal("Explosion.png"));

    public static Sound cannonShoot = Gdx.audio.newSound(Gdx.files.internal("Bullet.mp3"));

    public static Texture weaponBoxSelected = new Texture(Gdx.files.internal("WeaponBoxSelected.png"));
    public static Texture iconLives = new Texture(Gdx.files.internal("LivesHeart.png"));
    public static Texture iconEnemiesLeft = new Texture(Gdx.files.internal("TargetsLeft.png"));
    public static Texture iconWaveNum = new Texture(Gdx.files.internal("WaveFlag.png"));
    public static Texture iconCannon = new Texture(Gdx.files.internal("CannonIcon.png"));
    public static Texture iconFireCannon = new Texture(Gdx.files.internal("FireCannonIcon.png"));
    public static Texture iconMinigun = new Texture(Gdx.files.internal("MingunIcon.png"));
    public static Texture iconPulseTower = new Texture(Gdx.files.internal("PulseTowerIcon.png"));

    public static Texture normalTTiles = new Texture(Gdx.files.internal("normalTPlace.png"));
    public static Texture largeTTiles = new Texture(Gdx.files.internal("pulseTPlace.png"));
    public static Texture allseeingEye = new Texture(Gdx.files.internal("placementTileEye.png"));
}