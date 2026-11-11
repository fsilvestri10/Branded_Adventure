package progetto.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * VIEW: tutte le immagini del gioco, caricate una volta sola.
 * Il model non sa che esistono: è la view a scegliere quale immagine mostrare per ogni oggetto.
 */
public final class Assets {

    public static final BufferedImage BACKGROUND;
    public static final BufferedImage GRASS_TILE;
    public static final BufferedImage WALL;
    public static final BufferedImage TREE;
    public static final BufferedImage BUSH;
    public static final BufferedImage BUSH_TRAMPLED;
    public static final BufferedImage ENEMY;
    public static final BufferedImage ENEMY_FIGHT;
    public static final BufferedImage BOSS1;
    public static final BufferedImage BOSS1_FIGHT;
    public static final BufferedImage BOSS2;
    public static final BufferedImage BOSS2_FIGHT;
    public static final BufferedImage PLAYER_SHEET;
    public static final BufferedImage PLAYER_COMBAT;
    public static final BufferedImage SHOP;
    public static final BufferedImage GOLD;
    public static final BufferedImage CHAINMAIL;
    public static final BufferedImage BF_SWORD;
    public static final BufferedImage ANCIENT_SCROLL;
    public static final BufferedImage SWORD;
    public static final BufferedImage ARMOR;
    public static final BufferedImage BUILDINGS;
    
    static {
        try {
            BACKGROUND = load("/Background.png");
            BufferedImage grass = load("/Grass.png");
            GRASS_TILE = grass.getSubimage(32, 96, 16, 16);
            // il cespuglio sta nello stesso sprite sheet dell'erba (normale e calpestato)
            BUSH = grass.getSubimage(160 + 96, 176, 16, 16);
            BUSH_TRAMPLED = grass.getSubimage(176 + 96, 176, 16, 16);
            WALL = load("/Wall.png");
            TREE = load("/Tree.png");
            ENEMY = load("/Enemy.png");
            ENEMY_FIGHT = load("/EnemyFight.png");
            BOSS1 = load("/Boss1.png");
            BOSS1_FIGHT = load("/Boss1Fight.png");
            BOSS2 = load("/Boss2.png");
            BOSS2_FIGHT = load("/Boss2Fight.png");
            PLAYER_SHEET = load("/Player.png");
            PLAYER_COMBAT = PLAYER_SHEET.getSubimage(96, 0, 96, 96);
            GOLD = load("/Coin.png");
            CHAINMAIL = load("/Chainmail.png");
            BF_SWORD = load("/BF_Sword.png");
            ANCIENT_SCROLL = load("/Ancient_scroll.png");
            SWORD = load("/Long_Sword.png");
            ARMOR = load("/Cloth_Armor.png");
            BUILDINGS = load("/Buildings.png");
            SHOP = BUILDINGS.getSubimage(546, 16, 120, 160);
            
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Assets() {}

    private static BufferedImage load(String path) throws IOException {
        URL url = Assets.class.getResource(path);
        if (url == null) throw new IOException("Risorsa non trovata: " + path);
        return ImageIO.read(url);
    }
}
