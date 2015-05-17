package Object;

public class Skill {

	/* Variables */
	//what is duration used for?
	
	private String name;
	private String description;
	private String difficulty;
	private int attack;
	private int defense;
	private int accuracy;
	private int cooldown; 
	private int[] blocksAffected;
	
	/* Constructor */
	
	public Skill(String name, int attack, int defense, int cooldown, int[] blocksAffected) {
		this.name = name;
		this.attack = attack;
		this.defense = defense;
		this.cooldown = cooldown;
		this.blocksAffected = blocksAffected;
	}
	
	/* SET methods */
	
	public void setBlocksAffected(int[] blocks){
		this.blocksAffected = blocks;
	}
	
	/* GET methods */
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getDifficulty(int difficulty) {
		switch (difficulty) {
			case 1: return "Beginner";
			case 2: return "Intermediate";
			default: return "Advanced";
		}
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
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public int[] getBlocksAffected(){
		return this.blocksAffected;
	}
}
