package State;
/* Tutorial Reference: http://slick.cokeandcode.com/wiki/doku.php?id=02_-_slickblocks
 * 
 */

import game.Schooled;

import java.awt.Color;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Object.Text;
import Object.TextList;

public class MainMenu extends BasicGameState{

	/* State ID */
	private int stateID = -1;
	
	/* Background Image */
	private Image background = null;
	
	/* Header */
	private Text header;
	
	/* Main Menu List */
	private TextList mainMenu;
	
	/* Student Animation */
	private Image student = null;
	private Image studentblink = null;
	private Image studenttalk = null;
	boolean blink;
	int blinktimer = 0;
	
	/* Pencil */
	private Image pencil;
	private boolean drawPencil;
	private int pencilX;
	private int pencilY;
	
	/* Star */
	private Image star;
	private boolean drawStar;
	private int starX;
	private int starY;
	
	/* Character Change */
	private Image choose;
	private Image spotlight;
	private Animation arcChar;
	private Animation trebleChar;
	private Text change1;
	private Text change2;
	private boolean characterChange = false;
	
	/* Constructor */
	public MainMenu (int stateID) {
		this.stateID = stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		/* Loads Background Image */
		background = Schooled.loadImage("images/background1.jpg");
		
		/* Loads Header */
		header = new Text(60, "Main Menu", Schooled.COLOR);
		header.setLocation(gc.getWidth()/2 - header.getWidth()/2, 150);
		
		/* Loads Main Menu Options */
		mainMenu = new TextList(0, gc.getHeight() / 2 - 120, 25, gc.getWidth(), 20, Schooled.COLOR);
		mainMenu.addText("Arcade Mode");
		mainMenu.addText("School Mode");
		mainMenu.addText("Change Character");
		mainMenu.addText("Back to Start Menu");
		mainMenu.center();
		
		/* Student */
		student = Schooled.loadImage("images/student.png").getSubImage(0, 0, 400, 510);
		studentblink = Schooled.loadImage("images/student.png").getSubImage(0, 515, 400, 100);
		studenttalk = Schooled.loadImage("images/studenttalk1.png");
		blink = true;
		
		/* Loads Pencil */
		pencil = Schooled.loadImage("images/pencil.png");
		drawPencil = false;
		
		/* Loads Star */
		star = Schooled.loadImage("images/star.png");
		drawStar = false;
		
		/* Character Change */
		choose = Schooled.loadImage("images/changepanel.png");
		spotlight = Schooled.loadImage("images/spotlight.png");
		arcChar = Schooled.arc.animateStanding();
		trebleChar = Schooled.treble.animateStanding();
		change1 = new Text(25, "Choose your character:", Color.WHITE);
		change2 = new Text(25, "Done", Color.WHITE);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		/* Draws Background */
		background.draw(0,0);
		
		/* Draws Title */
		header.drawString();
		
		/* Handles Character Change */
		if (!characterChange) {
			mainMenu.drawTextList();
			
			/* Draws Pencil */
			if (drawPencil)
				pencil.draw(pencilX-35,pencilY,25,25);
		}
		else {
			choose.draw(gc.getWidth() / 2 - choose.getWidth() / 2, gc.getHeight() / 2 - choose.getHeight() / 2 - 10);
			arcChar.draw(gc.getWidth() / 2 - 82, gc.getHeight() / 2 - 60);
			trebleChar.draw(gc.getWidth() / 2 + 30, gc.getHeight() / 2 - 60);
			change1.drawString(gc.getWidth() / 2 - change1.getWidth() / 2, gc.getHeight() / 2 - 155);
			change2.drawString(gc.getWidth() / 2 - change2.getWidth() / 2, gc.getHeight() / 2 + 90);
			
			/* Draws Star */
			if (drawStar)
				star.draw(starX,starY,25,25);
		}
		
		/* Draws Student */
		student.draw(600,290);
		studenttalk.draw(10,645);
		if (blink)
			studentblink.draw(596,403);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {	
		
		/* Gets Input */
		Input input = gc.getInput();

		/* Gets Mouse Location */
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		boolean leftClick = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		/* Handles Arcade Mode, School Mode, and Character Change */
		if (!characterChange) {
			if (Schooled.textHover(mouseX, mouseY, mainMenu.getTextLine(0))) {
				drawPencil = true;
				pencilX = mainMenu.getTextLine(0).getX();
				pencilY = mainMenu.getTextLine(0).getY();
				if (leftClick && isAllowed())
					sbg.enterState(Schooled.ARCADEMODE);
				
				if (!isAllowed())
					studenttalk = Schooled.loadImage("images/studenttalk2.png");
				else
					studenttalk = Schooled.loadImage("images/studenttalk3.png");
				
			}
			else if (Schooled.textHover(mouseX, mouseY, mainMenu.getTextLine(1))) {
				drawPencil = true;
				pencilX = mainMenu.getTextLine(1).getX();
				pencilY = mainMenu.getTextLine(1).getY();
				studenttalk = Schooled.loadImage("images/studenttalk4.png");
				if (leftClick) {
					sbg.enterState(Schooled.SCHOOLMODE);
				}
			}
			else if (Schooled.textHover(mouseX, mouseY, mainMenu.getTextLine(2))) {
				drawPencil = true;
				pencilX = mainMenu.getTextLine(2).getX();
				pencilY = mainMenu.getTextLine(2).getY();
				studenttalk = Schooled.loadImage("images/studenttalk5.png");
				if (leftClick)
					characterChange = true;
			}
			else if (Schooled.textHover(mouseX, mouseY, mainMenu.getTextLine(3))) {
				drawPencil = true;
				pencilX = mainMenu.getTextLine(3).getX();
				pencilY = mainMenu.getTextLine(3).getY();
				studenttalk = Schooled.loadImage("images/studenttalk6.png");
				if (leftClick)
					sbg.enterState(Schooled.INTROMENU);
			}
			else {
				drawPencil = false;
				studenttalk = Schooled.loadImage("images/studenttalk1.png");
			}
		}
		else {
			if (input.isKeyPressed(Input.KEY_ESCAPE))
				characterChange = false;
			
			if (Schooled.currentChar.getName().equals("Arc Theorem")) {
				drawStar = true;
				starX = gc.getWidth() / 2 - 82 + 15;
				starY = gc.getHeight() / 2 - 60 + 75;
			}
			else {
				drawStar = true;
				starX = gc.getWidth() / 2 + 30 + 15;
				starY = gc.getHeight() / 2 - 60 + 75;
			}
			
			if (mouseX >= gc.getWidth() / 2 - 82 && mouseX <= gc.getWidth() / 2 - 82 + arcChar.getWidth() 
					&& mouseY >= gc.getHeight() / 2 - 60  && mouseY <= gc.getHeight() / 2 - 60 + arcChar.getHeight()) {		
				if (leftClick)
					Schooled.currentChar = Schooled.arc;
			}
			else if (mouseX >= gc.getWidth() / 2 + 30 && mouseX <= gc.getWidth() / 2 + 30 + trebleChar.getWidth() 
					&& mouseY >= gc.getHeight() / 2 - 60  && mouseY <= gc.getHeight() / 2 - 60 + trebleChar.getHeight()) {
				if (leftClick)
					Schooled.currentChar = Schooled.treble;
			}
			
			if (Schooled.textHover(mouseX, mouseY, change2)) {
				drawPencil = true;
				pencilX = change2.getX();
				pencilY = change2.getY();
				
				if (leftClick)
					characterChange = false;
			}
			else
				drawPencil = false;
		}
		
		animateStudent(gc);
	}
	
	/* Animates the student on the main menu */
	public void animateStudent(GameContainer gc) {
		if (blink){
			if ((int)gc.getTime() - blinktimer > 100){
				blinktimer = (int)gc.getTime();
				blink = false;
			}
		}
		else {
			if ((int)gc.getTime() - blinktimer > 3000){
				blinktimer = (int)gc.getTime();
				blink = true;
			}
		}
	}
	
	/* Determines if character is allowed to enter Arcade Mode */
	public boolean isAllowed() {
		return Schooled.currentChar.getTotalSkills() >= 3;
	}
	
	public int getID() {
		return this.stateID;
	}
}
