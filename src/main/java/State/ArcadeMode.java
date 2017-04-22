package State;
/* Tutorial Reference: http://slick.cokeandcode.com/wiki/doku.php?id=02_-_slickblocks
 * 
 */

import game.Schooled;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import DataObject.ArcTheorem;
import DataObject.TrebleClef;
import Object.Grid;
import Object.Shortcut;
import Object.Skill;
import Object.Text;
import Object.Character;
import Object.TextList;


public class ArcadeMode extends BasicGameState{
	
	/* FIX DAMAGE CALCULATION */

	/* State ID */
	int stateID = -1;
	
	/* Constants */
	private static final String HIT = "images/hit.png";
	
	/* Random Number Generator */
	private Random random = new Random();
	
	/* Timers */
	private int animationTimer = 0;
	private int compMoveTimer = 0;
	private int compAttackTimer = 0;
	
	/* Images : Background, ActionBox, SkillBox */
	private Image background;
	private Image actionBox;
	private Image skillBox;
	
	/* Skill Information */
	private Image skill1info;
	private Image skill2info; 
	private Image skill3info; 
	private Image skill4info;
	private int skillHover = -1;
	private Text key1;
	private Text key2;
	private Text key3;
	private Text key4;
	
	/* Headers */
	private Text actionBoxText;
	private Text skillBoxText;
	
	/* Grids */
	private Grid playerGrid;
	private Grid computerGrid;
	
	/* Player Data */
	private Character player;
	private Text playerName;
	private Text playerHealth;
	private Animation playerAction;
	
	/* Computer Data */	
	private Character computer;
	private Text computerName;
	private Text computerHealth;
	private Animation computerAction;
	
	/* ActionHistory, Shortcuts */
	private ArrayList<Text> actionHistory;
	private ArrayList<Shortcut> shortcuts;
	
	/* Victory Message */
	private Text winorlose;
	
	/* Booleans : Victory, Hit */
	private boolean playerWin = false;
	private boolean computerWin = false;
	private boolean hit = false;
	private boolean enter = false;
	
	/* Timers for CoolDown and Duration */
	private int pskill1timer;
	private int pskill2timer;
	private int pskill3timer;
	private int pskill4timer;
	private int cskill1timer;
	private int cskill2timer;
	private int cskill3timer;
	private int cskill4timer;
	
	/* Boosts */
	private Image boostPlus;
	private Image boostDefend;
	private boolean pBoostPlus = false;
	private boolean pBoostDefend = false;
	private boolean cBoostPlus = false;
	private boolean cBoostDefend = false;
	
	/* Waiting for Resets */
	private boolean resetWaiting1 = false;
	private boolean resetWaiting2 = false;
	private boolean resetWaiting3 = false;
	private boolean resetWaiting4 = false;
	
	/* Win Timer */
	private int winTimer;
	
	/* Constructor : Stores State ID */
	public ArcadeMode (int stateID) {
		this.stateID = stateID;
	}
	
	/* Initialize */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		/* Loads Images for Background and ActionBox */
		background = new Image("images/background1.jpg");
		actionBox = new Image("images/blackbox.png");
		skillBox = new Image("images/blackbox.png");
		
		/* Loads Character Information */
		player = new Character();
		computer = new Character();
		
		/* Initializes Action History and Shortcuts */
		actionHistory = new ArrayList<Text>();
		shortcuts = new ArrayList<Shortcut>();
		
		/* Loads Text */
		actionBoxText = new Text(20, "ACTION HISTORY", Schooled.WHITE);
		skillBoxText = new Text(20, "SKILLS", Schooled.WHITE);
		key1 = new Text(15, "Key 1", Schooled.WHITE);
		key2 = new Text(15, "Key 2", Schooled.WHITE);
		key3 = new Text(15, "Key 3", Schooled.WHITE);
		key4 = new Text(15, "Key 4", Schooled.WHITE);
		
