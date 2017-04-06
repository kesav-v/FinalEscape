package finalescape.item;

import finalescape.mapcomponent.FrisbeeProjectile;

/**
 * A very generic {@link ProjectileItem} of a frisbee.
 *
 * @author Ofek Gila
 * @see FrisbeeProjectile
 */
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