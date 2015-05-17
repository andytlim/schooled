package game;

/* This is the MAIN class. Classes are separated by different packages.
 * 
 * Programmer Notes:
 * - Add game states here [Define states in State package]
 * - Define static functions (general purpose functions) here
 * - Set game title here
 * - Set window size here
 * 
 * This class runs the game.
 * 
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import DataObject.ArcTheorem;
import DataObject.TrebleClef;
import Object.Text;
import Object.Character;
import State.*;

public class Schooled extends StateBasedGame{
	
	/* Constants */
	public static final int INTROMENU = 0;
	public static final int MAINMENU = 1;
	public static final int ARCADEMODE = 2;
	public static final int SCHOOLMODE = 3;
	
	/* Primary Text Color */
	public static final Color COLOR = Color.black;
	public static final Color WHITE = Color.white;
	
	/* Save File Loaded */
	public static ArcTheorem arc = new ArcTheorem();
	public static TrebleClef treble = new TrebleClef();
	public static Character currentChar = null;
	
	/* Constructor */
	public Schooled(String name) {
		super(name);
		
		this.addState(new IntroMenu(INTROMENU));
		this.addState(new MainMenu(MAINMENU));
		this.addState(new ArcadeMode(ARCADEMODE));
		this.addState(new SchoolMode(SCHOOLMODE));
		
		this.enterState(INTROMENU);
	}

	/* Main Program */
	public static void main(String[] args) throws SlickException {
		
		AppGameContainer app = new AppGameContainer(new Schooled("Schooled!"));
		
		/* Window Size */
		app.setDisplayMode(1000, 800, false);
		
		/* Options */
		app.setShowFPS(false);
		
		/* Start Game */
		app.start();
	}
	
	/**
	* This method handles running the init() method for each state but
	* since the init() method is called whenever you call another state
	* we just leave it empty
	*/
	public void initStatesList(GameContainer gc) throws SlickException {}
	
	/* Returns true if mouse is over a Text Object */ 
	public static boolean textHover(int mouseX, int mouseY, Text text) {
		return (mouseX >= text.getX() && mouseX <= text.getX() + text.getWidth()) &&
        		(mouseY >= text.getY() && mouseY <= text.getY() + text.getHeight());
	}
	
	/* Returns true if mouse is over a Image object */
	public static boolean imageHover(int mouseX, int mouseY, Image image) {
		return (mouseX >= image.getCenterOfRotationX() && mouseX <= image.getCenterOfRotationX() + image.getWidth()) &&
		(mouseY >= image.getCenterOfRotationY() && mouseY <= image.getCenterOfRotationY() + image.getHeight());
	}
	
	public static Image loadImage(String path)
	{
		path = "X:/dev/java/schooled/schooled/src/main/java/" + path;
		try {
		    BufferedImage bufferedImage = ImageIO.read(new File(path));
		    Texture texture = BufferedImageUtil.getTexture("", bufferedImage);
		    Image image = new Image(texture.getImageWidth(), texture.getImageHeight());
		    image.setTexture(texture); 
		    return image;
		} catch (Exception e) {
			System.out.println("Schooled.loadImage error: " + e);
			return null;
		}
	}
}
