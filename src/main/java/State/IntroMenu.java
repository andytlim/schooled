package State;
/* Tutorial Reference: http://slick.cokeandcode.com/wiki/doku.php?id=02_-_slickblocks
 * 
 */


import game.Schooled;

import java.awt.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Object.Text;
import Object.TextList;

public class IntroMenu extends BasicGameState{

	/* Variables */
	private int stateID = -1;
	
	/* Background */
	private Image background = null;
	
	/* Screen Text */
	private Text title;
	private Text subHeader;
	private Text exitPrompt;
	private TextList intro;
	private boolean exit = false;
	
	/* Pencil */
	private Image pencil;
	private boolean drawPencil;
	private int pencilX;
	private int pencilY;
	
	/* Constructor */
	public IntroMenu (int stateID) {
		this.stateID = stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		/* Loads Characters and Sets Current Character */
		Schooled.arc.loadAnimations(true);
		Schooled.treble.loadAnimations(true);
		Schooled.currentChar = Schooled.arc;
		
		/* Loads Background Image */
		background = new Image("images/background1.jpg");
		
		/* Loads Title */
		title = new Text(120, "Schooled!", Schooled.COLOR);
		title.setLocation(gc.getWidth()/2 - title.getWidth()/2, 150);
		
		/* Loads Sub Header */
		subHeader = new Text(20, "Copyright 2011. COMP 585 Serious Games Inc.", Schooled.COLOR);
		subHeader.setLocation(gc.getWidth()/2 - subHeader.getWidth()/2, gc.getHeight() - subHeader.getHeight() - 10);
		
		/* Loads Intro Menu Options */
		intro = new TextList(0, gc.getHeight() / 2, 25, gc.getWidth(), 20, Schooled.COLOR);
		intro.addText("Start Game");
		intro.addText("End Game");
		intro.center();
		
		/* Loads Pencil */
		pencil = new Image("images/pencil.png");
		drawPencil = false;
		
		/* Loads Exit Prompt */
		exitPrompt = new Text(40, "Quit? (y/n)", Schooled.COLOR);
		exitPrompt.setLocation(gc.getWidth()/2 - exitPrompt.getWidth()/2, gc.getHeight()/2 - exitPrompt.getHeight()/2);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		/* Draws Background */
		background.draw(0,0);
		
		/* Draws Title */
		title.drawString();
		
		/* Draws Sub Header */
		subHeader.drawString();
		
		/* Draws Pencil */
		if (drawPencil && !exit)
			pencil.draw(pencilX-35,pencilY,25,25);
		
		/* Handles Exit */
		if (!exit) {
			intro.drawTextList();
		}
		else {
			exitPrompt.drawString();
		}
		
		//Schooled.arc.animateDead().draw();
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {	
		
		/* Gets Input */
		Input input = gc.getInput();

		/* Gets Mouse Location */
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		/* Handles Start Game and End Game */
		if (Schooled.textHover(mouseX, mouseY, intro.getTextLine(0))) {
			drawPencil = true;
			pencilX = intro.getTextLine(0).getX();
			pencilY = intro.getTextLine(0).getY();
			if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
				sbg.enterState(Schooled.MAINMENU);
		}
		else if (Schooled.textHover(mouseX, mouseY, intro.getTextLine(1))) {
			drawPencil = true;
			pencilX = intro.getTextLine(1).getX();
			pencilY = intro.getTextLine(1).getY();
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				exit = true;
		}
		else
			drawPencil = false;
		
		/* Exit Game */
		if (exit) {
			if (input.isKeyPressed(Input.KEY_Y)) 
				gc.exit();
			else if (input.isKeyPressed(Input.KEY_N))
				exit = false;
		}
	}
	
	/* Gets State ID */
	public int getID() {
		return this.stateID;
	}
}
