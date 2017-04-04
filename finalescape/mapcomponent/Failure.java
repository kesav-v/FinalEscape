package finalescape.mapcomponent;

import finalescape.util.Direction;

import java.awt.Color;

public class Failure extends Teacher {

	public Failure() {
		super("Failure", null, 0);
	}

	@Override
	public void tick() {
		tryMovingInDir(solveMazeDirection(getTarget()));
	}

	@Override
	public void destroy() {}
}
