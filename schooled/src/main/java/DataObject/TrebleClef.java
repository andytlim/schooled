package DataObject;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Object.Character;
import Object.Skill;

public class TrebleClef extends Character{

	public TrebleClef() {
		super();
		
		/* Stats */
		setName("Treble Clef");
		setMaxHealth(500);
		setAttack(5);
		setDefense(0);
		setAccuracy(95);
		
		/* Skills */
		Skill chord = new Skill("Chord", 10, 0, 1000, null);
		Skill arpeggio = new Skill("Arpeggio", 15, 0, 3000, null);
		Skill rest = new Skill("Rest", 10, 5, 10000, null);
		Skill defend = new Skill("Defend", 0, 20, 10000, null);
		addSkill(chord);
		addSkill(arpeggio);
		addSkill(rest);
		addSkill(defend);
	}
	
	public void loadAnimations(boolean left) {
		/* Animations */
		Animation standing = new Animation();
		if (!left) {
			try {
				standing.addFrame(new Image("images/tio2.png").getSubImage(0, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tio2.png").getSubImage(52, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tio2.png").getSubImage(110, 0, 52, 67), 500);
				addAnimation(standing);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				standing.addFrame(new Image("images/tio3.png").getSubImage(273, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tio3.png").getSubImage(221, 0, 52, 67), 500);
				standing.addFrame(new Image("images/tio3.png").getSubImage(162, 0, 52, 67), 500);
				addAnimation(standing);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
