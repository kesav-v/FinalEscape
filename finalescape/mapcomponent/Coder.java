package finalescape.mapcomponent;

import finalescape.map.Map;

import java.awt.Color;

import java.awt.image.BufferedImage;

/**
 * The main {@link Character} of the {@link Map}, the {@code Coder} is controlled
 * by the player.
 *
 * The goal of the {@code Coder} is to bring the {@link finalescape.item.Laptop}
 * to the {@link Desk}.
 *
 * @author Ofek Gila
 * @see Character
 */
public class Coder extends Character {

	private int helicopterMoves;
	private BufferedImage helicopterImage;
	private MapComponent tempComponent1;
	private MapComponent tempComponent2;

	public Coder(Map map, int x, int y) {
		super(map, x, y, "Coder", 5);
		setColor(Color.BLUE);
		helicopterMoves = -2;
		helicopterImage = null;
		tempComponent1 = null;
		getInventory().add(new finalescape.item.Gavel());
	}

	@Override
	public void tick() {
		if (helicopterMoves >= 0)
			helicopterMoves--;
		else if (helicopterMoves == -1)
			unbecomeHelicopter();
		super.tick();
	}

	/**
	 * Checks if can move to coordinate, picking up any {@link finalescape.item.Item}s
	 * from any {@link ItemComponent}s it steps on.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if can move here, false otherwise
	 */
	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		Inventory inventory = getInventory();

		if (componentThere == null)
			return true;
		else if (componentThere instanceof ItemComponent) {
			if (inventory.isFull() && componentThere.getName().equals("Laptop"))
				inventory.set(inventory.getSelectedItemIndex(),
					((ItemComponent)componentThere).getItem());
			return true;
		} else if (componentThere instanceof Helicopter) {
			becomeHelicopter();
			getMap().removeComponent(x, y, true);
			return true;
		} else if (helicopterMoves >= 0) {
			tempComponent1 = componentThere;
			getMap().removeComponent(componentThere);
			if (getMap().get(x, y) == tempComponent1) {
				tempComponent1 = null;
				return false;
			} else return true;
		} else return !componentThere.isSolid();
	}

	private void becomeHelicopter() {
		helicopterMoves = 10;
		setInvincible(true);
	}

	private void unbecomeHelicopter() {
		helicopterMoves--;
		setInvincible(false);
	}

	@Override
	public void moveTo(int x, int y) {
		getMap().moveComponent(getX(), getY(), x, y);
		if (tempComponent2 != null) {
			getMap().addComponent(tempComponent2, getX(), getY());
			tempComponent2 = null;
		}
		if (tempComponent1 != null) {
			tempComponent2 = tempComponent1;
			tempComponent1 = null;
		}
		setX(x);
		setY(y);
	}

	@Override
	public BufferedImage getImage(int size) {
		if (helicopterMoves < 0)
			return super.getImage(size);
		else if (helicopterImage == null)
			helicopterImage = getImageByName("Helicopter", size);
		return helicopterImage;
	}

	/**
	 * When the {@code Coder} is destroyed, the game is lost.
	 */
	@Override
	public void destroy() {
		if (!isInvincible())
			getMap().loseGame();
	}
}