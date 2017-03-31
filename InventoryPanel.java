import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.GridLayout;

public class InventoryPanel extends JPanel {

	private Inventory inventory;
	private ItemPanel[] itemPanels;

	public InventoryPanel(Inventory inventory) {
		this.inventory = inventory;
		itemPanels = new ItemPanel[inventory.capacity()];
		setLayout(new GridLayout(0, itemPanels.length));
		for (int i = 0; i < itemPanels.length; i++)
			add(itemPanels[i] = new ItemPanel(inventory, i));
	}

	@Override
	public void paintComponent(Graphics g) {
		for (ItemPanel itemPanel : itemPanels)
			itemPanel.repaint();
	}
}