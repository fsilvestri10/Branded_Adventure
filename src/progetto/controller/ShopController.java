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
		    if (!shop.buy(index, player)) panel.drawNoGoldLabel();
		});
		panel.setOnExit(() -> {
            this.onExit.run();
        });
	}
}
