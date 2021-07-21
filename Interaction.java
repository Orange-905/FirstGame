import java.util.Scanner;
import java.util.ArrayList;

public class Interaction {
	static Scanner scanner = new Scanner(System.in);
	
	// class variables 
	public static int health = 20;
	public static int attack = 5;
	public static int defense = 5;
	public static int heal = 5;
	
	public static String userInput;
	
	public static void showPlayerStats() {
		System.out.println(String.format("Health: %s\nAttack: %s\nDefense: %s\nHeal: %s\n", health, attack, defense, heal));
	}
	
	public static void waitForEnter() {
		do {
			userInput = scanner.nextLine();
		} while(!userInput.equals(""));
	}
	
	
	public static void main(String[] args) {
		String separator = "----------------------------------------";
		String enterText = (" (press enter to continue)");
		
		String controls = "Make money by typing 'mm'\nview your balance by typing 'bal'\nview the shop"
		+ " by typing 'shop'\nbuy items by typing 'buy'\nview your inventory by typing 'inv'\nview your "
		+ "stats by typing 'stats'\nview these controls by typing 'controls'\nbattle enemies by typing "
		+ "'battle'\nand quit the game by typing 'quit'\n";
		
		String[] intros = {"The goal of the game is to defeat all enemies, using items and upgrades from the shop to help you.\n" + enterText, 
		controls + enterText, 
		"Good luck, young adventurer!"};
		
		String[] stats = {"Health", "Attack", "Defense", "Heal"};
		
		int bal = 0;
		
		// shop items 
		ArrayList<ShopItem> items = new ArrayList<ShopItem>();
		
		ShopItem item1 = new ShopItem();
		item1.name = "Prismatic Armor";
		item1.price = 80;
		item1.buffs = new int[] {5, 0, 15, 5};
		item1.description = "";
		items.add(item1);
		
		ShopItem item2 = new ShopItem();
		item2.name = "Shining Blade";
		item2.price = 100;
		item2.buffs = new int[] {0, 20, 0, 0};
		item2.description = "";
		items.add(item2);
		
		ShopItem item3 = new ShopItem();
		item3.name = "Berserker Boots";
		item3.price = 50;
		item3.buffs = new int[] {5, 0, 5, 0};
		item3.description = "";
		items.add(item3);
		
		ShopItem item4 = new ShopItem();
		item4.name = "Regen Potion";
		item4.price = 30;
		item4.buffs = new int[] {0, 0, 0, 15};
		item4.description = "";
		items.add(item4);
		
		ShopItem item5 = new ShopItem();
		item5.name = "Gold Booster";
		item5.price = 20;
		item5.buffs = new int[] {0, 0, 0, 0};
		item5.description = "Boost your gold production by 5x";
		items.add(item5);
		boolean goldBoosterOn = false;
		int goldIncrement;
		
		// enemies 
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		
		Enemy enemy0 = new Enemy(); // for arrayList purposes only  
		enemies.add(enemy0);
		
		Enemy enemy1 = new Enemy();
		enemy1.name = "Tumble Weedicator";
		enemy1.health = 20;
		enemy1.attack = 9;
		enemy1.defense = 4;
		enemy1.heal = 2; 
		enemy1.attackText = "The Tumble Weedicator has thrown a tumbling tumbleweed at you!";
		enemy1.graphics = " <O.O> ";
		enemies.add(enemy1);
		
		Enemy enemy2 = new Enemy();
		enemy2.name = "Avalauncher";
		enemy2.health = 50;
		enemy2.attack = 35;
		enemy2.defense = 10;
		enemy2.heal = 8;
		enemy2.attackText = "The Avalauncher has launched an avalanche at you!";
		enemy2.graphics = " \\_(0>0)_/ ";
		enemies.add(enemy2);
		
		Enemy enemy3 = new Enemy();
		enemy3.name = "Holla Bottler";
		enemy3.health = 70;
		enemy3.attack = 80;
		enemy3.defense = 12;
		enemy3.heal = 10;
		enemy3.attackText = "The Holla Bottler has hurled a bottle of venom at you! ";
		enemy3.graphics = " X/-[o}v{o]-\\X ";
		enemies.add(enemy3);
		
		// levels 
		ArrayList<Level> levels = new ArrayList<Level>();
		
		Level level0 = new Level(); // level 0 does not show up in game; this is for level 1 to be available at the start 
		level0.playerCompleted = true;
		levels.add(level0);
		
		Level level1 = new Level();
		level1.difficulty = "Easy";
		levels.add(level1);
		
		Level level2 = new Level();
		level2.difficulty = "Medium";
		levels.add(level2);
		
		Level level3 = new Level();
		level3.difficulty = "Hard";
		levels.add(level3);
		
		// introductory text 
		System.out.println("Welcome to the Cool RPG!" + enterText);
		
		for (int i = 0; i < 3; i++) {
			waitForEnter();
			System.out.println(intros[i]);
		}
		
		boolean quitGame = false;
		
		// interaction with user 
		while (!quitGame) {
			userInput = scanner.nextLine().toLowerCase();
			switch (userInput) {
			case "mm":
				goldIncrement = (goldBoosterOn) ? 5 : 1;
				bal += goldIncrement;
				System.out.println(String.format("Made +%d gold!", goldIncrement));
				break;
			case "bal":
			case "balance":
				System.out.println(String.format("Balance: %s gold", bal));
				break;
			case "shop":
				System.out.println("SHOP");
				for (int i = 0; i < items.size(); i++) {
					String boughtStem = (items.get(i).playerBought) ? (" [BOUGHT]") : (" [NOT BOUGHT]");
					String statsStem = "";
					for (int j = 0; j < items.get(i).buffs.length; j++) {
						if (items.get(i).buffs[j] != 0) statsStem += String.format(" +%s %s", items.get(i).buffs[j], stats[j]);
					}
					System.out.println(String.format("(%d) %s: %d gold (%s%s)%s", i + 1, items.get(i).name, items.get(i).price, statsStem.strip(), items.get(i).description, boughtStem));
				}
				break;
			case "buy":
				System.out.println("What would you like to buy? Type the number of the item or upgrade in the shop. Type 0 to exit.");
				String strBuyRequest = scanner.nextLine();
				try {
					int buyRequest = Integer.parseInt(strBuyRequest) - 1;
					if (buyRequest >= 0 && buyRequest < items.size()) {
						if (items.get(buyRequest).playerBought) {
							System.out.println("You bought this already! Type 'buy' to buy something else.");
						}
						else {
							if (bal >= items.get(buyRequest).price) {
								bal -= items.get(buyRequest).price;
								items.get(buyRequest).playerBought = true;
								items.get(buyRequest).applyBuffs();
								if (buyRequest == 4) goldBoosterOn = true;
								System.out.println(String.format("You have bought %s!", items.get(buyRequest).name));
							}
							else {
								System.out.println(String.format("You don't have enough money! You are %d gold short. Type 'mm' to make more money.", items.get(buyRequest).price - bal));
							}
						}
					}
					else if (buyRequest == -1) {
						break;
					}
					else {
						System.out.println("Number out of range! Type 'buy' to try again.");
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Your input must be an integer! Type 'buy' to try again.");
				}
				break;
			case "stats":
				System.out.println("STATS");
				showPlayerStats();
				break;
			case "inv":
			case "inventory":
				System.out.println("INVENTORY");
				int numItemsBought = 0;
				for (int i = 0; i < items.size(); i++) {
					if (items.get(i).playerBought) {
						String statsStem = "";
						for (int j = 0; j < items.get(i).buffs.length; j++) {
							if (items.get(i).buffs[j] != 0) statsStem += String.format(" +%s %s", items.get(i).buffs[j], stats[j]);
						}
						System.out.println(String.format("%s (%s%s)", items.get(i).name, statsStem.strip(), items.get(i).description));
						numItemsBought++;
					}
				}
				if (numItemsBought == 0) System.out.println("Your inventory is empty! Buy items and upgrades in the shop.");
				break;
			case "controls":
				System.out.println(controls);
				break;
			case "battle":
				System.out.println("Choose a level: ");
				for (int i = 1; i < levels.size(); i++) {
					if (levels.get(i-1).playerCompleted) System.out.println(String.format("Level %d (%s)", i, levels.get(i).difficulty));
				}
				String strLevelChoice = scanner.nextLine();
				try {
					int levelChoice = Integer.parseInt(strLevelChoice);
					if (levelChoice >= 1 && levelChoice < levels.size()) {
						if (levels.get(levelChoice - 1).playerCompleted) {
							System.out.println(String.format("\nYou have chosen Level %s!", levelChoice));
							System.out.println(String.format("Your enemy is the mighty %s!", enemies.get(levelChoice).name));
							System.out.println(enemies.get(levelChoice).graphics);
							
							System.out.println("\nYou: ");
							showPlayerStats();
							System.out.println(String.format("%s (Enemy):", enemies.get(levelChoice).name));
							enemies.get(levelChoice).showEnemyStats();
							
							System.out.println("Proceed to battle? (y/n)");
							String battleConfirmation;
							boolean exitBattle = false;
							while (!exitBattle) {
								battleConfirmation = scanner.nextLine();
								switch (battleConfirmation) {
								case "y":
									System.out.println(String.format("You have chosen to battle the %s!", enemies.get(levelChoice).name));
									System.out.println(enemies.get(levelChoice).graphics);
									System.out.println(separator);
									System.out.println("BATTLE");
									
									int currentHealth = health;
									int currentEnemyHealth = enemies.get(levelChoice).health;
									
									int damageToEnemy = (int) (attack * Math.pow(0.934, enemies.get(levelChoice).defense)); 
									// exponential function: 0% damage reduction with 0 defense, 50% damage reduction with 10 defense 
									int damageToPlayer = (int) (enemies.get(levelChoice).attack * Math.pow(0.934, defense));
									
									int characterTurn = 0; // 0 for player, 1 for enemy 
									while (currentHealth > 0 && currentEnemyHealth > 0) {
										if (characterTurn == 0) {
											System.out.println("It is your turn.");
											System.out.println("Make a move: \n(1) Attack \n(2) Heal ");
											
											boolean moveMade = false;
											String strMoveChoice;
											int moveChoice;
											
											while (!moveMade) {
												strMoveChoice = scanner.nextLine();
												try {
													moveChoice = Integer.parseInt(strMoveChoice);
													switch (moveChoice) {
													case 1:
														System.out.println("You attacked the enemy!");
														System.out.println(String.format("You did %d damage to the %s.", damageToEnemy, enemies.get(levelChoice).name));
														currentEnemyHealth = Math.max(0, currentEnemyHealth - damageToEnemy);
														moveMade = true;
														break;
													case 2:
														System.out.println("You decided to heal yourself!");
														System.out.println(String.format("You gained %d health.", Math.min(health, currentHealth + heal) - currentHealth));
														currentHealth = Math.min(health, currentHealth + heal);
														moveMade = true;
														break;
													default:
														System.out.println("That is not one of the options! Try again.");
														break;
													}
												}
												catch (NumberFormatException e) {
													System.out.println("You must type a number! Try again.");
													System.out.println("Make a move: \n(1) Attack \n(2) Heal ");
												}
											}
											System.out.println(String.format("\nYou: %d/%d HP", currentHealth, health));
											System.out.println(String.format("Enemy: %d/%d HP", currentEnemyHealth, enemies.get(levelChoice).health));

											if (currentEnemyHealth > 0) { // check if enemy is still alive 
												System.out.println(enterText);
												waitForEnter();
												System.out.println(separator);
											}
											else {
												System.out.println(String.format("\nYou have killed the %s!", enemies.get(levelChoice).name));
												int reward = (levels.get(levelChoice).playerCompleted) ? 5 * (int) Math.pow(2, levelChoice) / 2 : 10 * (int) Math.pow(2, levelChoice);
												System.out.println(String.format("You have won %d gold for your valiant effort.", reward));
												bal += reward;
												if (levelChoice == 3 && levels.get(levelChoice).playerCompleted == false) {
													System.out.println("\nCONGRATULATIONS!!");
													System.out.println("You completed all the levels. You have beat the game!");
												}
												levels.get(levelChoice).playerCompleted = true;
												System.out.println("Exiting the battle...");
											}
										} 
										else {
											if (currentEnemyHealth > 0) { // check if enemy is still alive 
												System.out.println(String.format("It is the %s's turn.", enemies.get(levelChoice).name));
												System.out.println(enemies.get(levelChoice).graphics);
												
												if (currentEnemyHealth - damageToEnemy <= 0 && currentEnemyHealth != enemies.get(levelChoice).health && damageToPlayer < currentHealth) { 
													// if enemy would die on next move (if attacked) and enemy cannot one shot the player now, then use heal 
													System.out.println(String.format("The %s decided to heal itself!", enemies.get(levelChoice).name));
													System.out.println(String.format("The %s gained %d health.", enemies.get(levelChoice).name,  Math.min(enemies.get(levelChoice).health, currentEnemyHealth + enemies.get(levelChoice).heal) - currentEnemyHealth));
													currentEnemyHealth = Math.min(enemies.get(levelChoice).health, currentEnemyHealth + enemies.get(levelChoice).heal);
													
													System.out.println(String.format("\nYou: %d/%d HP", currentHealth, health));
													System.out.println(String.format("Enemy: %d/%d HP", currentEnemyHealth, enemies.get(levelChoice).health));
													
													System.out.println(enterText);
													waitForEnter();
													System.out.println(separator);
												}
												else {
													System.out.println(enemies.get(levelChoice).attackText);
													System.out.println(String.format("You lost %d health.", damageToPlayer));
													currentHealth = Math.max(0, currentHealth - damageToPlayer);
													
													System.out.println(String.format("\nYou: %d/%d HP", currentHealth, health));
													System.out.println(String.format("Enemy: %d/%d HP", currentEnemyHealth, enemies.get(levelChoice).health));
													
													if (currentHealth > 0) { // check if player is still alive after enemy attack 
														System.out.println(enterText);
														waitForEnter();
														System.out.println(separator);
													}
													else {
														System.out.println("Oh no! You lost all your health!");
														System.out.println("You have died!");
														
														System.out.println("(press enter)");
														waitForEnter();
														System.out.println(separator);
														
														System.out.println("You lost all your gold but kept all the items and upgrades you have.");
														bal = 0;
														System.out.println("Use the shop to get stronger next time.");
													}
												}
											}
										}
										characterTurn++;
										characterTurn %= 2;
									}
									exitBattle = true; // after battle has ended 
									break;
								case "n":
									System.out.println("Leaving the battle!");
									exitBattle = true;
									break;
								case "":
									break;
								default:
									System.out.println("Invalid option. Type 'y' or 'n'.");
									break;
								}
							}
						}
						else {
							System.out.println("You have not unlocked that level yet! Type 'battle' to try again.");
						}
					}
					else {
						System.out.println("That level does not exist! Type 'battle' to try again. Then, type the number of the level you want.");
					}
				}
				catch (NumberFormatException e) {
					System.out.println("You have to type the number of the level! Type 'battle' to try again.");
				}
				break;
			case "quit":
				System.out.println("Game ended. Thanks for playing!");
				quitGame = true;
				break;
			case "":
				break;
			default:
				System.out.println("Invalid input. Try again.");
				break;
			}
		}
		scanner.close();
	}
}
