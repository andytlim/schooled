package Object;

import java.util.ArrayList;

import org.newdawn.slick.Animation;

public class Character {

	/* Variables */
	
	private String name;
	private String esp;
	private ArrayList<Skill> skills;
	private ArrayList<Available> available;
	private ArrayList<Animation> animations;
	private int maxHealth;
	private int health;
	private int attack;
	private int defense;
	private int accuracy;
	private int totalSkills = 0;
	
	/* Constructors */
	
	public Character() {
		skills = new ArrayList<Skill>();
		available = new ArrayList<Available>();
		animations = new ArrayList<Animation>();
	}
	
	/* SET methods */
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	/* GET methods */
	
	public String getName() {
		return this.name;
	}
	
	public String getEsp() {
		return this.esp;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getAttack() {
		return this.attack;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public int getAccuracy() {
		return this.accuracy;
	}
	
	public boolean getAvailable(int index) {
		return this.available.get(index).getAvailable();
	}
	
	public int getTotalSkills() {
		return this.totalSkills;
	}
	
	/* Skills */
	
	public ArrayList<Skill> getSkills() {
		return this.skills;
	}
	
	public Skill getSkill(int index) {
		return this.skills.get(index);
	}
	
	public void addSkill(Skill skill) {
		this.skills.add(skill);
		this.available.add(new Available());
	}
	
	public void enableSkill(int index) {
		if (!getAvailable(index))
			totalSkills++;
		this.available.get(index).setAvailable(true);
	}
	
	public void disableSkill(int index) {
		this.available.get(index).setAvailable(false);
		totalSkills--;
	}
	
	/* Animations */
	
	public void addAnimation(Animation animation) {
		animations.add(animation);
	}
	
	public Animation animateStanding() {
		return animations.get(0);
	}
	
	public Animation animateDead() {
		return animations.get(1);
	}
}
