import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.GridLayout;

public class InventoryPanel extends JPanel {

	private Inventory inventory;
	private ItemPanel[] itemPanels;

	public InventoryPanel(Inventory inventory) {
		this.inventory = inventory;
		itemPanels = new ItemPanel[inventory.capacity()];
		setLayout(null);
	}

	public void initializeItemPanels() {
		for (int i = 0; i < itemPanels.length; i++) {
			itemPanels[i] = new ItemPanel(inventory, i);
			itemPanels[i].setSize(getHeight(), getHeight());
			itemPanels[i].setLocation(getHeight() * i, 0);
			add(itemPanels[i]);
		}
	}
}