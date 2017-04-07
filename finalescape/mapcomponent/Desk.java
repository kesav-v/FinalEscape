package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;

import java.awt.Color;

/**
 * The {@link Coder}'s final destination in his quest. The {@code Coder} must bring
 * the {@link finalescape.item.Laptop} here.
 *
 * @author Ofek Gila
 * @see finalescape.item.Laptop
 * @see Coder
 */
public class Desk extends MapComponent {

	/**
	 * Since {@code Desk}s are only spawned on the edges of the {@link Map}, the
	 * constructor orients the {@code Desk} to face outwards.
	 * @param  map  the {@link Map} to place in
	 * @param  x    x coordinate
	 * @param  y    y coordinate
	 */
	public Desk(Map map, int x, int y) {
		super(map, x, y, "Desk");
		setColor(new Color(139, 69, 19));
		if (x == 0)
			setDirection(Direction.WEST);
		else if (x == map.size() - 1)
			setDirection(Direction.EAST);
		else if (y == map.size() - 1)
			setDirection(Direction.SOUTH);
		else setDirection(Direction.NORTH);
		setSolid(true);
		setOpaque(true);
	}
}