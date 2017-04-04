package finalescape.mapcomponent;

import finalescape.util.Direction;

import java.awt.Color;

public class Failure extends Teacher {

	private int elapsedTicks;

	public Failure() {
		super("Failure", null, 0);
		setDelayInterval(15);
		elapsedTicks = 0;
	}

	@Override
	public void tick() {
		tryMovingInDir(solveMazeDirection(getTarget()));
		elapsedTicks++;
		if (elapsedTicks % 1000 == 0 && getDelayInterval() > 5)
			setDelayInterval(getDelayInterval() - 1);
	}

	@Override
	public void destroy() {}
}
