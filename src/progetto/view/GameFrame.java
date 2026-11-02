package progetto.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** VIEW: la finestra del gioco. Sa solo mostrare il pannello che le viene dato. */
public class GameFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 800);
    }

    /** Sostituisce la schermata corrente (mappa, combattimento, game over). */
    public void showPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
        panel.requestFocusInWindow();
    }
}
