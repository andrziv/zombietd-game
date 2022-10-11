package com.rp4k.zombietd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.List;
import java.util.Random;

import java.util.ArrayList;

// the class that handles all of the game's general functions
public class ZombieTD extends ApplicationAdapter {
	SpriteBatch batch;
	public static ArrayList<Towers> towerList = new ArrayList<>();
	public static ArrayList<Enemy> zombieList = new ArrayList<>();
	public static ArrayList<Bullet> bulletList = new ArrayList<>();
	public static ArrayList<Explosion> explosionList = new ArrayList<>();
	public static Buttons cannonButton;
	public static Buttons pulseTowerButton;
	public static Buttons fireCannonButton;
	public static Buttons minigunButton;
	public static Buttons lightningTowerButton;
	public static Buttons vanguardTowerButton;
	public static Buttons tilePlacingHelpButton;
	public enum towerType{Cannon, FireCannon, Minigun, PulseTower, lightningTower, vanguardTower};
	public static towerType selectedTower = towerType.Cannon;

	// default enemy counts
	static int zombieWave = 0;
	int zombieWaveDelayCounter = 0;
	public static int zombiesKilledThisWave = 0;

	final int TIME_TILL_NEXT_WAVE = 300;

	// default game play states
	public static int playerLives = 3;
	public static String gameState = "Playing";

	// ui selection box default setting
	public int selectedBoxX = 117;
	public int selectedBoxY = 500;

	// tile placement helper default positions and settings
	boolean istileButtonOn = false;
	public int tileHelpX1 = -1000;
	public int tileHelpY1 = -1000;
	public int tileHelpX2 = -1000;
	public int tileHelpY2 = -1000;

	public OrthographicCamera Camera; // mostly there for android

	public static rootFinder enemyPath;
	public static List<Node> nodeListing = new ArrayList<>();

	// creates all of the buttons and objects for the user to interact with
	// as well as all of the objects required for the game to run
	@Override
	public void create () {
		batch = new SpriteBatch();
		cannonButton = new Buttons(130, 530, Resources.iconCannon);
		fireCannonButton = new Buttons(237, 530, Resources.iconFireCannon);
		pulseTowerButton = new Buttons(344, 530, Resources.iconPulseTower);
		minigunButton = new Buttons(449, 530, Resources.iconMinigun);
		lightningTowerButton = new Buttons(549, 530, Resources.lightningTowerTexture);
		vanguardTowerButton = new Buttons(649, 530, Resources.moneyMakingTowerTexture);

		tilePlacingHelpButton = new Buttons(0, 460, Resources.allseeingEye);
		UI.cost = 40;

		Camera = new OrthographicCamera();
		Camera.setToOrtho(false, 1024, 600);

		enemyPath = new rootFinder();

		waveGenerator();
	}

	// draws all of the objects on the screen for the user to see
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update();

		Camera.update();
		batch.setProjectionMatrix(Camera.combined);

		batch.begin();
		batch.draw(Resources.backgroundTexture, 0, 0);
		for (int i = 0; i < bulletList.size(); i++)
		{
			bulletList.get(i).draw(batch);
		}

		batch.draw(Resources.toolbarTexture, 0, 500);
		batch.draw(Resources.coinCurrentMoneyTexture, 0, 500);
		batch.draw(Resources.iconLives, 780, 510);
		batch.draw(Resources.iconWaveNum, 880, 555);
		batch.draw(Resources.iconEnemiesLeft, 880, 505);
		batch.draw(Resources.weaponBoxSelected, selectedBoxX, selectedBoxY);

		batch.draw(Resources.normalTTiles, tileHelpX1, tileHelpY1);
		batch.draw(Resources.largeTTiles, tileHelpX2, tileHelpY2);

		for (int i = 0; i < towerList.size(); i++)
		{
			towerList.get(i).draw(batch);
		}
		for (int i = 0; i < zombieList.size(); i++)
		{
			zombieList.get(i).draw(batch);
		}

		for(int i = 0; i < explosionList.size(); i++)
		{
			explosionList.get(i).draw(batch);
		}

