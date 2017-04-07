package finalescape.item;

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.Character;
import finalescape.mapcomponent.Desk;

import java.awt.Color;

/**
 * The main objective in the game. The {@link finalescape.mapcomponent.Coder}
 * needs to get this laptop to the {@link Desk}. The main {@link Item} of
 * {@link finalescape.mapcomponent.David}.
 *
 * @author Ofek Gila
 * @see finalescape.mapcomponent.David
 * @see finalescape.mapcomponent.Coder
 */
public class Laptop extends Item {

	private Character character;

	/**
	 * Initializes the {@code Laptop}.
	 */
	public Laptop() {
		super("Laptop");
		setColor(new Color(212, 175, 55)); // gold
		setPrecedence(Integer.MAX_VALUE);
	}

	/**
	 * Tries placing {@code Laptop} when used.
	 * @param  character the {@link Character} who has the {@code Laptop}
	 * @return           true if used and should be removed from {@link finalescape.mapcomponent.Inventory}, false otherwise.
	 */
	@Override
	public boolean onUse(Character character) {
		this.character = character;
		return placeIfPossible(character);
	}

	/**
	 * Returns true if can be placed on a specific {@link MapComponent}, false
	 * otherwise. Cannot be placed on solid components, and if placed on the
	 * {@link Desk}, win the game.
	 * @param  component the {@link MapComponent} to place in
	 * @return           whether or not can be placed on component
	 */
	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component instanceof Desk)
			character.getMap().winGame();
		return false;
	}
}