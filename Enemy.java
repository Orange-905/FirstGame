public class Enemy {
	String name;
	
	// enemy stats 
	int health;
	int attack;
	int defense;
	int heal;
	
	String attackText;
	String graphics;
	
	public void showEnemyStats() {
		System.out.println(String.format("Health: %s\nAttack: %s\nDefense: %s\nHeal: %s\n", health, attack, defense, heal));
	}
}
