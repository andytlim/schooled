package Object;

import java.awt.Color;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Text {
	
	/* Variables */
	
	private UnicodeFont uFont;
	private int x;
	private int y;
	private int size;
	private String fontPath;
	private String text;
	private Color color;
	private Image background;
	
	/* Constructor */
	
	public Text() {
		
	}
	
	public Text(int size, String text, Color color) throws SlickException {
		this.size = size;
		this.text = text;
		this.color = color;
		this.fontPath = "images/font2.ttf";
		
		
		uFont = new UnicodeFont(fontPath, size, false, false);
		uFont.addAsciiGlyphs();
		uFont.addGlyphs(400,600);
		uFont.getEffects().add(new ColorEffect(color));
		uFont.loadGlyphs();
	}
	
	/* SET methods */
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setSize(int size) throws SlickException {
		getFont(size, color);
	}
	
	public void setFont(String url) throws SlickException {
		this.fontPath = url;
		getFont(size,color);
	}
	
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public void setText(String text) throws SlickException {
		this.text = text;
		getFont(size, color);
	}
	
	public void setColor(Color color) throws SlickException {
		getFont(size, color);
	}
	
	public void setBackground(String url) throws SlickException {
		this.background = new Image(url);
	}
	
	/* GET methods */
	
	public String getText() {
		return this.text;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getHeight() {
		return uFont.getHeight(text);
	}
	
	public int getWidth() {
		return uFont.getWidth(text);
	}
	
	@SuppressWarnings("unchecked")
	public void getFont(int size, Color color) throws SlickException {
		uFont.getGlyphPages().set(0, new ColorEffect(color));
	}
	
	public void drawString() throws SlickException {
		if (background != null)
			background.draw(x,y,getWidth(),getHeight());
		uFont.drawString(this.x, this.y, this.text);
	}
	
	public void drawString(int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		if (background != null)
			background.draw(this.x,this.y,getWidth(),getHeight());
		uFont.drawString(this.x, this.y, this.text);
	}
	
	public void destroy() {
		uFont.destroy();
	}
}
