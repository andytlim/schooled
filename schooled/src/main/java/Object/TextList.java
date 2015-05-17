package Object;

import game.Schooled;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TextList {

	// Used for a list of text like menus or options
	// **find a way to get a more accurately placed background**
	
	/* Variables */
	
	private int x;
	private int y;
	private int size;
	private int width; // Size of textList block
	private int space;
	private ArrayList<Text> textList;
	private Color color;
	private Image background;
	
	/* Constructor */
	
	public TextList(int x, int y, int size, int width, int space, Color color) {
		this.textList = new ArrayList<Text>();
		this.x = x;
		this.y = y;
		this.size = size;
		this.width = width;
		this.space = space;
		this.color = color;
	}
	
	/* Methods */
	
	// Adds a new text to the list
	public void addText(String text) throws SlickException {
		
		Text textLine = new Text(size, text.substring(0,text.length()).trim(), Schooled.COLOR);
		textLine.setLocation(x, y + (45 * textList.size()));
		
		textList.add(textLine);
	}
	
	public void left() throws SlickException {	
		for (int i = 0; i < textList.size(); i++) {
			textList.get(i).setX(x);
		}
	}
	
	public void center() throws SlickException {
		for (int i = 0; i < textList.size(); i++) {
			textList.get(i).setX(x + width - (width / 2) - (textList.get(i).getWidth() / 2));
		}
	}
	
	public void right() throws SlickException {
		for (int i = 0; i < textList.size(); i++) {
			textList.get(i).setX(x + width - textList.get(i).getWidth());
		}
	}
	
	// Used for action history
	public void pushAndPop(String text, int max) throws SlickException {
		Text action = new Text(size, text, color);
		textList.add(action);
		
		if (textList.size() > max) {
			textList.remove(0);
		}
	}
	
	// Draws paragraph
	public void drawTextList() throws SlickException {
		if (background != null) {
			background.draw(x,y,getWidth(),size*textList.size());
		}
		
		Iterator<Text> iterate = textList.iterator();
		while (iterate.hasNext()) {
			iterate.next().drawString();
		}
	}
	
	/* SET methods */
	
	public void setBackground(String url) throws SlickException {
		this.background = new Image(url);
	}
	
	/* GET methods */
	
	public int getWidth() {
		return this.width;
	}
	
	public Text getTextLine(int i) {
		return textList.get(i);
	}
}