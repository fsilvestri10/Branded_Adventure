package progetto.view;

import java.awt.image.BufferedImage;

import progetto.model.Direction;

/** VIEW: animazione di camminata del giocatore (ritaglia i frame dallo sprite sheet). */
public class Sprite {
    private BufferedImage[] walkUp;
    private BufferedImage[] walkDown;
    private BufferedImage[] walkLeft;
    private BufferedImage[] walkRight;
    private int spriteNum = 0;
    private int spriteCounter = 0;
    private int spriteChange = 12;

    public Sprite(BufferedImage image) {
        // istanza e popolamento degli array per animazioni
        this.walkUp = new BufferedImage[3];
        this.walkDown = new BufferedImage[3];
        this.walkLeft = new BufferedImage[3];
        this.walkRight = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            this.walkUp[i] = image.getSubimage(i * 96, 288, 96, 96);
            this.walkDown[i] = image.getSubimage(i * 96, 0, 96, 96);
            this.walkLeft[i] = image.getSubimage(i * 96, 96, 96, 96);
            this.walkRight[i] = image.getSubimage(i * 96, 192, 96, 96);
        }
    }

    public BufferedImage getCurrentSprite(Direction direction) {
        switch (direction) {
            case UP:
                return this.walkUp[this.spriteNum];
            case DOWN:
                return this.walkDown[this.spriteNum];
            case LEFT:
                return this.walkLeft[this.spriteNum];
            case RIGHT:
                return this.walkRight[this.spriteNum];
            default:
                return null;
        }
    }

    /** Fa avanzare l'animazione se il giocatore cammina, altrimenti mostra il frame fermo. */
    public void update(boolean moving) {
        if (moving) {
            this.spriteCounter++;
            if (this.spriteCounter >= this.spriteChange) {
                this.spriteNum++;
                if (this.spriteNum >= 3) this.spriteNum = 0;
                this.spriteCounter = 0;
            }
        } else {
            this.spriteNum = 1;
        }
    }
}
