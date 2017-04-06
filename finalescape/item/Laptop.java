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