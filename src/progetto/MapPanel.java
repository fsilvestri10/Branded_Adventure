package progetto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapPanel extends JPanel {
	
	private Player player;
	private GameWorld world;
	
    public MapPanel(KeyHandler keyH, GameWorld world, Player player) {
        // PERMETTE AL PANNELLO DI RICEVERE L'INPUT TASTIERA
        this.setFocusable(true);
        this.addKeyListener(keyH);
        
        this.world = world;
        this.player = player;
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        world.draw(g2, player, getWidth() / 2, getHeight() / 2);
        
        player.draw(g2, 0, 0, getWidth() / 2, getHeight() / 2);
        
    }
}