package progetto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JPanel;

/** VIEW: schermata di game over. Non decide cosa succede ai click: avvisa il controller. */
public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JButton continueButton = new JButton("Continua");
    private JButton exitButton = new JButton("Esci");

    private Runnable onContinue;
    private Runnable onExit;

    public MenuPanel() {
        this.setBackground(new Color(0,0,0,128));
        // i pulsanti vengono posizionati a mano in paintComponent
        this.setLayout(null);

        styleButton(continueButton);
        styleButton(exitButton);

        continueButton.addActionListener(e -> {
            if (this.onContinue != null) this.onContinue.run();
        });
        exitButton.addActionListener(e -> {
            if (this.onExit != null) this.onExit.run();
        });

        this.add(continueButton);
        this.add(exitButton);
    }

    public void setOnContinue(Runnable onContinue) {
        this.onContinue = onContinue;
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        continueButton.setBounds(getWidth() / 2 - getWidth() / 9, getHeight() / 2, 100, 50);
        exitButton.setBounds(getWidth() / 2, getHeight() / 2, 100, 50);

        Font font = new Font("Impact", Font.BOLD, 60);
        g2.setFont(font);
        String pauseMessage = "Gioco in pausa";
        g2.setColor(Color.WHITE);
        g2.drawString(pauseMessage, (getWidth() - g2.getFontMetrics().stringWidth(pauseMessage)) / 2, getHeight() / 3 + getHeight() / 10);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
