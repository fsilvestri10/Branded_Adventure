package progetto.controller;

import progetto.model.Player;
import progetto.model.Shop;
import progetto.view.ShopPanel;

public class ShopController {
	
	private Player player;
	private Shop shop;
	private ShopPanel panel;
	private Runnable onExit;
	
	public ShopController(ShopPanel panel, Player player, Shop shop, Runnable onExit) {
		this.player = player;
		this.panel = panel;
		this.shop = shop;
		this.onExit = onExit;
		
		panel.setOnBuy(index -> {
            if (this.shop.getItem(index) != this.player.getEquipment(index)) {
            	if(this.player.getGold() >= this.shop.getItem(index).getCost()) {
            		this.player.changeEquipment(index, shop.getItem(index));
            		this.player.giveGold(-this.shop.getItem(index).getCost());
            	} else {
            		this.panel.drawNoGoldLabel();
            	}
            }
        });
		panel.setOnExit(() -> {
            this.onExit.run();
        });
	}
}
