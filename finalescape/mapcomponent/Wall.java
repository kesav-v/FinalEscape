package finalescape.mapcomponent;

import finalescape.map.Map;

import java.awt.Color;

/**
 * A very basic {@link MapComponent} that just stays put.
 *
 * @author Ofek Gila
 */
public class Wall extends MapComponent {
	public Wall(Map map, int x, int y) {
		super(map, x, y, "Wall");
		setSolid(true);
		setOpaque(true);
		setColor(Color.BLACK);
	}
}