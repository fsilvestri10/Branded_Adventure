package progetto.controller;

import java.awt.EventQueue;

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
                GameWorld world = new GameWorld(tileSize);
                Player player = new Player("ez", 100, 1, GameController.SPAWN_TILE * tileSize, GameController.SPAWN_TILE * tileSize, tileSize, tileSize);

                // VIEW e input
                KeyHandler keys = new KeyHandler();
                GameFrame frame = new GameFrame();
                MapPanel mapPanel = new MapPanel(keys, world, player);

                // CONTROLLER
                GameController controller = new GameController(frame, keys, world, player, mapPanel);

                frame.setVisible(true);
                controller.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
