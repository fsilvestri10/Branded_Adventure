package progetto.controller;

import java.awt.EventQueue;

import progetto.model.Castle;
import progetto.model.GameWorld;
import progetto.model.Player;
import progetto.view.GameFrame;
import progetto.view.MapPanel;

/** Punto di avvio: crea model, view e controller e li collega. Nessuna logica di gioco. */
public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                int tileSize = 50;

                // MODEL
                
                Player player = new Player("ez", 1000000, 1, GameController.SPAWN_TILE_X * tileSize, GameController.SPAWN_TILE_Y * tileSize, tileSize, tileSize);
                GameWorld world = new GameWorld(tileSize, player);
                //Castle castle = new Castle(tileSize, );
                // VIEW e input
                KeyHandler keys = new KeyHandler();
                GameFrame frame = new GameFrame();
                MapPanel mapPanel = new MapPanel(keys, world, player);

                // CONTROLLER
                GameController controller = new GameController(frame, keys, world, player, mapPanel);

                frame.setResizable(false);
                frame.setVisible(true);
                controller.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
