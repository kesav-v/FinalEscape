package finalescape.item;

/**
 * A very generic {@link ProjectileItem} of a frisbee.
 *
 * @author Ofek Gila
 */

import finalescape.mapcomponent.FrisbeeProjectile;

public class Frisbee extends ProjectileItem {

	/**
	 * Initializes this component.
	 */
	public Frisbee() {
		super("Frisbee");
		setProjectile(new FrisbeeProjectile(this));
		setPrecedence(5);
	}
}