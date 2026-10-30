package progetto;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {

    private JFrame frame;
    private MapPanel mapPanel;
    private Player player;
    private Clip gameOverClip;

    private JButton retryButton = new JButton("Riprova");;
    private JButton exitButton = new JButton("Esci");

    public GameOverPanel(JFrame frame, MapPanel mapPanel, Player player) {
        this.frame = frame;
        this.mapPanel = mapPanel;
        this.player = player;

        this.setBackground(Color.BLACK);
        
        //playGameOverMusic("/sound/gameover.wav");

        styleButton(retryButton);
        styleButton(exitButton);

        retryButton.addActionListener(e -> {
            this.frame.setContentPane(this.mapPanel);
            this.frame.revalidate();
            this.mapPanel.requestFocus();
        });

        this.add(retryButton);
        this.add(exitButton);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        retryButton.setBounds(getWidth() / 2 - getWidth() / 9, getHeight() / 2, 100, 50);
        exitButton.setBounds(getWidth() / 2, getHeight() / 2, 100, 50);

        Font font = new Font("Impact", Font.BOLD, 60);
        g2.setFont(font);
        String deathMessage = "GAME OVER";
        g2.setColor(Color.RED);
        g2.drawString("GAME OVER", (getWidth() - g2.getFontMetrics().stringWidth(deathMessage)) / 2, getHeight() / 3 + getHeight() / 10);
        
        
        Font subFont = new Font("Arial", Font.PLAIN, 18);
        g2.setFont(subFont);
        g2.setColor(Color.GRAY);
        String subText = "Sei stato sconfitto...";
        g2.drawString(subText, (getWidth() - g2.getFontMetrics().stringWidth(subText)) / 2, getHeight() /2 - getHeight() / 30);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}