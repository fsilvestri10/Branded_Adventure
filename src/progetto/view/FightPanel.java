package progetto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JPanel;

import progetto.model.Battle;
import progetto.model.Enemy;
import progetto.model.Player;

/**
 * VIEW: la schermata di combattimento.
 * Mostra lo stato della battaglia (HP, messaggio) leggendolo dal model; quando l'utente
 * preme un pulsante avvisa il controller tramite i callback, senza decidere nulla.
 */
public class FightPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Battle battle;
    private final Player player;
    private final Enemy enemy;

    private JButton attack = new JButton("Attacca");
    private JButton skills = new JButton("Abilità");
    private JButton[] skillButtons = new JButton[4];
    private boolean skillsOpened = false; // stato del menù (solo visivo)

    private Runnable onAttack;
    private IntConsumer onSkill;

    public FightPanel(Battle battle) {
        this.battle = battle;
        this.player = battle.getPlayer();
        this.enemy = battle.getEnemy();

        // i pulsanti vengono posizionati a mano in paintComponent
        this.setLayout(null);

        // se viene premuto attacco, avvisiamo il controller
        this.attack.addActionListener(e -> {
            if (this.onAttack != null) this.onAttack.run();
        });
        // se viene premuto abilità, apriamo o chiudiamo il menù delle abilità
        this.skills.addActionListener(e -> skillsPressed());
        this.add(attack);
        this.add(skills);

        // creiamo i pulsanti per le abilità che possediamo, per le altre mettiamo "???"
        for (int i = 0; i < skillButtons.length; i++) {
            if (this.player.getSkills().size() > i) {
            	skillButtons[i] = new JButton(this.player.getSkills().get(i).getName());
            	int index = i;
               	skillButtons[i].addActionListener(e -> {
                   	if (this.onSkill != null) this.onSkill.accept(index);
               	});
            } else skillButtons[i] = new JButton("???");
        }
    }

    /** Cosa fare quando l'utente preme "Attacca". */
    public void setOnAttack(Runnable onAttack) {
        this.onAttack = onAttack;
    }

    /** Cosa fare quando l'utente sceglie un'abilità (riceve l'indice dell'abilità). */
    public void setOnSkill(IntConsumer onSkill) {
        this.onSkill = onSkill;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        // disegno lo sfondo
        g2.drawImage(Assets.BACKGROUND, 0, 0, w, h, null);

        // disegno la barra dell'HUD
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fillRect(0, h - h / 5, w, h / 5);

        // disegno il player, il nemico e i messaggi sopra
        drawPlayerBattle(g2, w, h);
        drawEnemyBattle(g2, w, h);
        drawCombatMessage(g2, w, h);

        // tasti "Skill" e "Attack"
        setButton(attack, w - w / 5, h - h / 5, w / 5, h / 5);
        setButton(skills, w - w * 2 / 5, h - h / 5, w / 5, h / 5);

        // mostra/nasconde le varie abilità se premuto "Skill"
        for (int i = 0; i < skillButtons.length; i++) {
            if (this.skillButtons[i] != null && this.skillButtons[i].isVisible()) {
                setButton(this.skillButtons[i], w - w * 2 / 5, h - h / 5 - h * (i + 1) / 20, w / 5, h / 20);
                
            }
        }
        for(int i = 0; i < this.battle.getPlayer().getSkills().size(); i++) {
        	if(this.battle.getPlayer().getSkills().get(i).getCooldown() != 0 && this.skillsOpened) {
            	g2.drawImage(Assets.ARMOR, 700, 550, 20, 20, null);
            	
            }
        }
        
        g2.drawImage(Assets.ARMOR, 700, 550, 20, 20, null);
        
    }

    private void drawPlayerBattle(Graphics2D g2, int w, int h) {
        BufferedImage sprite = Assets.PLAYER_COMBAT;
        g2.drawImage(sprite, 0, h - h / 5, h / 5, h / 5, null);
        g2.fillRect(w / 5, h - h / 10, w / 3, h / 10);
        g2.setColor(Color.GREEN);
        double percentHP = (double) this.player.getCurrentHP() / this.player.getTotalHP();
        g2.fillRect(w / 5 + 15, h - h / 10 + 15, (int) (w / 3 * percentHP) - 30, h / 10 - 30);
        g2.setFont(new Font("sanserif", Font.BOLD, w / 30));
        g2.drawString("HP", w / 5 - w / 20, h - h / 10 + h / 15);
        g2.setFont(new Font("sanserif", Font.BOLD, w / 20));
        g2.drawString(this.player.getCurrentHP() + "/" + this.player.getTotalHP(), w / 5 + h / 10, h - h / 10 - 20);
    }

    private void drawEnemyBattle(Graphics2D g2, int w, int h) {
        switch(enemy.getId()) {
    	case 0:
    		g2.drawImage(Assets.BOSS1_FIGHT, w / 2 - w / 6, h / 4, w / 3, h / 2, null);
    		break;
    	case 1:
    		g2.drawImage(Assets.BOSS2_FIGHT, w / 2 - w / 4, h / 4, w / 2, h / 2, null);
    		break;
    	case 2:
    		g2.drawImage(Assets.BOSS2_FIGHT, w / 2 - w / 6, h / 4, w / 3, h / 2, null);
    		break;
    	case 3:
    		g2.drawImage(Assets.BOSS1_FIGHT, w / 2 - w / 6, h / 4, w / 3, h / 2, null);
    		break;
    	default:
    		g2.drawImage(Assets.ENEMY_FIGHT, w / 2 - w / 6, h / 4, w / 3, h / 2, null);
    		break;
        }

        g2.setColor(Color.GREEN);
        double percentHP = (double) this.enemy.getCurrentHP() / this.enemy.getTotalHP();
        g2.fillRect(w / 2 - w / 6, h / 5, (int) (w / 3 * percentHP), h / 10 - 30);
    }

    private void drawCombatMessage(Graphics g2, int w, int h) {
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fillRect(0, 0, w, h / 12);
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        g2.setColor(Color.WHITE);
        g2.drawString(this.battle.getMessage(), 20, h / 20);
    }

    // funzione per mostrare correttamente i Button
    private void setButton(JButton button, int xPos, int yPos, int width, int height) {
        button.setBounds(xPos, yPos, width, height);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setVisible(true);
    }

    // se il menù abilità è chiuso lo apro, sennò lo chiudo
    private void skillsPressed() {
        this.skillsOpened = !this.skillsOpened;

        for (int i = 0; i < skillButtons.length; i++) {
            if (!this.skillsOpened) {
                this.remove(skillButtons[i]);
            } else {
                this.add(skillButtons[i]);
            }
        }
    }
}
