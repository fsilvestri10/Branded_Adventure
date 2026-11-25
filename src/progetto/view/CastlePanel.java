package progetto.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import progetto.model.Bush;
import progetto.model.Castle;
import progetto.model.CastleGate;
import progetto.model.Enemy;
import progetto.model.Fence;
import progetto.model.Player;
import progetto.model.Shop;
import progetto.model.Tree;
import progetto.model.Wall;
import progetto.model.WorldObject;

public class CastlePanel extends JPanel {
	private Player player;
	private Castle castle;
	private final Sprite playerSprite = new Sprite(Assets.PLAYER_SHEET);
	
	public CastlePanel(KeyListener keys, Castle castle, Player player) {
        // permette al pannello di ricevere l'input da tastiera
        this.setFocusable(true);
        this.addKeyListener(keys);

        this.castle = castle;
        this.player = player;
    }
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
	}
	
	private void drawBoss() {
		
	}
	// disegno della mappa
    private void drawTiles(Graphics2D g2) {
        int tileSize = this.castle.getTileSize();
        for (int x = 0; x < this.castle.getColumns(); x++) {
            for (int y = 0; y < this.castle.getRows(); y++) {
                //if (this.world.isGrassTile(x, y)) g2.drawImage(Assets.GRASS_TILE, screenX, screenY, tileSize, tileSize, null);
            }
        }
    }
	private void drawObject(Graphics2D g2, WorldObject o, int cx, int cy) {
        int screenX = o.getX() - this.player.getX() + cx;
        int screenY = o.getY() - this.player.getY() + cy;
        
        // disegno gli oggetti nella mappa
        if (o instanceof Wall) {
        	if (o.getX() == 0 || o.getX() == 50 * 49)
        		g2.drawImage(Assets.CASTLE_HEAD, screenX, screenY, o.getWidth(), o.getHeight(), null);
        	else if (o.getY() == 50 * 49)
        		g2.drawImage(Assets.CASTLE_ROOF, screenX, screenY, o.getWidth() + 40, o.getHeight(), null);
        	else
        		g2.drawImage(Assets.CASTLE_WALL, screenX, screenY - 56, o.getWidth(), o.getHeight() + 62, null);
    		
        } else if(o instanceof CastleGate) {
        	g2.drawImage(Assets.CASTLE_GATE_SHADOW, screenX, screenY - 6, o.getWidth(), o.getHeight() + 12, null);
        	//if(this.world.getGate().isOpen()) g2.drawImage(Assets.CASTLE_OPENED_GATE, screenX - 6, screenY - 6, o.getWidth() + 6, o.getHeight() + 12, null);
        	//else g2.drawImage(Assets.CASTLE_GATE, screenX - 6, screenY - 6, o.getWidth() + 12, o.getHeight() + 12, null);
        }
    }
	// aggiorna animazione di movimento del player
    public void updateAnimation() {
        this.playerSprite.update(this.player.isMoving());
    }
 // disegna hud del player
    private void drawHud(Graphics2D g2, int cx, int cy) {
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fillRect(0, 600, 200, 200);
        g2.setColor(Color.YELLOW);
        g2.setFont(Assets.PIXEL_FONT.deriveFont(48f));
        g2.drawImage(Assets.GOLD, 0, 730, 30, 30, null);
        g2.drawString("Gold: " + this.player.getGold(), 30, 755);
        g2.drawString("Equipment: ", 0, 640);
        
        // ARMA
        g2.drawImage(Assets.itemImage(this.player.getEquipment(0)), 0, 655, 30, 30, null);
        g2.drawString(this.player.getEquipment(0).getName(), 30, 680);
        
        // ARMATURA
        g2.drawImage(Assets.itemImage(this.player.getEquipment(1)), 0, 695, 30, 30, null);        g2.drawString(this.player.getEquipment(1).getName(), 30, 720);
    }
}
