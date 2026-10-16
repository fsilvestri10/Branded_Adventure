package progetto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FightPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	
	private Player player;
	private Enemy enemy;
	
	public FightPanel(Player player, Enemy enemy) {
		this.enemy = enemy;
		this.player = player;
		try {
			this.backgroundImage = ImageIO.read(getClass().getResource("/Background.png"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
		g2.setColor(new Color(0, 0, 0, 128));
		g2.fillRect(0, getHeight()-getHeight()/5, getWidth(), getHeight()/5);
		
		this.player.drawBattle(g2, getWidth(), getHeight());
		this.enemy.drawBattle(g2, getWidth(), getHeight());
	}
}
