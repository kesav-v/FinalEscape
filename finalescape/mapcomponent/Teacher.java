package finalescape.mapcomponent;

import finalescape.item.Item;
import finalescape.item.Textbook;

import java.awt.Color;

/**
 * A {@link Character} that has a specific {@link Item} of choice, typically not
 * attacking nor chasing the {@link Coder} if they have said {@code Item} selected.
 *
 * Teachers chase the {@link Coder} if within their direct line of sight in
 * the 4 {@link finalescape.util.Direction}s, and can destroy the {@link Coder},
 * but this can be overridden.
 *
 * @author Ofek Gila
 * @see Character
 * @see Kavita
 * @see David
 * @see Failure
 */
public class Teacher extends Character {

	private Item itemOfChoice;
	private boolean boss;

	/**
	 * Creates a {@code Teacher} with a specific {@link Item} of choice, and chance
	 * of initially spawning with said {@code Item} in their {@link Inventory} of
	 * a specific size.
	 * @param  name                    name of {@code Teacher}
	 * @param  itemOfChoice            {@link Item} of choice
	 * @param  itemOfChoiceProbability chance of spawning with said {@link Item}
	 * @param  inventorySize           {@link Inventory} capacity
	 */
	public Teacher(String name, Item itemOfChoice, double itemOfChoiceProbability,
		int inventorySize) {
		super(name, inventorySize);
		this.itemOfChoice = itemOfChoice;
		if (Math.random() < itemOfChoiceProbability)
			getInventory().add(itemOfChoice);
		setColor(Color.RED);
		boss = false;
	}

	/**
	 * Creates a {@code Teacher} with a specific {@link Item} of choice, and chance
	 * of initially spawning with said {@code Item} in their {@link Inventory}.
	 * @param  name                    name of {@code Teacher}
	 * @param  itemOfChoice            {@link Item} of choice
	 * @param  itemOfChoiceProbability chance of spawning with said {@link Item}
	 */
	public Teacher(String name, Item itemOfChoice, double itemOfChoiceProbability) {
		this(name, itemOfChoice, itemOfChoiceProbability, 2);
	}

	public Teacher() {
		this("Teacher", new Textbook(), 0.25);
	}

	/**
	 * By default, {@code Teacher}s chase the main {@link Character} if he is
	 * within their direct line of sight.
	 */
	@Override
	public void tick() {
		Character target = getTarget();
		if (target == null) {
			moveRandomly();
			return;
		}
		if (target.getX() == getX())
			if (target.getY() > getY())
				moveCharacterDelta(0, 1);
			else moveCharacterDelta(0, -1);
		else if (target.getY() == getY())
			if (target.getX() > getX())
				moveCharacterDelta(1, 0);
			else moveCharacterDelta(-1, 0);
		else moveRandomly();
	}

	/**
	 * Returns the {@link Character} that this {@code Teacher} is targeting,
	 * typically the {@link Coder}, the main {@code Character}.
	 * @return the targeted {@link Character}
	 */
	public Character getTarget() {
		Character target = getMap().getMainCharacter();
		if (instof(target.getInventory().getSelectedItem(), itemOfChoice))
			return null;
		return target;
	}

	/**
	 * Returns true if {@code Teacher} can move to specific coordinate, false otherwise.
	 * Teachers move normally, but can kill the main {@link Character} if they don't
	 * have this {@code Teacher}'s {@link Item} of choice selected.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if can move here, false otherwise
	 */
	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null)
			return true;
		else if (componentThere == getMap().getMainCharacter()
			&& !(instof(((Character)componentThere).getInventory().getSelectedItem(), itemOfChoice)))
			getMap().getMainCharacter().destroy();
		else if (componentThere instanceof ItemComponent && !getInventory().isFull())
			return true;
		return false;
	}

	/**
	 * Returns this {@code Teacher}'s {@link Item} of choice.
	 * @return this {@code Teacher}'s {@link Item} of choice
	 */
	public Item getItemOfChoice() { return itemOfChoice; }

	/**
	 * Makes this {@code Teacher} become a boss. By default, makes it tick 3 times
	 * as often, but can be overridden.
	 */
	public void becomeBoss(double bossboost) {
		if (boss) return;
		boss = true;
		setDelayInterval((int)(getDelayInterval() / bossboost + 0.5));
	}
}