package progetto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JPanel;

/** VIEW: schermata di game over. Non decide cosa succede ai click: avvisa il controller. */
public class GameOverPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JButton retryButton = new JButton("Riprova");
    private JButton exitButton = new JButton("Esci");

    private Runnable onRetry;
    private Runnable onExit;

    public GameOverPanel() {
        this.setBackground(Color.BLACK);
        // i pulsanti vengono posizionati a mano in paintComponent
        this.setLayout(null);

        styleButton(retryButton);
        styleButton(exitButton);

        retryButton.addActionListener(e -> {
            if (this.onRetry != null) this.onRetry.run();
        });
        exitButton.addActionListener(e -> {
            if (this.onExit != null) this.onExit.run();
        });

        this.add(retryButton);
        this.add(exitButton);
    }

    public void setOnRetry(Runnable onRetry) {
        this.onRetry = onRetry;
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        retryButton.setBounds(getWidth() / 2 - getWidth() / 9, getHeight() / 2, 100, 50);
        exitButton.setBounds(getWidth() / 2, getHeight() / 2, 100, 50);

        Font font = new Font("Impact", Font.BOLD, 60);
        g2.setFont(font);
        String deathMessage = "GAME OVER";
        g2.setColor(Color.RED);
        g2.drawString(deathMessage, (getWidth() - g2.getFontMetrics().stringWidth(deathMessage)) / 2, getHeight() / 3 + getHeight() / 10);

        Font subFont = new Font("Arial", Font.PLAIN, 18);
        g2.setFont(subFont);
        g2.setColor(Color.GRAY);
        String subText = "Sei stato sconfitto...";
        g2.drawString(subText, (getWidth() - g2.getFontMetrics().stringWidth(subText)) / 2, getHeight() / 2 - getHeight() / 30);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
