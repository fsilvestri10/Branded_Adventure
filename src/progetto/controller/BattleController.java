package progetto.controller;

import javax.swing.Timer;

import progetto.model.Battle;
import progetto.view.FightPanel;

/**
 * CONTROLLER: collega i pulsanti di FightPanel alla Battle (model) e scandisce i turni.
 * Le regole (danni, vittoria, sconfitta) stanno in Battle: qui si decide solo quando applicarle.
 */
public class BattleController {

    private static final int ATTACK_TIME = 1; // millisecondi tra un'azione e l'altra

    private final Battle battle;
    private final Runnable onWon;
    private final Runnable onLost;

    public BattleController(Battle battle, FightPanel panel, Runnable onWon, Runnable onLost) {
        this.battle = battle;
        this.onWon = onWon;
        this.onLost = onLost;

        // se viene premuto attacco o un'abilità, il giocatore agisce (se è il suo turno)
        panel.setOnAttack(() -> {
            if (this.battle.playerAttack()) schedule(this::playerActs);
        });
        panel.setOnSkill(index -> {
            if (this.battle.playerSkill(index)) schedule(this::playerActs);
        });
    }

    private void playerActs() {
        this.battle.resolvePlayerAction();
        if (this.battle.getState() == Battle.State.WON) {
            this.battle.applyRewards();
            this.onWon.run();
        } else {
            // se dopo aver agito il nemico è ancora vivo, allora agisce a sua volta
            schedule(this::enemyActs);
        }
    }

    private void enemyActs() {
        this.battle.resolveEnemyAction();
        if (this.battle.getState() == Battle.State.LOST) this.onLost.run();
    }

    // esegue l'azione dopo una breve pausa, una volta sola
    private void schedule(Runnable action) {
        Timer timer = new Timer(ATTACK_TIME, e -> action.run());
        timer.setRepeats(false);
        timer.start();
    }
}
