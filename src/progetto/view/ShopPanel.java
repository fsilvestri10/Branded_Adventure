package progetto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ShopPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton buyItem1 = new JButton("Compra");
    private JButton buyItem2 = new JButton("Compra");
    private JButton exit = new JButton("Esci");
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
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        g2.drawImage(Assets.BACKGROUND, 0, 0, w, h, null);
        
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fillRect(0, h - h / 5, w, h / 5);
        
        setButton(buyItem1, w - w / 5, h - h / 5, w / 5, h / 5);
        setButton(buyItem2, w - w * 2 / 5, h - h / 5, w / 5, h / 5);
        setButton(exit, w - w*3 / 5, h - h / 5, w / 5, h / 5);
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
