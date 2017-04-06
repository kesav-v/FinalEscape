package finalescape.mapcomponent;

import finalescape.util.Direction;
import finalescape.item.Packet;

public class Kavita extends Teacher {

	private int tickCount;

	public Kavita() {
		super("Kavita", new Packet(), 0.9);
		tickCount = 0;
	}

	@Override
	public void tick() {
		Character target = getTarget();
		if (getInventory().getSelectedItem() instanceof Packet
			&& target != null) {
			if (target.getX() == getX() && !getInventory().isFull())
				if (target.getY() > getY())
					setDirection(Direction.getDir(0, 1));
				else setDirection(Direction.getDir(0, -1));
			else if (target.getY() == getY())
				if (target.getX() > getX() && !getInventory().isFull())
					setDirection(Direction.getDir(1, 0));
				else setDirection(Direction.getDir(-1, 0));
			else {
				moveRandomly();
				return;
			}
			getInventory().useSelectedItem(this);
		} else moveRandomly();
		tickCount++;
		if (tickCount % 10 == 0)
			getInventory().add(new Packet());
	}
}