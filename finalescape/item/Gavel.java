package finalescape.item;

import finalescape.mapcomponent.GavelProjectile;

public class Gavel extends ProjectileItem {
	public Gavel() {
		super("Gavel");
		setProjectile(new GavelProjectile(this));
	}
}
