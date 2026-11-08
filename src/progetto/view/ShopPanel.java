package progetto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ShopPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton buyItem1 = new JButton("Compra");
    private JButton buyItem2 = new JButton("Compra");
    private JButton exit = new JButton("Esci");
    private JLabel noGold = new JLabel("Non hai abbastanza oro per comprare questo oggetto");
	private IntConsumer onBuy;
	private Runnable onExit;
    
	public ShopPanel() {
		this.setLayout(null);
		this.buyItem1.addActionListener(e -> {
            if (this.onBuy != null) this.onBuy.accept(0);
        });
        this.buyItem2.addActionListener(e -> {
            if (this.onBuy != null) this.onBuy.accept(1);
        });
        this.exit.addActionListener(e -> {
            if (this.onExit != null) this.onExit.run();
        });
        this.add(buyItem1);
        this.add(buyItem2);
        this.add(exit);
        this.add(noGold);
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        g2.drawImage(Assets.BACKGROUND, 0, 0, w, h, null);
        
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fillRect(0, h - h / 5, w, h / 5);
        
        drawItems(g2, w, h);
        
        setButton(buyItem1, w - w / 5, h - h / 5, w / 5, h / 5);
        setButton(buyItem2, w - w * 2 / 5, h - h / 5, w / 5, h / 5);
        setButton(exit, w - w * 3 / 5, h - h / 5, w / 5, h / 5);
        
	}
	
	public void drawItems (Graphics g2, int w, int h) {
		
		g2.drawImage(Assets.ANCIENT_SCROLL, w - w * 2 / 5, h - h * 2 / 3, w / 5, h / 6, null);
		//g2.setFont(new Font("Simplex", Font.BOLD, 25));
		//g2.drawString("CHAINMAIL", w - w * 3 / 8, h * 2 / 5);
		//g2.drawString("500 GOLD", w - w * 3 / 8, h - h * 2 / 6);
		g2.drawImage(Assets.CHAINMAIL, w - w * 2 / 5, h - h * 2 / 4, w / 5, h / 3, null);
		
		g2.drawImage(Assets.ANCIENT_SCROLL, w - w / 5, h - h * 2 / 3, w / 5, h / 6, null);
		g2.drawImage(Assets.BF_SWORD,  w - w / 5, h - h * 2 / 4, w / 5, h / 3, null);
	}
	
	public void drawNoGoldLabel() {
		int w = getWidth();
        int h = getHeight();
        this.noGold.setBounds(w - w*3 / 5, h / 3, w, h / 5);
        this.noGold.setBackground(new Color(0, 0, 0, 128));
        this.noGold.setForeground(Color.WHITE);
        this.noGold.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.noGold.setVisible(true);
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
	private void setButton(JButton button, int xPos, int yPos, int width, int height) {
        button.setBounds(xPos, yPos, width, height);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setVisible(true);
    }
	
}
