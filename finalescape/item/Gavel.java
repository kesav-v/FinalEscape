package finalescape.item;

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.GavelProjectile;
import finalescape.mapcomponent.Teacher;

public class Gavel extends ProjectileItem {
	public Gavel() {
		super("Gavel");
		setProjectile(new GavelProjectile(this));
	}

	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component.isSolid() && component instanceof Teacher)
			GavelProjectile.stunAllOfType(component.getMap(), component);
		return false;
	}
}
