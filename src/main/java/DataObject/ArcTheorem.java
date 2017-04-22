package DataObject;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Object.Character;
import Object.Skill;

public class ArcTheorem extends Character{

	public ArcTheorem() {
		super();
		
		/* Stats */
		setName("Arc Theorem");
		setMaxHealth(500);
		setAttack(5);
		setDefense(0);
		setAccuracy(95);
		
		/* Skills */
		Skill vector = new Skill("Vector", 10, 0, 1000, null);
		Skill powerSet = new Skill("Power Set", 15, 0, 3000, null);
		Skill dimensionBoost = new Skill("Dimension Boost", 10, 5, 10000, null);
		Skill defend = new Skill("Defend", 0, 20, 10000, null);
		addSkill(vector);
		addSkill(powerSet);
		addSkill(dimensionBoost);
		addSkill(defend);
		
	}
	
	public void loadAnimations(boolean left) {
		/* Animations */
		Animation standing = new Animation();
		Animation dead = new Animation();
		if (!left) {
			try {
				standing.addFrame(new Image("images/tango2.png").getSubImage(0, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tango2.png").getSubImage(50, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tango2.png").getSubImage(100, 0, 52, 67), 500);
				addAnimation(standing);
				dead.addFrame(new Image("images/tango2.png").getSubImage(0,  200, 104, 30), 500);
				addAnimation(dead);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				standing.addFrame(new Image("images/tango3.png").getSubImage(420, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tango3.png").getSubImage(370, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tango3.png").getSubImage(320, 0, 52, 67), 500);
				addAnimation(standing);
				dead.addFrame(new Image("images/tango2.png").getSubImage(0, 200, 104, 30), 500);
				addAnimation(dead);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
