package Game.entities.ghosts;

import Game.Game;
import Game.entities.MovingEntity;
import Game.ghostStates.*;
import Game.ghostStrategies.IGhostStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Ghost extends MovingEntity {
    protected GhostState state;

    // Конкретные состояния
    protected final GhostState chaseMode;
    protected final GhostState scatterMode;
    protected final GhostState frightenedMode;
    protected final GhostState eatenMode;
    protected final GhostState houseMode;

    protected int modeTimer = 0;
    protected int frightenedTimer = 0;
    protected boolean isChasing = false;

    protected static BufferedImage frightenedSprite1;
    protected static BufferedImage frightenedSprite2;
    protected static BufferedImage eatenSprite;

    protected IGhostStrategy strategy;

    public Ghost(int xPos, int yPos, String spriteName) {
        super(32, xPos, yPos, 2, spriteName, 2, 0.1f);

        // Инициализация всех состояний
        chaseMode = new ChaseMode(this);
        scatterMode = new ScatterMode(this);
        frightenedMode = new FrightenedMode(this);
        eatenMode = new EatenMode(this);
        houseMode = new HouseMode(this);

        state = houseMode; // начальное состояние

        try {
            frightenedSprite1 = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_frightened.png"));
            frightenedSprite2 = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_frightened_2.png"));
            eatenSprite = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_eaten.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Методы смены состояния (State Pattern)
    public void switchChaseMode() {
        state = chaseMode;
        modeTimer = 0;
    }

    public void switchScatterMode() {
        state = scatterMode;
        modeTimer = 0;
    }

    public void switchFrightenedMode() {
        frightenedTimer = 0;
        state = frightenedMode;
    }

    public void switchEatenMode() {
        state = eatenMode;
    }

    public void switchHouseMode() {
        state = houseMode;
    }

    public void switchChaseModeOrScatterMode() {
        if (isChasing) {
            switchChaseMode();
        } else {
            switchScatterMode();
        }
        isChasing = !isChasing;
    }

    // Strategy getter/setter
    public IGhostStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IGhostStrategy strategy) {
        this.strategy = strategy;
    }

    public GhostState getState() {
        return state;
    }

    @Override
    public void update() {
        if (!Game.getFirstInput()) return;

        if (state == frightenedMode) {
            frightenedTimer++;
            if (frightenedTimer >= (60 * 7)) {
                state.timerFrightenedModeOver(); // Использование состояния
            }
        }

        if (state == chaseMode || state == scatterMode) {
            modeTimer++;
            if ((isChasing && modeTimer >= (60 * 20)) || (!isChasing && modeTimer >= (60 * 5))) {
                state.timerModeOver(); // Использование состояния
            }
        }

        if (xPos == 208 && yPos == 168) {
            state.outsideHouse();
        }

        if (xPos == 208 && yPos == 200) {
            state.insideHouse();
        }

        // State Pattern: делегируем направление текущему состоянию
        state.computeNextDir();
        updatePosition();
    }

    @Override
    public void render(Graphics2D g) {
        if (state == frightenedMode) {
            if (frightenedTimer <= (60 * 5) || frightenedTimer % 20 > 10) {
                g.drawImage(frightenedSprite1.getSubimage((int) subimage * size, 0, size, size), xPos, yPos, null);
            } else {
                g.drawImage(frightenedSprite2.getSubimage((int) subimage * size, 0, size, size), xPos, yPos, null);
            }
        } else if (state == eatenMode) {
            g.drawImage(eatenSprite.getSubimage(direction * size, 0, size, size), xPos, yPos, null);
        } else {
            g.drawImage(sprite.getSubimage((int) subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size), xPos, yPos, null);
        }
    }
}

