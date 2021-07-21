public class ShopItem {
	String name;
	int price;
	String description;
	boolean playerBought = false;
	
	int[] buffs;
		
	public void applyBuffs() {
		Interaction.health += buffs[0];
		Interaction.attack += buffs[1];
		Interaction.defense += buffs[2];
		Interaction.heal += buffs[3];
	
	}
}