		/* Loads Grid */
		playerGrid = new Grid("images/ptile2.png","images/ptile2.png");
		computerGrid = new Grid("images/ctile.png","images/ctile.png");
		playerGrid.loadGrid();
		computerGrid.loadGrid();
		
		/* Loads Images for Boosts */
		boostPlus = new Image("images/boostplus.png");
		boostDefend = new Image("images/boostdefend.png");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		/* Draws Background */
		background.draw(0,0);
		
		/* If Player or Computer Wins, End Game */
		win(gc);
		
		/* Draws Onscreen Items */
		drawGrid(gc);
		
		// EDIT
		actionBox.draw(0, gc.getHeight() - 150, 300, 145);
		actionBoxText.drawString(15, gc.getHeight() - 150);
		skillBox.draw(310, gc.getHeight() - 150, 510, 145);
		skillBoxText.drawString(330, gc.getHeight() - 150);
		
		key1.drawString(370, gc.getHeight() - 30);
		key2.drawString(475, gc.getHeight() - 30);
		key3.drawString(580, gc.getHeight() - 30);
		key4.drawString(685, gc.getHeight() - 30);
		
		drawActionHistory(gc);
		drawGUI();
		drawHealth(gc, g);
		drawCoolDown(gc, g);
		drawShortcuts(gc);
			
		computer.animateStanding().draw(computerGrid.getCurrPosX() * 110 + gc.getWidth() - 470,
				computerGrid.getCurrPosY() * 110 + 265);
		if (cBoostPlus)
			boostPlus.draw(computerGrid.getCurrPosX() * 110 + gc.getWidth() - 430, computerGrid.getCurrPosY() * 110 + 265, 20, 20);
		if (cBoostDefend)
			boostDefend.draw(computerGrid.getCurrPosX() * 110 + gc.getWidth() - 410, computerGrid.getCurrPosY() * 110 + 265, 20, 20);
			
		player.animateStanding().draw(playerGrid.getCurrPosX() * 110 + 190,
				playerGrid.getCurrPosY() * 110 + 265);
		if (pBoostPlus)
			boostPlus.draw(playerGrid.getCurrPosX() * 110 + 230, playerGrid.getCurrPosY() * 110 + 265, 20, 20);
		if (pBoostDefend)
			boostDefend.draw(playerGrid.getCurrPosX() * 110 + 250, playerGrid.getCurrPosY() * 110 + 265, 20, 20);
		
		if (skillHover == 0)
			skill1info.draw(gc.getWidth() / 2 - skill1info.getWidth() / 2, 100);
		else if (skillHover == 1)
			skill2info.draw(gc.getWidth() / 2 - skill1info.getWidth() / 2, 100);
		else if (skillHover == 2)
			skill3info.draw(gc.getWidth() / 2 - skill1info.getWidth() / 2, 100);
		else if (skillHover == 3)
			skill4info.draw(gc.getWidth() / 2 - skill1info.getWidth() / 2, 100);
			
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		if (!enter) {
			if (Schooled.currentChar.getName().equals("Arc Theorem")) {
				player = Schooled.currentChar;
				((ArcTheorem) player).loadAnimations(true);
				computer = new TrebleClef();
				((TrebleClef) computer).loadAnimations(false);
				
				skill1info = new Image("images/skillvector.png");
				skill2info = new Image("images/skillpowerset.png");
				skill3info = new Image("images/skilldimensionboost.png");
				skill4info = new Image("images/skilldefend.png");
			}
			else {
				player = Schooled.currentChar;
				((TrebleClef) player).loadAnimations(true);
				computer = new ArcTheorem();
				((ArcTheorem) computer).loadAnimations(false);
				
				skill1info = new Image("images/skillchord.png");
				skill2info = new Image("images/skillarpeggio.png");
				skill3info = new Image("images/skillrest.png");
				skill4info = new Image("images/skilldefend.png");
			}
			
			/* Loads Shortcuts */
			if (shortcuts.size() == 0) {
				shortcuts.add(new Shortcut(new Image("images/skill.png"), player.getSkill(0), Input.KEY_1));
				shortcuts.add(new Shortcut(new Image("images/skill.png"), player.getSkill(1), Input.KEY_2));
				shortcuts.add(new Shortcut(new Image("images/skill.png"), player.getSkill(2), Input.KEY_3));
				shortcuts.add(new Shortcut(new Image("images/defend2.png"), player.getSkill(3), Input.KEY_4));
			}
			
			/* Loads Onscreen Player Information */
			playerName = new Text(20, player.getName(), Schooled.COLOR);
			playerName.setLocation(10, 60);
			playerHealth = new Text(20, player.getHealth() + "/" + player.getMaxHealth(), Schooled.COLOR);
			playerHealth.setLocation(10, 40);
			
			/* Loads Onscreen Computer Information */
			computerName = new Text(20, computer.getName(), Schooled.COLOR);
			computerName.setLocation(gc.getWidth() - computerName.getWidth() - 10, 60);
			computerHealth = new Text(20, computer.getHealth() + "/" + computer.getMaxHealth(), Schooled.COLOR);
			computerHealth.setLocation(gc.getWidth() - computerHealth.getWidth() - 10, 40);	
			
			enter = true;
		}
		
