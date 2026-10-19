package progetto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class FightPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	
	private Player player;
	private Enemy enemy;
	
	private JButton attack = new JButton("Attacca");
	private JButton skills = new JButton("Abilità");
	private JButton[] skillButtons = new JButton[4];
	
	private boolean checkButtons = true;
	private boolean skillsOpened = false;
	
	public FightPanel(Player player, Enemy enemy) {
		this.enemy = enemy;
		this.player = player;
		try {
			this.backgroundImage = ImageIO.read(getClass().getResource("/Background.png"));
		} catch (IOException e) {
			System.out.println(e);
		}
		this.attack.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        player.attack(enemy);
		    }
		});
		skills.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        skillsPressed(attack, skills);
		    }
		});
		this.add(attack);
		this.add(skills);
		
		for(int i = 0; i < skillButtons.length; i++) {
			if(this.player.getSkills().size() > i) skillButtons[i] = new JButton(this.player.getSkills().get(i).getName());
			else skillButtons[i] = new JButton("???");
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
		g2.setColor(new Color(0, 0, 0, 128));
		g2.fillRect(0, getHeight()-getHeight()/5, getWidth(), getHeight()/5);
		
		this.player.drawBattle(g2, getWidth(), getHeight());
		this.enemy.drawBattle(g2, getWidth(), getHeight());
		
		attack.setBackground(new Color(59, 89, 182));
        attack.setForeground(Color.WHITE);
        attack.setFocusPainted(false);
        attack.setFont(new Font("Algerian", Font.BOLD, 20));
		attack.setBounds(getWidth()-getWidth()/5, getHeight()-getHeight()/5, getWidth()/5, getHeight()/5);
		attack.setVisible(true);
		
		
		skills.setBounds(getWidth()-getWidth()*2/5, getHeight()-getHeight()/5, getWidth()/5, getHeight()/5);
		skills.setBackground(new Color(59, 89, 182));
        skills.setForeground(Color.WHITE);
        skills.setFocusPainted(false);
        skills.setFont(new Font("Tahoma", Font.BOLD, 20));
		skills.setVisible(true);
		
		for(int i = 0; i < skillButtons.length; i++) {
			if(this.skillButtons[i] != null && this.skillButtons[i].isVisible()) {
				skillButtons[i].setBounds(getWidth()-getWidth()*2/5, getHeight()-getHeight()/5-getHeight()*(i+1)/20, getWidth()/5, getHeight()/20);
				skillButtons[i].setBackground(new Color(59, 89, 182));
		        skillButtons[i].setForeground(Color.WHITE);
		        skillButtons[i].setFocusPainted(false);
		        skillButtons[i].setFont(new Font("Tahoma", Font.BOLD, 20));
				skillButtons[i].setVisible(true);
			}
		}
	}
	private void skillsPressed(JButton attack, JButton skills) {
		if (!this.skillsOpened) this.skillsOpened = true;
		else this.skillsOpened = false;
		
		for(int i = 0; i < skillButtons.length; i++) {
			if(!this.skillsOpened) {
				this.remove(skillButtons[i]);
			} else {
				this.add(skillButtons[i]);
			}
		}
	}				
}
