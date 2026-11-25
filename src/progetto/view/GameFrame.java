package progetto.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

//la finestra del gioco
public class GameFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 150, 1000, 800);
    }

    // Sostituisce la schermata corrente
    public void showPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
        panel.requestFocusInWindow();
    }
}
