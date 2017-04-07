package finalescape.mapcomponent;

import finalescape.util.Direction;

import java.awt.Color;

/**
 * A {@link Teacher} that chases down the main {@link Character} from any side
 * of the {@link finalescape.map.Map}. While {@code Failure} cannot be destroyed,
 * it can be stunned for a period of time.
 *
 * {@code Failure} speeds up over time.
 *
 * {@code Failure}'s worst enemy, is of course homework {@link finalescape.item.Packet}'s,
 * and the corresponding {@link PacketProjectile}s.
 *
 * @author Ofek Gila
 * @see finalescape.item.Packet#canPlaceOn
 * @see PacketProjectile#canMoveHere
 */
public class Failure extends Teacher {

	private int elapsedTicks;

	public Failure() {
		super("Failure", null, 0, 0);
		setDelayInterval(15);
		elapsedTicks = 0;
	}

	/**
	 * Tries chasing the main {@link Character}, by following the shortest path
	 * to him.
	 */
	@Override
	public void tick() {
		tryMovingInDir(solveMazeDirection(getTarget()));
		elapsedTicks++;
		if (elapsedTicks % 750 == 0 && getDelayInterval() > 3)
			setDelayInterval(getDelayInterval() - 1);
	}

	/**
	 * Instead of removing {@code Failure} from the map, it is only stunned
	 * temporarily.
	 */
	@Override
	public void destroy() {
		preventUpdate(5);
	}
}
