package finalescape.item;

/**
 * The main objective in the game. The {@link Coder} needs to get this laptop
 * to the {@link Desk}.
 */

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.Character;
import finalescape.mapcomponent.Desk;

import java.awt.Color;

public class Laptop extends Item {

	private Character character;

	/**
	 * Initializes the {@code Laptop}
	 */
	public Laptop() {
		super("Laptop");
		setColor(new Color(212, 175, 55)); // gold
		setPrecedence(Integer.MAX_VALUE);
	}

	@Override
	public boolean onUse(Character character) {
		this.character = character;
		return placeIfPossible(character);
	}

	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component instanceof Desk)
			character.getMap().winGame();
		return false;
	}
}