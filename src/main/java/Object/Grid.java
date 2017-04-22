package Object;

import game.Schooled;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Grid {

	/* Variables */
	
	private String FREEPOSITION;
	private String TAKENPOSITION;
	public Image[][] grid;
	private int currPosX;
	private int currPosY;
	
	/* Constructor */
	
	public Grid(String free, String taken) {
		FREEPOSITION = free;
		TAKENPOSITION = taken;
		this.grid = new Image[3][3];
		this.currPosX = 1;
		this.currPosY = 1;
	}
	
	/* Methods */
	
	public void loadGrid() throws SlickException {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = Schooled.loadImage(FREEPOSITION);
			}
		}
		initializeCurrentPosition();
	}
	
	// Initiates current position
	public void initializeCurrentPosition() throws SlickException {
		grid[currPosX][currPosY] = Schooled.loadImage(TAKENPOSITION);
	}
	
	// Draws grid
	public void drawGrid(int baseX, int baseY) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j].draw(baseX + x, baseY + y, 100,100);
				y += 110;
			}
			y = 0;
			x += 110;
		}
	}
	
	public void moveUp() throws SlickException {
		if (currPosY > 0) {
			grid[currPosX][currPosY] = Schooled.loadImage(FREEPOSITION);
			currPosY--;
			grid[currPosX][currPosY] = Schooled.loadImage(TAKENPOSITION);
		}
	}
	
	public void moveDown() throws SlickException {
		if (currPosY < 2) {
			grid[currPosX][currPosY] = Schooled.loadImage(FREEPOSITION);
			currPosY++;
			grid[currPosX][currPosY] = Schooled.loadImage(TAKENPOSITION);
		}
	}
	
	public void moveLeft() throws SlickException {
		if (currPosX > 0) {
			grid[currPosX][currPosY] = Schooled.loadImage(FREEPOSITION);
			currPosX--;
			grid[currPosX][currPosY] = Schooled.loadImage(TAKENPOSITION);
		}
	}
	
	public void moveRight() throws SlickException {
		if (currPosX < 2) {
			grid[currPosX][currPosY] = Schooled.loadImage(FREEPOSITION);
			currPosX++;
			grid[currPosX][currPosY] = Schooled.loadImage(TAKENPOSITION);
		}
	}
	
	/* GET methods */
	
	public int getCurrPosX(){
		return currPosX;
	}
	
	public int getCurrPosY(){
		return currPosY;
	}
	
	public int getCurrBlock(){
		return  getCurrPosX() * 3 + getCurrPosY();
	}
}