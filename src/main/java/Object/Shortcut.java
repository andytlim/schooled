package Object;

import org.newdawn.slick.Image;

public class Shortcut {
	
	/* Variables */
	
	private Image icon;
	private String description;
	private int key;
	private Skill skill;
	
	/* Constructor */
	
	public Shortcut(Image icon, String description, int key) {
		this.icon = icon;
		this.description = description;
		this.key = key;
	}
	
	public Shortcut(Image icon, Skill skill, int key) {
		this.icon = icon;
		this.skill = skill;
		this.key = key;
	}
	
	/* GET methods */
	
	public Image getImage() {
		return this.icon;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public Skill getSkill(){
		return this.skill;
	}
}
