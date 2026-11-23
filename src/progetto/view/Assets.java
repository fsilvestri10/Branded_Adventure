package progetto.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import javax.imageio.ImageIO;

import progetto.model.Item;

/**
 * VIEW: tutte le risorse grafiche del gioco,
 * caricate una volta sola.
 *
 * Il model non sa che esistono: è la view a scegliere
 * quale risorsa utilizzare per ogni oggetto.
 */
public final class Assets {

    // IMMAGINI
    public static final BufferedImage BACKGROUND;
    public static final BufferedImage GRASS_TILE;
    public static final BufferedImage WALL;
    public static final BufferedImage TREE;
    public static final BufferedImage BUSH;
    public static final BufferedImage BUSH_TRAMPLED;
    public static final BufferedImage BUILDINGS;
    public static final BufferedImage SHOP;
    public static final BufferedImage ENEMY;
    public static final BufferedImage ENEMY_FIGHT;
    public static final BufferedImage BOSS1;
    public static final BufferedImage BOSS1_FIGHT;
    public static final BufferedImage BOSS2;
    public static final BufferedImage BOSS2_FIGHT;
    public static final BufferedImage PLAYER_SHEET;
    public static final BufferedImage PLAYER_COMBAT;
    public static final BufferedImage ANCIENT_SCROLL;
    public static final BufferedImage GOLD;
    public static final BufferedImage SWORD;
    public static final BufferedImage ARMOR;
    public static final BufferedImage CHAINMAIL;
    public static final BufferedImage BF_SWORD;
    public static final BufferedImage CASTLE;
    public static final BufferedImage PATH_TILE;
    public static final BufferedImage CASTLE_GATE;
    public static final BufferedImage CASTLE_GATE_SHADOW;
    public static final BufferedImage CASTLE_WALL;
    public static final BufferedImage CASTLE_HEAD;
    public static final BufferedImage CASTLE_ROOF;
    public static final BufferedImage CASTLE_COLUMN;
    public static final BufferedImage CASTLE_LEFT_WALL;
    public static final BufferedImage CASTLE_RIGHT_WALL;
    public static final BufferedImage COOLDOWN_ICON;
    public static final BufferedImage FENCE;
    public static final BufferedImage SHOP_BACKGROUND;
    public static final BufferedImage SHOP_FRAME;
    public static final BufferedImage ITEM_FRAME;
    public static final BufferedImage MEDIEVAL_WOOD;
    public static final BufferedImage WOOD_SIGN;
    public static final BufferedImage WOOD_CROSS;
    public static final BufferedImage BIG_WOOD_SIGN;
    public static final BufferedImage WINTERS_APPROACH;
    public static final BufferedImage STORMRAZOR;
    public static final BufferedImage THORNMAIL;
    public static final BufferedImage BLACK_CLEAVER;
    public static final BufferedImage GARGOYLE_STONEPLATE;
    public static final BufferedImage INFINITY_EDGE;
    public static final BufferedImage BUFF;
    public static final BufferedImage DEBUFF;
    
    // FONT
    public static final Font PIXEL_FONT;

    static {

        try {

            // CARICAMENTO IMMAGINI

            BACKGROUND = load("/Background.png");

            BufferedImage grass = load("/Grass.png");

            GRASS_TILE = grass.getSubimage(32, 96, 16, 16);

            // Il cespuglio sta nello stesso sprite sheet dell'erba
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
            WINTERS_APPROACH = load("/Winters_approach.png");
            STORMRAZOR = load("/Stormrazor.png");
            THORNMAIL = load("/Thornmail.png");
            BLACK_CLEAVER = load("/Black_cleaver.png");
            GARGOYLE_STONEPLATE = load("/Gargoyle_stoneplate.png");
            INFINITY_EDGE = load("/Infinity_edge.png");

            BUILDINGS = load("/Buildings.png");
            SHOP = BUILDINGS.getSubimage(546, 16, 120, 160);

            CASTLE = load("/Castle2.png");
            PATH_TILE = CASTLE.getSubimage(200, 328, 50, 50);
            CASTLE_GATE = CASTLE.getSubimage(315, 0, 100, 90);
            CASTLE_GATE_SHADOW = CASTLE.getSubimage(430, 224, 68, 66);
            CASTLE_WALL = CASTLE.getSubimage(210, 128, 46, 90);
            CASTLE_COLUMN = CASTLE.getSubimage(352, 320, 32, 96);
            CASTLE_LEFT_WALL = CASTLE.getSubimage(200, 128, 56, 90);
            CASTLE_RIGHT_WALL = CASTLE.getSubimage(223, 128, 23, 90);
            CASTLE_HEAD = CASTLE.getSubimage(199, 29, 16, 19);
            CASTLE_ROOF = CASTLE.getSubimage(92, 78, 41, 17);


            COOLDOWN_ICON = load("/Cooldown.png");
            BUFF = load("/Buff.png");
            DEBUFF = load("/Debuff.png");
            FENCE = load("/Fence.png");

            SHOP_BACKGROUND = load("/Shop_background.png");
            SHOP_FRAME = load("/Shop_frame.png");
            ITEM_FRAME = load("/Item_frame.png");

            MEDIEVAL_WOOD = load("/MedievalWood.png");
            WOOD_SIGN =  MEDIEVAL_WOOD.getSubimage(224, 34, 32, 30);
            WOOD_CROSS = MEDIEVAL_WOOD.getSubimage(80, 32, 16, 16);
            BIG_WOOD_SIGN = MEDIEVAL_WOOD.getSubimage(0, 0, 76, 94);

            // CARICAMENTO FONT
            PIXEL_FONT = loadFont("/PixelPurl.ttf");

        } catch (IOException e) {

            throw new ExceptionInInitializerError(e);

        }
    }
    public static BufferedImage itemImage(Item item) {
        return switch (item.getId()) {
            case SWORD -> SWORD;
            case ARMOR -> ARMOR;
            case BF_SWORD -> BF_SWORD;
            case CHAINMAIL -> CHAINMAIL;
            case STORMRAZOR -> STORMRAZOR;
            case WINTERS_APPROACH -> WINTERS_APPROACH;
            case BLACK_CLEAVER -> BLACK_CLEAVER;
            case THORNMAIL -> THORNMAIL;
            case INFINITY_EDGE -> INFINITY_EDGE;
            case GARGOYLE_STONEPLATE -> GARGOYLE_STONEPLATE;
        };
    }
    private Assets() {}
    
    // METODO PER CARICARE LE IMMAGINI
    private static BufferedImage load(String path) throws IOException {
        URL url = Assets.class.getResource(path);
        if (url == null) {
            throw new IOException("Risorsa non trovata: " + path);
        }
        return ImageIO.read(url);
    }

    // METODO PER CARICARE I FONT
    private static Font loadFont(String path) throws IOException {
        try (InputStream stream = Assets.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("Font non trovato: " + path);
            }
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException e) {
            throw new IOException("Formato font non valido: " + path, e);

        }
    }
}