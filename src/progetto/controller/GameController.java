package progetto.controller;

import javax.swing.Timer;

import progetto.model.Battle;
import progetto.model.Enemy;
import progetto.model.GameWorld;
import progetto.model.Player;
import progetto.model.Shop;
import progetto.view.AudioPlayer;
import progetto.view.FightPanel;
import progetto.view.GameFrame;
import progetto.view.GameOverPanel;
import progetto.view.MapPanel;
import progetto.view.ShopPanel;

/**
 * CONTROLLER: il cuore del gioco.
 * Legge l'input, chiede al model di aggiornarsi e decide quale schermata mostrare alla view.
 */
public class GameController {

    /** Casella di partenza del giocatore (si moltiplica per la dimensione delle caselle). */
    public static final int SPAWN_TILE = 25;

    private static final int TICK_MS = 16; // ciclo di gioco a ~60 FPS

    private final GameFrame frame;
    private final KeyHandler keys;
    private final GameWorld world;
    private final Player player;
    private final MapPanel mapPanel;
    private final AudioPlayer audio = new AudioPlayer();

    private boolean onMap = true;

    public GameController(GameFrame frame, KeyHandler keys, GameWorld world, Player player, MapPanel mapPanel) {
        this.frame = frame;
        this.keys = keys;
        this.world = world;
        this.player = player;
        this.mapPanel = mapPanel;
    }

    /** Mostra la mappa e fa partire il ciclo di gioco. */
    public void start() {
        this.frame.showPanel(this.mapPanel);
        Timer timer = new Timer(TICK_MS, e -> update());
        timer.start();
    }

    private void update() {
        if (this.onMap) updateMap();
        this.frame.getContentPane().repaint();
    }

    private void updateMap() {
        // controllo se è stato premuto un tasto per il movimento e agisco di conseguenza
        int dx = 0;
        int dy = 0;
        if (this.keys.upPressed) dy -= Player.SPEED;
        if (this.keys.downPressed) dy += Player.SPEED;
        if (this.keys.leftPressed) dx -= Player.SPEED;
        if (this.keys.rightPressed) dx += Player.SPEED;
        	
        this.player.move(dx, dy, this.world);
        this.mapPanel.updateAnimation();
        this.world.update();

        // se c'è un nemico a portata, la view mostra il suggerimento e con E si combatte
        Object near = this.world.findEnemyInRange(this.player);
        this.mapPanel.setFightHint(near != null);
        if(near == null) return;
        
        if (this.keys.ePressed && near instanceof Enemy) {
            this.keys.ePressed = false;
            startBattle((Enemy)near);
        } else if(this.keys.ePressed && near instanceof Shop) {
        	this.keys.ePressed = false;
            openShop((Shop)near);
        }
    }

    private void openShop(Shop shop) {
    	this.onMap = false;
    	shop.updateShop();
    	ShopPanel shopPanel = new ShopPanel ();
    	new ShopController(shopPanel, player, shop, this::onExitShop);
    	this.frame.showPanel(shopPanel);
    }
     private void startBattle(Enemy enemy) {
        this.onMap = false;
        this.world.removeEnemy(enemy);

        Battle battle = new Battle(this.player, enemy);
        FightPanel fightPanel = new FightPanel(battle);
        new BattleController(battle, fightPanel, this::onBattleWon, this::onBattleLost);

        this.audio.play("Normal_fight");
        this.frame.showPanel(fightPanel);
    }
     
     private void onExitShop() {
    	 this.onMap = true;
    	 this.frame.showPanel(this.mapPanel);
     }

    // vittoria: ferma la musica e torna alla mappa
    private void onBattleWon() {
        this.audio.stop();
        this.onMap = true;
        this.frame.showPanel(this.mapPanel);
    }

    // sconfitta: ferma la musica e passa alla schermata di game over
    private void onBattleLost() {
        this.audio.stop();
        GameOverPanel gameOverPanel = new GameOverPanel();
        gameOverPanel.setOnRetry(this::retry);
        gameOverPanel.setOnExit(() -> System.exit(0));
        this.frame.showPanel(gameOverPanel);
    }

    // "Riprova": il mondo e il giocatore ripartono da capo
    private void retry() {
        int spawn = SPAWN_TILE * this.world.getTileSize();
        this.world.reset();
        this.player.respawn(spawn, spawn);
        this.onMap = true;
        this.frame.showPanel(this.mapPanel);
    }
}
