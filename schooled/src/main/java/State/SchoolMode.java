package State;
/* Tutorial Reference: http://slick.cokeandcode.com/wiki/doku.php?id=02_-_slickblocks
 * 
 */

import game.Schooled;

import java.awt.Color;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Object.Text;
import Object.TextList;

public class SchoolMode extends BasicGameState{

	/* State ID */
	private int stateID = -1;
	
	/* Background Image */
	private Image background = null;
	
	/* Teacher Image */
	private Image teacher1 = null;
	private Image teacher2 = null;
	private Image teacher1blink = null;
	private Image teacher2blink = null;
	private Image teacher1talk = null;
	private Image teacher2talk = null;
	private boolean blink;
	private int blinktimer;
	private String esp; 
	
	/* Header */
	private Text header;
	
	/* ESP Lessons */
	private TextList MathESPList;
	private TextList MusicESPList;
	private ArrayList<Image> MathLessons;
	private ArrayList<Image> MusicLessons;
	private Text back;
	private boolean backtolist;
	private int lesson = -1;
	
	/* Check Mark */
	Image checkmark;
	
	/* Constructor */
	public SchoolMode (int stateID) {
		this.stateID = stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		/* Loads Background Image */
		background = new Image("images/background1.jpg");
		
		/* Loads Teachers */
		teacher1 = new Image("images/teacher1.png").getSubImage(0, 0, 400, 510);
		teacher2 = new Image("images/teacher2.png").getSubImage(500, 0, 400, 510);
		teacher1blink = new Image("images/teacher1.png").getSubImage(0, 515, 400, 100);
		teacher2blink = new Image("images/teacher2.png").getSubImage(500, 515, 400, 100);
		teacher1talk = new Image("images/teacher1talk1.png");
		teacher2talk = new Image("images/teacher1talk1.png");
		blink = true;
		
		if (Schooled.currentChar.getName().equals("Arc Theorem"))
			esp = "math";
		else
			esp = "music";
			
		/* Loads Header */
		header = new Text(50, "Educational Subject Program: Math", Schooled.COLOR);
		header.setLocation(gc.getWidth()/2 - header.getWidth()/2, 50);
		
		/* Loads Math ESP Lesson Titles */
		MathESPList = new TextList(0, gc.getHeight() / 2 - 120, 25, gc.getWidth(), 25, Schooled.COLOR);
		MathESPList.addText("Lesson 1 - The Power of Vectors");
		MathESPList.addText("Lesson 2 - Sets, Sets, and more Sets");
		MathESPList.addText("Lesson 3 - Breaking Down Dimensions");
		MathESPList.addText("Back to Main Menu");
		MathESPList.center();
		
		/* Loads Math Lessons */
		MathLessons = new ArrayList<Image>();
		MathLessons.add(new Image("images/mathlesson1.png"));
		MathLessons.add(new Image("images/mathlesson2.png"));
		MathLessons.add(new Image("images/mathlesson3.png"));
		
		/* Loads Music ESP Lesson Titles */
		MusicESPList = new TextList(0, gc.getHeight() / 2 - 120, 25, gc.getWidth(), 25, Schooled.COLOR);
		MusicESPList.addText("Lesson 1 - Rests Make It Epic!");
		MusicESPList.addText("Lesson 2 - Fiddling with Chords");
		MusicESPList.addText("Lesson 3 - Arpeggio");
		MusicESPList.addText("Back to Main Menu");
		MusicESPList.center();
		
		/* Loads Music Lessons */
		MusicLessons = new ArrayList<Image>();
		MusicLessons.add(new Image("images/musiclesson1.png"));
		MusicLessons.add(new Image("images/musiclesson2.png"));
		MusicLessons.add(new Image("images/musiclesson3.png"));
		
		/* Loads Check Mark */
		checkmark = new Image("images/check.png");
		
		/* Back Button */
		back = new Text(25, "Back", Color.WHITE);
		
		/* Shows ESP Lesson List */
		backtolist = true;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		/* Draws Background */
		background.draw(0,0);
		
		/* Draws Title */
		if (esp.equals("math"))
			header.setText("Educational Subject Program: Math");
		else
			header.setText("Educational Subject Program: Music");
		header.drawString();
		
		/* Determines which ESP to Display */	
		if (esp.equals("math")) {
			/* Draws Lesson List */
			if (backtolist) {
				MathESPList.drawTextList();
				
				if (Schooled.currentChar.getAvailable(0))
					checkmark.draw(MathESPList.getTextLine(0).getX() - 35, MathESPList.getTextLine(0).getY(), 25, 25);
				if (Schooled.currentChar.getAvailable(1))
					checkmark.draw(MathESPList.getTextLine(1).getX() - 35, MathESPList.getTextLine(1).getY(), 25, 25);
				if (Schooled.currentChar.getAvailable(2))
					checkmark.draw(MathESPList.getTextLine(2).getX() - 35, MathESPList.getTextLine(2).getY(), 25, 25);
				
			}
			else {
				if (lesson == 0)
					MathLessons.get(0).draw(gc.getWidth() / 2 - MathLessons.get(0).getWidth() / 2, 150);
				if (lesson == 1)
					MathLessons.get(1).draw(gc.getWidth() / 2 - MathLessons.get(1).getWidth() / 2, 150);
				if (lesson == 2)
					MathLessons.get(2).draw(gc.getWidth() / 2 - MathLessons.get(2).getWidth() / 2, 150);
				back.drawString(120, 575);
			}
			
			/* Draws Teacher */
			teacher1.draw(628,290);
			if (blink) {
				teacher1blink.draw(617,403);
			}
			teacher1talk.draw(10,645);	
		}
		else {
			/* Draws Lesson List */
			if (backtolist) {
				MusicESPList.drawTextList();
				
				if (Schooled.currentChar.getAvailable(0))
					checkmark.draw(MusicESPList.getTextLine(0).getX() - 35, MusicESPList.getTextLine(0).getY(), 25, 25);
				if (Schooled.currentChar.getAvailable(1))
					checkmark.draw(MusicESPList.getTextLine(1).getX() - 35, MusicESPList.getTextLine(1).getY(), 25, 25);
				if (Schooled.currentChar.getAvailable(2))
					checkmark.draw(MusicESPList.getTextLine(2).getX() - 35, MusicESPList.getTextLine(2).getY(), 25, 25);
			}
			else {
				if (lesson == 0)
					MusicLessons.get(0).draw(gc.getWidth() / 2 - MusicLessons.get(0).getWidth() / 2, 150);
				if (lesson == 1)
					MusicLessons.get(1).draw(gc.getWidth() / 2 - MusicLessons.get(1).getWidth() / 2, 150); 
				if (lesson == 2)
					MusicLessons.get(2).draw(gc.getWidth() / 2 - MusicLessons.get(2).getWidth() / 2, 150); 
				back.drawString(120, 575);
			}
			
			/* Draws Teacher */
			teacher2.draw(600,290);
			if (blink) {
				teacher2blink.draw(599,403);
			}
			teacher2talk.draw(10,645);	
		}
			
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {	
		
		if (Schooled.currentChar.getName().equals("Arc Theorem"))
			esp = "math";
		else
			esp = "music";
		
		/* Gets Input */
		Input input = gc.getInput();

		/* Gets Mouse Location */
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		/* Handles School Mode Exit */
		if (lesson <= -1 && input.isKeyPressed(Input.KEY_ESCAPE)){
			sbg.enterState(Schooled.MAINMENU);
		}
		
		if (lesson > -1 && input.isKeyPressed(Input.KEY_ESCAPE)){
			lesson = -1;
			backtolist = true;
		}
		
		/* Handles Navigation through ESPs */
		if (lesson <= -1) {
			if (esp.equals("math")) {
				if (Schooled.textHover(mouseX, mouseY, MathESPList.getTextLine(0))) {
					teacher1talk = new Image("images/teacher1talk2.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						Schooled.currentChar.enableSkill(0);
						lesson = 0;
						backtolist = false;
					}
				}
				else if (Schooled.textHover(mouseX, mouseY, MathESPList.getTextLine(1))) {
					teacher1talk = new Image("images/teacher1talk3.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						Schooled.currentChar.enableSkill(1);
						lesson = 1;
						backtolist = false;
					}
				}
				else if (Schooled.textHover(mouseX, mouseY, MathESPList.getTextLine(2))) {
					teacher1talk = new Image("images/teacher1talk4.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						Schooled.currentChar.enableSkill(2);
						lesson = 2;
						backtolist = false;
					}
				}
				else if (Schooled.textHover(mouseX, mouseY, MathESPList.getTextLine(3))) {
					teacher1talk = new Image("images/teacher1talk1.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						sbg.enterState(Schooled.MAINMENU);
					}
				}
				else {
					teacher1talk = new Image("images/teacher1talk1.png");
				}
			}
			else {
				if (Schooled.textHover(mouseX, mouseY, MusicESPList.getTextLine(0))) {
					teacher2talk = new Image("images/teacher2talk2.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						Schooled.currentChar.enableSkill(0);
						lesson = 0;
						backtolist = false;
					}
				}
				else if (Schooled.textHover(mouseX, mouseY, MusicESPList.getTextLine(1))) {
					teacher2talk = new Image("images/teacher2talk3.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						Schooled.currentChar.enableSkill(1);
						lesson = 1;
						backtolist = false;
					}
				}
				else if (Schooled.textHover(mouseX, mouseY, MusicESPList.getTextLine(2))) {
					teacher2talk = new Image("images/teacher2talk4.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						Schooled.currentChar.enableSkill(2);
						lesson = 2;
						backtolist = false;
					}
				}
				else if (Schooled.textHover(mouseX, mouseY, MusicESPList.getTextLine(3))) {
					teacher2talk = new Image("images/teacher2talk1.png");
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						sbg.enterState(Schooled.MAINMENU);
					}
				}
				else {
					teacher2talk = new Image("images/teacher2talk1.png");
				}
			}
		}
		else {
			if (Schooled.textHover(mouseX, mouseY, back)) {
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					lesson = -1;
					backtolist = true;
				}
			}
		}
		
		animateTeacher(gc);
	}
	
	/* Animates the teacher on the screen */
	public void animateTeacher(GameContainer gc) {
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
	
	/* Sets the Educational Subject Program */
	public void setESP(String esp) {
		this.esp = esp;
	}
	
	public int getID() {
		return this.stateID;
	}
}
