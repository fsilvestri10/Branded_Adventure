package progetto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class FightPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	
	private Player player;
	private Enemy enemy;
	private JFrame frame;
	private MapPanel mapPanel;
	
	private JButton attack = new JButton("Attacca");
	private JButton skills = new JButton("Abilità");
	private JButton[] skillButtons = new JButton[4];
	
	private boolean playersTurn = true; // true -> turno del player		false -> turno del nemico
	private boolean skillsOpened = false;
	private AudioInputStream audioInputStream;
	private Clip clip;
	
	public FightPanel(Player player, Enemy enemy, JFrame frame, MapPanel mapPanel) {
		
			try {
				this.audioInputStream = AudioSystem.getAudioInputStream(new File("resources/fight.wav").getAbsoluteFile());
				this.clip = AudioSystem.getClip();
				clip.open(audioInputStream);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		clip.start();
		
		this.enemy = enemy;
		this.player = player;
		this.frame = frame;
		this.mapPanel = mapPanel;
		try {
			this.backgroundImage = ImageIO.read(getClass().getResource("/Background.png"));
		} catch (IOException e) {
			System.out.println(e);
		}
		this.attack.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if(playersTurn) {
		        	player.attack(enemy);
		        	playersTurn = false;
		        }
		    }
		});
		this.skills.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        skillsPressed(attack, skills);
		    }
		});
		this.add(attack);
		this.add(skills);
		
		for(int i = 0; i < skillButtons.length; i++) {
			if(this.player.getSkills().size() > i) {
				skillButtons[i] = new JButton(this.player.getSkills().get(i).getName());
				this.addActionListener(i);
			} else skillButtons[i] = new JButton("???");
			
		}
	}
	private void addActionListener(int i) {
		this.skillButtons[i].addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(playersTurn) {
		    		player.useSkill(i, enemy);
		    		playersTurn = false;
		    	}
		    }
		});
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
		
		//tasti skills e attack
		this.setButton(attack, getWidth()-getWidth()/5, getHeight()-getHeight()/5, getWidth()/5, getHeight()/5, new Color(59, 89, 182), Color.WHITE);
		this.setButton(skills, getWidth()-getWidth()*2/5, getHeight()-getHeight()/5, getWidth()/5, getHeight()/5, new Color(59, 89, 182), Color.WHITE);
		
		//mostra le varie skill se premuto skill
		for(int i = 0; i < skillButtons.length; i++) {
			if(this.skillButtons[i] != null && this.skillButtons[i].isVisible()) {
				this.setButton(this.skillButtons[i], getWidth()-getWidth()*2/5, getHeight()-getHeight()/5-getHeight()*(i+1)/20, getWidth()/5, getHeight()/20, new Color(59, 89, 182), Color.WHITE);
				
			}
		}
		if(this.enemy.getCurrentHP() <= 0) {
			this.frame.setContentPane(this.mapPanel);
			this.mapPanel.requestFocus();
			if(this.enemy.getId() < 4) {
				this.enemy.setSkill(SkillDatabase.getHeal());
				this.player.learnSkill(this.enemy.getSkills().get(0));
			}
			this.clip.stop();
			this.player.giveExp(this.enemy.getExp());
			this.player.giveGold(this.enemy.getGold());
			this.player.setCurrentHP(this.player.getCurrentHP()+this.player.getTotalHP()/4);
		} else {
			if(!this.playersTurn) {
				this.enemy.attack(player);
				this.playersTurn = true;
			}
		}
	}
	private void setButton(JButton button, int xPos, int yPos, int width, int height, Color backgroundColor, Color String) {
		button.setBounds(xPos, yPos, width, height);
		button.setBackground(backgroundColor);
		button.setForeground(String);
		button.setFocusPainted(false);
		button.setFont(new Font("Tahoma", Font.BOLD, 20));
		button.setVisible(true);
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