		/* Gets Input*/
		Input input = gc.getInput();

		/* Gets Mouse Location */
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		/* Main Code */
		if (!playerWin && !computerWin) {
			
			/* Player Uses a Skill */
			playerUseSkill(gc, input);

			resetBoosts(gc);
			
			skillHover(mouseX,mouseY);
			if (hit == true){
				if ((int)gc.getTime() - animationTimer > 500){
					computerGrid.loadGrid();
					playerGrid.loadGrid();
					hit = false;
				}
			}
			
			playerMove(input);
			
			/* Computer Moves */
			if (gc.getTime() - compMoveTimer > 2000 ) {
				randomCompMove();
				compMoveTimer = (int)gc.getTime();
			}
			
			/* Computer Attacks */
			if (gc.getTime() - compAttackTimer > 3000) {
				randomCompAttack(gc);
				compAttackTimer = (int)gc.getTime();
			}
		}
		else {
			if (input.isKeyPressed(Input.KEY_ESCAPE)) {
				Schooled.currentChar.setHealth(Schooled.currentChar.getMaxHealth());
				playerWin = false;
				computerWin = false;
				enter = false;
				sbg.enterState(Schooled.MAINMENU);
			}
		}
    }
	
	private void win(GameContainer gc) throws SlickException {
		if (playerWin || computerWin) {
			if (playerWin) {
				winorlose = new Text(130, "You Win!", java.awt.Color.black);
			}
			if (computerWin) {
				winorlose = new Text(130, "Computer Wins :(", java.awt.Color.black);
			}
			winorlose.setLocation(gc.getWidth()/2 - winorlose.getWidth()/2, 100);
			winorlose.drawString();
		}
	}
	
	private void skillHover(int mouseX, int mouseY) {		
		if (mouseX >= 370 && mouseX <= 445 && mouseY >= 690 && mouseY <= 765) {
			skillHover = 0;
		}
		else if (mouseX >= 475 && mouseX <= 550 && mouseY >= 690 && mouseY <= 765) {
			skillHover = 1;
		}
		else if (mouseX >= 580 && mouseX <= 655 && mouseY >= 690 && mouseY <= 765) {
			skillHover = 2;
		}
		else if (mouseX >= 685 && mouseX <= 760 && mouseY >= 690 && mouseY <= 765) {
			skillHover = 3;
		}
		else
			skillHover = -1;
	}
	
	private void drawGrid(GameContainer gc) {
		/* Draws Player and Computer Grids */
		playerGrid.drawGrid(170, 250);
		computerGrid.drawGrid(gc.getWidth() - 490, 250);
	}
	
	private void drawActionHistory(GameContainer gc) throws SlickException {
		//needs to be able to set how many appear
		if (actionHistory.size() != 0) {
			
			int y = gc.getHeight() - 120;
			
			// iterates each text
			for (int i = 0; i < actionHistory.size(); i++) {
				actionHistory.get(i).setLocation(15, y);
				actionHistory.get(i).drawString();
				y += 20;
			}
		}
	}
	
	private void drawHealth(GameContainer gc, Graphics g) {
		// Displays Player Health
		Rectangle pHealthGUI = new Rectangle(10f, 30f, player.getHealth() * 300f / 500, 10f);
		GradientFill pFill = new GradientFill(10f, 30f, Color.red, 300f, 10f, Color.red);
		g.fill(pHealthGUI, pFill);
		
		// Displays Computer Health
		Rectangle cHealthGUI = new Rectangle(gc.getWidth() - (computer.getHealth() * 300f / 500) - 10f, 30f, computer.getHealth() * 300f / 500, 10f);
		GradientFill cFill = new GradientFill(gc.getWidth() - 310f, 30f, Color.red, 300f, 10f, Color.red);
		g.fill(cHealthGUI, cFill);
	}
	
	private void drawCoolDown(GameContainer gc, Graphics g) {
		
		float decay;
		
		decay = player.getSkill(0).getCooldown() - (gc.getTime() - pskill1timer);
		decay = decay / player.getSkill(0).getCooldown();
		if (decay > 0) {
			Rectangle pHealthGUI = new Rectangle(370f, gc.getHeight() - 120f, decay * 75f, 10f);
			GradientFill pFill = new GradientFill(685f, gc.getHeight() - 120f, Color.green, 75f, 10f, Color.green);
			g.fill(pHealthGUI, pFill);
		}
		
		decay = player.getSkill(1).getCooldown() - (gc.getTime() - pskill2timer);
		decay = decay / player.getSkill(1).getCooldown();
		if (decay > 0) {
			Rectangle pHealthGUI = new Rectangle(475f, gc.getHeight() - 120f, decay * 75f, 10f);
			GradientFill pFill = new GradientFill(475f, gc.getHeight() - 120f, Color.green, 75f, 10f, Color.green);
			g.fill(pHealthGUI, pFill);
		}
		
		decay = player.getSkill(2).getCooldown() - (gc.getTime() - pskill3timer);
		decay = decay / player.getSkill(2).getCooldown();
		if (decay > 0) {
			Rectangle pHealthGUI = new Rectangle(580f, gc.getHeight() - 120f, decay * 75f, 10f);
			GradientFill pFill = new GradientFill(580f, gc.getHeight() - 120f, Color.green, 75f, 10f, Color.green);
			g.fill(pHealthGUI, pFill);
		}
		
		decay = player.getSkill(3).getCooldown() - (gc.getTime() - pskill4timer);
		decay = decay / player.getSkill(3).getCooldown();
		if (decay > 0) {
			//(75f - decay * 75f) + gc.getHeight() - 125f
			Rectangle pHealthGUI = new Rectangle(685f, gc.getHeight() - 120f, decay * 75f, 10f);
			GradientFill pFill = new GradientFill(685f, gc.getHeight() - 120f, Color.green, 75f, 10f, Color.green);
			g.fill(pHealthGUI, pFill);
		}
	}
	
	private void drawShortcuts(GameContainer gc) {
		int next = 0;
		for (int i = 0; i < shortcuts.size(); i++) {
			shortcuts.get(i).getImage().draw(370 + next, gc.getHeight() - 110,75,75);
			next += 105;
		}
	}
	
	private void drawGUI() throws SlickException {
		// displays names & health
		playerName.drawString();
		playerHealth.drawString();
		computerName.drawString();
		computerHealth.drawString();
	}
	
	private void boostPlayer(Skill skill) throws SlickException {
		player.setAttack(player.getAttack() + skill.getAttack());
		player.setDefense(player.getDefense() + skill.getDefense());
	}	
	
	private void resetPlayer(Skill skill) throws SlickException {
		player.setAttack(player.getAttack() - skill.getAttack());
		player.setDefense(player.getDefense() - skill.getDefense());
	}	
	
	private void damagePlayer(Skill skill) throws SlickException {
		int damage = 0;
		damage = player.getDefense() - (computer.getAttack() + skill.getAttack());
		if (damage > 0)
			damage = 0;
		player.setHealth(player.getHealth() + damage);
		playerHealth.setText(player.getHealth() + "/" + player.getMaxHealth());
		
		if (player.getHealth() <= 0) {
			playerHealth.setText("0/" + player.getMaxHealth());
			computerWin = true;
		}
	}
	
	private void boostComputer(Skill skill) throws SlickException {
		computer.setAttack(computer.getAttack() + skill.getAttack());
		computer.setDefense(computer.getDefense() + skill.getDefense());
	}
	
	private void resetComputer(Skill skill) throws SlickException {
		computer.setAttack(computer.getAttack() - skill.getAttack());
		computer.setDefense(computer.getDefense() - skill.getDefense());
	}	
	
	/* Figure out why this doesn't work! */
	private void damageComputer(Skill skill) throws SlickException {
		int damage = 0;
		damage = computer.getDefense() - (player.getAttack() + skill.getAttack());
		if (damage > 0)
			damage = 0;
		System.out.println(computer.getHealth() + damage);
		computer.setHealth(computer.getHealth() + damage);
		computerHealth.setText(computer.getHealth() + "/" + computer.getMaxHealth());
		
		if (computer.getHealth() <= 0) {
			computerHealth.setText("0/" + computer.getMaxHealth());
			playerWin = true;
		}
	}
	
	private void updateActionHistory(String msg) throws SlickException {
		
		// stores action
		Text action = new Text(15, msg, java.awt.Color.white);
		actionHistory.add(action);
		
		if (actionHistory.size() > 5) {
			actionHistory.remove(0);
		}
	}
	
	private void playerUseSkill(GameContainer gc, Input input) throws SlickException {
		if (input.isKeyPressed(shortcuts.get(0).getKey()) && (gc.getTime() - pskill1timer > player.getSkill(0).getCooldown())) {
			skillOne(gc,0);
		}
		else if (input.isKeyPressed(shortcuts.get(1).getKey()) && gc.getTime() - pskill2timer > player.getSkill(1).getCooldown()) {
			skillTwo(gc,0);
		}
		else if (input.isKeyPressed(shortcuts.get(2).getKey()) && gc.getTime() - pskill3timer > player.getSkill(2).getCooldown()) {
			skillThree(gc,0);
		}
		else if (input.isKeyPressed(shortcuts.get(3).getKey()) && gc.getTime() - pskill4timer > player.getSkill(3).getCooldown()) {
			skillFour(gc, 0);
		}
	}
	
	/* Resets Skill 3 & 4 Boosts for Player and Computer */
	private void resetBoosts(GameContainer gc) throws SlickException {
		if ((int)gc.getTime() - pskill3timer > 5000 && resetWaiting1) {
			resetPlayer(player.getSkill(2));
			pBoostPlus = false;
			resetWaiting1 = false;
		}
		
		if ((int)gc.getTime() - pskill4timer > 3000 && resetWaiting2) {
			resetPlayer(player.getSkill(3));
			pBoostDefend = false;
			resetWaiting2 = false;
		}
		
		if ((int)gc.getTime() - cskill3timer > 5000 && resetWaiting3) {
			resetComputer(computer.getSkill(2));
			cBoostPlus = false;
			resetWaiting3 = false;
		}
		
		if ((int)gc.getTime() - cskill4timer > 3000 && resetWaiting4) {
			resetComputer(computer.getSkill(3));
			cBoostDefend = false;
			resetWaiting4 = false;
		}
		
	}
	
	/* 0 for human, 1 for computer */
	private void skillOne(GameContainer gc, int who) throws SlickException {
		hit = true;
		animationTimer = (int) gc.getTime();
		if (who == 0) {
			pskill1timer = (int) gc.getTime();
			updateActionHistory("Player used " + player.getSkill(0).getName());
			for (int i=0; i<3; i++){
				computerGrid.grid[i][playerGrid.getCurrPosY()] = new Image(HIT);
			}
			if (playerGrid.getCurrPosY() == computerGrid.getCurrPosY()) {
				damageComputer(player.getSkill(0));
			}
		}
		else {
			cskill1timer = (int) gc.getTime();
			updateActionHistory("Computer used " + computer.getSkill(0).getName());
			for (int i=0; i<3; i++){
				playerGrid.grid[i][computerGrid.getCurrPosY()] = new Image(HIT);
			}
			if (computerGrid.getCurrPosY() == playerGrid.getCurrPosY())
				damagePlayer(computer.getSkill(0));
		}
	}
	
	private void skillTwo(GameContainer gc, int who) throws SlickException {
		hit = true;
		animationTimer = (int) gc.getTime();
		if (who == 0) {
			pskill2timer = (int) gc.getTime();
			updateActionHistory("Player used " + player.getSkill(1).getName());
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					computerGrid.grid[i][j] = new Image(HIT);
				}
			}
			damageComputer(player.getSkill(1));
		}
		else {
			cskill2timer = (int) gc.getTime();
			updateActionHistory("Computer used " + computer.getSkill(1).getName());
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					playerGrid.grid[i][j] = new Image(HIT);
				}
			}
			damagePlayer(computer.getSkill(1));
		}
	}
	
	private void skillThree(GameContainer gc, int who) throws SlickException {
		if (who == 0) {
			pskill3timer = (int) gc.getTime();
			pBoostPlus = true;
			updateActionHistory("Player used " + player.getSkill(2).getName());
			boostPlayer(player.getSkill(2));
			resetWaiting1 = true;
		}
		else {
			cskill3timer = (int) gc.getTime();
			cBoostPlus = true;
			updateActionHistory("Computer used " + computer.getSkill(2).getName());
			boostComputer(computer.getSkill(2));
			resetWaiting3 = true;
		}
	}
	
	private void skillFour(GameContainer gc, int who) throws SlickException {		
		if (who == 0) {
			pskill4timer = (int) gc.getTime();
			pBoostDefend = true;
			updateActionHistory("Player used " + player.getSkill(3).getName());
			boostPlayer(player.getSkill(3));
			resetWaiting2 = true;
		}
		else {
			cskill4timer = (int) gc.getTime();
			cBoostDefend = true;
			updateActionHistory("Computer used " + computer.getSkill(3).getName());
			boostPlayer(computer.getSkill(3));
			resetWaiting4 = true;
		}
	}
	
	private void playerMove(Input input) throws SlickException {
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			playerGrid.moveUp();
		}
		else if (input.isKeyPressed(Input.KEY_DOWN)) {
			playerGrid.moveDown();
		}
		else if (input.isKeyPressed(Input.KEY_LEFT)){
			playerGrid.moveLeft();
		}
		else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			playerGrid.moveRight();
		}
	}
	
	private void randomCompMove() throws SlickException{
		int i = random.nextInt(4);
		if (i == 0 ) computerGrid.moveLeft();
		else if ( i == 1) computerGrid.moveRight();
		else if ( i == 2) computerGrid.moveUp();
		else computerGrid.moveDown();
	}
	
	private void randomCompAttack(GameContainer gc) throws SlickException{
		int i = random.nextInt(4);
		if (i == 0 && gc.getTime() - cskill1timer > computer.getSkill(0).getCooldown()) 
			skillOne(gc,1);
		else if ( i == 1 && gc.getTime() - cskill2timer > computer.getSkill(1).getCooldown()) 
			skillTwo(gc,1);
		else if ( i == 2 && gc.getTime() - cskill3timer > computer.getSkill(2).getCooldown()) 
			skillThree(gc,1);
		else if (i == 3 && gc.getTime() - cskill4timer > computer.getSkill(3).getCooldown())
			skillFour(gc, 1);
	}

	public int getID() {
		return this.stateID;
	}
}
