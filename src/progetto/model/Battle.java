package progetto.model;

/**
 * MODEL: lo scontro a turni tra giocatore e nemico.
 * Contiene turni, stato e ricompense; non sa nulla di pulsanti, timer o schermate.
 * Il controller chiama i metodi nell'ordine giusto, la view legge solo stato e messaggio.
 */
public class Battle {

    public enum State {
        PLAYER_TURN,   // il giocatore può scegliere un'azione
        PLAYER_ACTING, // il giocatore ha scelto, l'azione sta per essere applicata
        ENEMY_TURN,    // il nemico sta per attaccare
        WON,
        LOST
    }

    private final Player player;
    private final Enemy enemy;
    private State state = State.PLAYER_TURN;
    private String message = "È il tuo turno, scegli un'azione!";
    private int pendingSkill = -1; // -1 = attacco normale, altrimenti indice dell'abilità

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    /** Il giocatore sceglie di attaccare. False se non è il suo turno. */
    public boolean playerAttack() {
        if (this.state != State.PLAYER_TURN) return false;
        this.state = State.PLAYER_ACTING;
        this.message = "Il giocatore attacca!";
        this.pendingSkill = -1;
        return true;
    }

    /** Il giocatore sceglie di usare un'abilità. False se non è il suo turno o l'abilità non esiste. */
    public boolean playerSkill(int index) {
        if (this.state != State.PLAYER_TURN) return false;
        if (index < 0 || index >= this.player.getSkills().size()) return false;
        this.state = State.PLAYER_ACTING;
        this.message = "Il giocatore usa un'abilità!";
        this.pendingSkill = index;
        return true;
    }

    /** Applica l'azione scelta dal giocatore. Se il nemico è ancora vivo, tocca a lui. */
    public void resolvePlayerAction() {
        if (this.state != State.PLAYER_ACTING) return;
        if (this.pendingSkill < 0) this.player.attack(this.enemy);
        else this.player.useSkill(this.pendingSkill, this.enemy);

        if (this.enemy.isDead()) {
            this.state = State.WON;
            return;
        }
        this.message = "Il nemico attacca!";
        this.state = State.ENEMY_TURN;
    }

    /** Il nemico attacca. Se il giocatore è ancora vivo torna il suo turno. */
    public void resolveEnemyAction() {
        if (this.state != State.ENEMY_TURN) return;
        this.enemy.attack(this.player);

        if (this.player.isDead()) {
            this.state = State.LOST;
            return;
        }
        this.message = "È il tuo turno, scegli un'azione!";
        this.state = State.PLAYER_TURN;
    }

    /** Ricompense per la vittoria (da chiamare una volta sola, quando lo stato è WON). */
    public void applyRewards() {
        if (this.enemy.isBoss() && !this.enemy.getSkills().isEmpty()) {
            // se il nemico sconfitto è un boss, il giocatore riceve la relativa skill
            this.player.learnSkill(this.enemy.getSkills().get(0));
        }
        // il giocatore riceve le ricompense dopo aver vinto
        this.player.giveExp(this.enemy.getExp());
        this.player.giveGold(this.enemy.getGold());
        this.player.heal(this.player.getTotalHP() / 4);
    }

    public Player getPlayer() {
        return this.player;
    }

    public Enemy getEnemy() {
        return this.enemy;
    }

    public State getState() {
        return this.state;
    }

    public String getMessage() {
        return this.message;
    }
}
