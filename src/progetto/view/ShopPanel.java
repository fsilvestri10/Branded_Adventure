package progetto.view;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import progetto.model.Item;

public class ShopPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	//private JButton buyItem1 = new JButton();
    //private JButton buyItem2 = new JButton();
    private ArrayList<JButton> buyButtons = new ArrayList<>();
    private JButton exit = new JButton();
    private JLabel noGold = new JLabel("Non hai abbastanza oro per comprare questo oggetto");
	private IntConsumer onBuy;
	private Runnable onExit;
	private ArrayList<Item> items;
	private int playerGold;
	
	public ShopPanel() {
		this.setLayout(null);
        this.exit.addActionListener(e -> {
            if (this.onExit != null) this.onExit.run();
        });
        this.add(exit);
        this.add(noGold);
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        g2.drawImage(Assets.SHOP_BACKGROUND, 0, 0, w, h, null);
        g2.fillRect(120, 140, 750, 450);
        g2.drawImage(Assets.SHOP_FRAME, 50, -20, 900, 700, null);
        g2.drawImage(Assets.WOOD_SIGN, 800, -5, 100, 100, null);
        g2.drawImage(Assets.WOOD_CROSS, 815, 8, 70, 70, null);
        
        drawPlayerGold(g2);
        drawItems(g2);
        
        positionBuyButtons();
        setButton(exit, 800, -5, 100, 100);        
	}
	
	public void drawItems (Graphics g2) {
		g2.setFont(Assets.PIXEL_FONT.deriveFont(32f));
		g2.setColor(Color.BLACK);
		
		int x = 200;
		int y = 220;
		for (int i = 0; i < items.size(); i++) {
			if (i == items.size() / 2) {
				x = 200;
				y = 420;
			}
			g2.drawImage(Assets.itemImage(items.get(i)), x, y, 80, 80, null);			g2.drawImage(Assets.ITEM_FRAME, x - 35, y - 45, 150, 160, null);
			g2.drawImage(Assets.ANCIENT_SCROLL, x - 20, y + 100, 120, 60, null);
			g2.drawImage(Assets.GOLD, x, y + 120, 25, 25, null);
			g2.drawString(String.valueOf(items.get(i).getCost()), x + 30, y + 140);
			x += 165;
		}
	}
	
	private void drawPlayerGold (Graphics2D g2) {
	    g2.setFont(Assets.PIXEL_FONT.deriveFont(48f));
	    g2.setColor(Color.BLACK);

	    g2.drawImage(Assets.BIG_WOOD_SIGN, 30, -10, 150, 200, null);
	    g2.drawImage(Assets.GOLD, 50, 90, 35, 35, null);
	    g2.drawString(String.valueOf(playerGold), 85, 120);
	}
	
	public void drawNoGoldLabel() {
        this.noGold.setBounds(230, 630, 1000, 100);
        this.noGold.setBackground(new Color(0, 0, 0, 128));
        this.noGold.setForeground(Color.WHITE);
        this.noGold.setVisible(true);
        this.noGold.setFont(Assets.PIXEL_FONT.deriveFont(32f));
        Timer timer = new Timer(2000, e -> this.noGold.setVisible(false));
        timer.setRepeats(false);
        timer.start();
	}
	
	public void setOnBuy(IntConsumer onBuy) {
        this.onBuy = onBuy;
    }
	
	public void setOnExit(Runnable onExit) {
		this.onExit = onExit;
	}
	
	private void createBuyButtons() {
	    buyButtons.clear();

	    for (int i = 0; i < items.size(); i++) {
	        int index = i;

	        JButton button = new JButton();

	        button.addActionListener(e -> {
	            if (onBuy != null) {
	                onBuy.accept(index);
	            }
	        });

	        buyButtons.add(button);
	        this.add(button);
	    }
	}
	
	private void positionBuyButtons() {
	    int x = 180;
	    int y = 320;

	    for (int i = 0; i < buyButtons.size(); i++) {

	        if (i == buyButtons.size() / 2) {
	            x = 180;
	            y = 510;
	        }

	        setButton(buyButtons.get(i), x, y, 120, 60);

	        x += 165;
	    }
	}
	
	private void setButton(JButton button, int xPos, int yPos, int width, int height) {
        button.setBounds(xPos, yPos, width, height);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(Assets.PIXEL_FONT.deriveFont(32f));
        
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        
    	button.setVisible(true);
    }
	
	public void setItems(ArrayList<Item> items) {
	    this.items = items;

	    for (JButton button : buyButtons) {
	        this.remove(button);
	    }

	    createBuyButtons();
	    repaint();
	}
	
	public void setPlayerGold(int gold) {
	    this.playerGold = gold;
	    repaint();
	}
}
