package progetto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import javax.swing.JPanel;

import progetto.model.Bush;
import progetto.model.Enemy;
import progetto.model.GameWorld;
import progetto.model.Player;
import progetto.model.Shop;
import progetto.model.Tree;
import progetto.model.Wall;
import progetto.model.WorldObject;

/**
 * VIEW: la mappa. Legge il model (mondo e giocatore) e lo disegna, senza mai modificarlo.
 * Non conosce il controller: riceve solo un KeyListener generico per l'input.
 */
public class MapPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int ENEMY_FRAME_TICKS = 60;

    private final Player player;
    private final GameWorld world;
    private final Sprite playerSprite = new Sprite(Assets.PLAYER_SHEET);
    private final Random rnd = new Random();
    private boolean fightHint = false;

    // animazione di ogni nemico (sprite scelto a caso ogni tanto); WeakHashMap: i nemici sconfitti vengono liberati
    private final Map<Enemy, EnemyAnim> enemyAnims = new WeakHashMap<>();

    private static class EnemyAnim {
        int counter = ENEMY_FRAME_TICKS; // parte già al limite, così il primo frame viene scelto subito
        BufferedImage frame;
    }

    public MapPanel(KeyListener keys, GameWorld world, Player player) {
        // permette al pannello di ricevere l'input da tastiera
        this.setFocusable(true);
        this.addKeyListener(keys);

        this.world = world;
        this.player = player;
    }

    /** Fa avanzare l'animazione del giocatore (il controller lo chiama a ogni aggiornamento). */
    public void updateAnimation() {
        this.playerSprite.update(this.player.isMoving());
    }

    /** Mostra o nasconde la scritta "E per combattere". */
    public void setFightHint(boolean fightHint) {
        this.fightHint = fightHint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        // disegno dello sfondo
        g2.drawImage(Assets.BACKGROUND, 0, 0, getWidth(), getHeight(), null);

        drawTiles(g2, cx, cy);

        // disegno gli oggetti del mondo
        for (WorldObject object : this.world.getObjects()) {
            drawObject(g2, object, cx, cy);
        }

        drawPlayer(g2, cx, cy);
        drawHud(g2, cx, cy);
    }

    // disegno della mappa (spostata rispetto al giocatore)
    private void drawTiles(Graphics2D g2, int cx, int cy) {
        int tileSize = this.world.getTileSize();
        for (int x = 0; x < this.world.getColumns(); x++) {
            for (int y = 0; y < this.world.getRows(); y++) {
                // posizione dove va disegnata la casella sullo schermo
                int screenX = x * tileSize - this.player.getX() + cx;
                int screenY = y * tileSize - this.player.getY() + cy;

                // salto le caselle fuori dallo schermo
                if (screenX + tileSize < 0 || screenX > getWidth() || screenY + tileSize < 0 || screenY > getHeight()) continue;

                // posiziono l'erba nella mappa
                if (this.world.isGrassTile(x, y)) g2.drawImage(Assets.GRASS_TILE, screenX, screenY, tileSize, tileSize, null);
            }
        }
    }

    private void drawObject(Graphics2D g2, WorldObject o, int cx, int cy) {
        int screenX = o.getX() - this.player.getX() + cx;
        int screenY = o.getY() - this.player.getY() + cy;
        
        if (o instanceof Wall) {
            g2.drawImage(Assets.WALL, screenX - 6, screenY - 6, o.getWidth() + 12, o.getHeight() + 12, null);
        } else if (o instanceof Tree) {
            g2.drawImage(Assets.TREE, screenX - 20, screenY - 20, o.getWidth() + 40, o.getHeight() + 40, null);
        } else if (o instanceof Bush) {
            BufferedImage img = ((Bush) o).isTrampled() ? Assets.BUSH_TRAMPLED : Assets.BUSH;
            g2.drawImage(img, screenX, screenY, o.getWidth(), o.getHeight(), null);
        } else if (o instanceof Enemy) {
            drawEnemy(g2, (Enemy) o, screenX, screenY);
        } else if(o instanceof Shop) {
        	g2.drawImage(Assets.WALL, screenX - 6, screenY - 6, o.getWidth() + 12, o.getHeight() + 12, null);
        }
    }

    private void drawEnemy(Graphics2D g2, Enemy enemy, int screenX, int screenY) {
        EnemyAnim anim = this.enemyAnims.computeIfAbsent(enemy, k -> new EnemyAnim());

        // ogni tanto scelgo a caso una delle 4 righe dello sprite sheet
        anim.counter++;
        if (anim.counter >= ENEMY_FRAME_TICKS) {
            //BufferedImage sheet = enemy.isBoss() ? Assets.BOSS1 : Assets.ENEMY;
        	BufferedImage sheet = null;
            switch(enemy.getId()) {
            	case 0:
            		sheet = Assets.BOSS1;
            		break;
            	case 1:
            		sheet = Assets.BOSS2;
            		break;
            	case 2:
            		sheet = Assets.BOSS1;
            		break;
            	case 3:
            		sheet = Assets.BOSS1;
            		break;
            	default:
            		sheet = Assets.ENEMY;
            		break;
            }
            anim.frame = sheet.getSubimage(96, this.rnd.nextInt(4) * 96, 96, 96);
            anim.counter = 0;
        }

        g2.drawImage(anim.frame, screenX - 6, screenY - 6, enemy.getWidth() + 12, enemy.getHeight() + 12, null);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Britannic Bold", Font.BOLD, 15));
        g2.drawString("[ LV. " + enemy.getLevel() + " ]", screenX - 3, screenY - 7);
    }

    // il giocatore sta sempre al centro dello schermo
    private void drawPlayer(Graphics2D g2, int cx, int cy) {
        BufferedImage frame = this.playerSprite.getCurrentSprite(this.player.getDirection());
        if (frame != null) g2.drawImage(frame, cx, cy, this.player.getWidth(), this.player.getHeight(), null);

        if (this.fightHint) {
            g2.setColor(Color.BLACK);
            g2.drawString("E per combattere", cx + 18, cy);
        }
        g2.setFont(new Font("Britannic Bold", Font.BOLD, 15));
        g2.drawString("[ LV. " + this.player.getLevel() + " ]", cx - 3, cy - 7);
    }

    private void drawHud(Graphics2D g2, int cx, int cy) {
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fillRect(0, cy * 2 - cy / 10, cx / 3, cy / 10);
        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("sanserif", Font.BOLD, 25));
        g2.drawString("Gold: " + this.player.getGold(), 0, cy * 2);
    }
}