		cannonButton.draw(batch);
		minigunButton.draw(batch);
		pulseTowerButton.draw(batch);
		fireCannonButton.draw(batch);
		lightningTowerButton.draw(batch);
		vanguardTowerButton.draw(batch);
		tilePlacingHelpButton.draw(batch);
		UI.draw(batch);
		batch.end();
	}

	// ui icon that lets the user toggle it and see where their selected tower can be placed
	public void viewingPlacingTiles(int x, int y) {
		if (tilePlacingHelpButton.isClicked(x, y) && istileButtonOn == true) {
			tileHelpX1 = -1000;
			tileHelpY1 = -1000;
			tileHelpX2 = -1000;
			tileHelpY2 = -1000;
		}
		if (tilePlacingHelpButton.isClicked(x, y) && istileButtonOn == false) {
			switch (selectedTower) {
				case Cannon:
				case lightningTower:
				case Minigun:
				case FireCannon:
				case vanguardTower:
					tileHelpX1 = 0;
					tileHelpY1 = 0;
					tileHelpX2 = -1000;
					tileHelpY2 = -1000;
					break;
				case PulseTower:
					tileHelpX1 = -1000;
					tileHelpY1 = -1000;
					tileHelpX2 = 0;
					tileHelpY2 = 0;
					break;
				default:
					tileHelpX1 = -1000;
					tileHelpY1 = -1000;
					tileHelpX2 = -1000;
					tileHelpY2 = -1000;
					break;
			}
		}

		if (istileButtonOn == true) {
			istileButtonOn = false;
		} else
		{
			istileButtonOn = true;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	// updates all of the objects in the game
	public void update()
	{
		Input();

		for (int i = 0; i < zombieList.size(); i++)
		{
			zombieList.get(i).update();
		}
		for(int i = 0; i < towerList.size(); i++)
		{
			towerList.get(i).update();
		}
		for(int i = 0; i < bulletList.size(); i++)
		{
			bulletList.get(i).update();
		}
		for(int i = 0; i < explosionList.size(); i++)
		{
			explosionList.get(i).update();
		}

		if (zombieWaveDelayCounter == TIME_TILL_NEXT_WAVE)
		{
			waveGenerator();
			zombieWaveDelayCounter = 0;
		}
		else if (zombieList.isEmpty() && zombieWaveDelayCounter != TIME_TILL_NEXT_WAVE)
		{
			zombieWaveDelayCounter++;
		}

		checkCollisions();
		checkZombieEscaped();
		checkRemove();
		checkWaveOver();
		checkGameOver();
	}

	// creates enemies based on the "wave" number
	public void waveGenerator()
	{
		Random rand = new Random();
		int numOfZombiesModifier = rand.nextInt(5);
		int amountOfZombiesAdded = zombieWave * numOfZombiesModifier + 3;

		nodeListing = enemyPath.findRoot(new Vector2(rootFinder.roundToFifty((int) 1024), rootFinder.roundToFifty((int) 350)), new Vector2(rootFinder.roundToFifty(0), rootFinder.roundToFifty(450)));

		for(int i = 0; i < amountOfZombiesAdded; i++)
		{
			float zombiePotentialXPos = rootFinder.roundToFifty(rand.nextInt(250) + Resources.backgroundTexture.getWidth()); // rand.nextInt(250) + Resources.backgroundTexture.getWidth()
			float zombiePotentialYPos = rootFinder.roundToFifty(rand.nextInt(100) + Resources.backgroundTexture.getHeight() / 2 - 100); // rand.nextInt(100) + Resources.backgroundTexture.getHeight() / 2 - 100
			int fastZombiesRNG = rand.nextInt(4);

			if (zombieWave != 0 && (zombieWave%3 == 0 || zombieWave > 3) && fastZombiesRNG > 2)
			{
				zombieList.add(new FastZombie (zombiePotentialXPos, zombiePotentialYPos));
				continue;
			}
			else
			{
				zombieList.add(new Zombie (zombiePotentialXPos, zombiePotentialYPos));
			}
		}
	}

	// handles what happens when all of the current enemies were killed
	public void checkWaveOver()
	{
		if (zombieList.isEmpty() && zombieWaveDelayCounter == TIME_TILL_NEXT_WAVE)
		{
			zombiesKilledThisWave = 0;
			zombieWave++;
		}
	}

	// what happens when a zombie "escapes" or enters a set area
	public void checkZombieEscaped()
	{
		for (int i = 0; i < zombieList.size(); i++)
		{
			if (zombieList.get(i).enemyXPosition < -50) {

				zombieList.get(i).Active = false;
				playerLives--;
			}
		}
	}

	// if the player lets too many enemies through, sets the game to game over mode
	public void checkGameOver()
	{
		if (playerLives == 0)
		{
			gameState = "Game Over";
		}
	}

	// what happens if there are any collisions between bullets and zombies
	public void checkCollisions()
	{
		if (!bulletList.isEmpty() && !zombieList.isEmpty())
		{
			for (int i = 0; i < bulletList.size(); i++)
			{
				for (int j = 0; j < zombieList.size(); j++)
				{
					if (Intersector.overlaps(bulletList.get(i).getHitbox(), zombieList.get(j).getHitbox()))
					{
						zombieList.get(j).enemyHealth-=bulletList.get(i).damage;
						if (bulletList.get(i) instanceof PulseWave) {
							bulletList.get(i).bulletActive = true;
						}
						else if (bulletList.get(i) instanceof shockBullet) {
							if (zombieList.get(j).invincibilityTime >= 30 && zombieList.get(j).debuffState.equals("None")) {
								zombieList.get(j).debuffState = "Shocked";
								zombieList.get(j).debuffState();
								zombieList.get(j).invincibilityTime = 0;
							}
						}
						else
						{
							bulletList.get(i).bulletActive = false;
							explosionList.add(new Explosion(bulletList.get(i).bulletXPosition, bulletList.get(i).bulletYPosition));
						}
					}
				}
			}
		}
	}

	// handles what happens if a zombie is killed by a tower
	public void checkRemove()
	{
		for (int i = 0; i < zombieList.size(); i++)
		{
			if (!zombieList.get(i).Active || zombieList.get(i).enemyHealth <= 0) {
				zombiesKilledThisWave++;
				zombieList.remove(i);
				UI.money += 5;
			}
		}
		for (int i = 0; i < bulletList.size(); i++)
		{
			if (!bulletList.get(i).bulletActive || bulletList.get(i).bulletLifetime <= 0) {

				bulletList.remove(i);
			}
		}
	}

	// checks if the tower can be built on a part of the screen
	// returns false if it can't be build because it will be placed on ui or out of bounds
	public boolean isBuildable(float y)
	{
		if (selectedTower == towerType.PulseTower) // for the pulse tower, which is 9x the size of a normal tower
		{
			return (y < 150 && y >= 0) || (y >= 350 && y < 450);
		}

		return y >= 0 && y < 500;
	}

	// handles the user's clicks and what parts of the screen do if clicked
	public void Input()
	{
		if(Gdx.input.justTouched())
		{
			float x;
			float y;
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			Camera.unproject(touchPos);
			x = touchPos.x;
			y = touchPos.y;

			viewingPlacingTiles((int) x, (int) y); // Lets the player press a button to see where their selected weapon can be placed

			// if the player tried to place a tower on another tower, return nothing
			for (int i = 0; i < towerList.size(); i++)
			{
				if (towerList.get(i).getHitbox().contains(x, y))
				{
					return;
				}
			}

			// determines if the tower is buildable
			// ie if the user has the funds to purchase them
			if (isBuildable(y) && UI.money >= UI.cost)
			{
				Towers tower = null;
				switch (selectedTower)
				{
					case Cannon :
						tower = new Cannon (x, y);
						UI.money -= Cannon.costToDeploy;
						break;
					case Minigun :
						tower = new Minigun (x, y);
						UI.money -= Minigun.costToDeploy;
						break;
					case FireCannon :
						tower = new FireCannon (x, y);
						UI.money -= FireCannon.costToDeploy;
						break;
					case PulseTower :
						tower = new PulseTower(x, y);
						UI.money -= PulseTower.costToDeploy;
						break;
					case lightningTower:
						tower = new LightningTower(x, y);
						UI.money -= LightningTower.costToDeploy;
						break;
					case vanguardTower:
						tower = new vanguardTower(x, y);
						UI.money -= vanguardTower.costToDeploy;
					default:
						break;
				}
				enemyPath = new rootFinder();
				towerList.add(tower);
				if (!zombieList.isEmpty()) {
					nodeListing = enemyPath.findRoot(new Vector2(rootFinder.roundToFifty((int) zombieList.get(0).enemyXPosition), rootFinder.roundToFifty((int) zombieList.get(0).enemyYPosition)), new Vector2(rootFinder.roundToFifty(-50), rootFinder.roundToFifty(250)));
				}
				if (!enemyPath.hasPath) // does not allow the user to completely block the enemy's path
				{
					towerList.remove(tower);
					UI.money+=tower.costToDeploy;
				}
			}
			// Checks if the weapon buttons are clicked to change the selected weapon
			else if (cannonButton.isClicked(x, y))
			{
				selectedTower = towerType.Cannon;
				UI.cost = 40;
				selectedBoxX = 117;
				selectedBoxY = 500;
			}
			else if (fireCannonButton.isClicked(x, y))
			{
				selectedTower = towerType.FireCannon;
				UI.cost = 80;
				selectedBoxX = 223; //237
				selectedBoxY = 500;
			}
			else if (pulseTowerButton.isClicked(x, y))
			{
				selectedTower = towerType.PulseTower;
				UI.cost = 800;
				selectedBoxX = 329;
				selectedBoxY = 500;
			}
			else if (minigunButton.isClicked(x, y))
			{
				selectedTower = towerType.Minigun;
				UI.cost = 100;
				selectedBoxX = 435;
				selectedBoxY = 500;
			}
			else if (lightningTowerButton.isClicked(x, y))
			{
				selectedTower = towerType.lightningTower;
				UI.cost = 150;
				selectedBoxX = 541;
				selectedBoxY = 500;
			}
			else if (vanguardTowerButton.isClicked(x, y))
			{
				selectedTower = towerType.vanguardTower;
				UI.cost = 30;
				selectedBoxX = 647;
				selectedBoxY = 500;
			}
		}

		// Key inputs for testing
		if(Gdx.input.isKeyJustPressed(Input.Keys.S))
		{
			zombieList.add(new FastZombie(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY()));
		}

		if(Gdx.input.isKeyPressed(Input.Keys.L))
		{
			zombieList.add(new Zombie(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY()));
		}
	}
}
