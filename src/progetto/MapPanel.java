package progetto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Player player;
	private GameWorld world;
	private BufferedImage backgroundImage;
	
    public MapPanel(KeyHandler keyH, GameWorld world, Player player) {
        // PERMETTE AL PANNELLO DI RICEVERE L'INPUT TASTIERA
        this.setFocusable(true);
        this.addKeyListener(keyH);
        
        this.world = world;
        this.player = player;
        
        try {
			this.backgroundImage = ImageIO.read(getClass().getResource("/Background.png"));
		} catch (IOException e) {
			System.out.println(e);
		}
 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int screenCenterX = getWidth() / 2;
        int screenCenterY = getHeight() / 2;
        
        // Disegno dello sfondo
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        
        // Disegno del mondo
        world.draw(g2, player, screenCenterX, screenCenterY);
        
        // Disegno del player
        player.draw(g2, 0, 0, screenCenterX, screenCenterY);
        
    }
}