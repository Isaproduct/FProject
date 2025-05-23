package Game.entities;

import Game.Game;
import Game.Observer;
import Game.Sujet;
import Game.entities.ghosts.Ghost;
import Game.utils.CollisionDetector;
import Game.utils.KeyHandler;
import Game.utils.WallCollisionDetector;

import java.util.ArrayList;
import java.util.List;

public class Pacman extends MovingEntity implements Sujet {
    private CollisionDetector collisionDetector;
    private final List<Observer> observerCollection;

    public Pacman(int xPos, int yPos) {
        super(32, xPos, yPos, 2, "pacman.png", 4, 0.3f);
        observerCollection = new ArrayList<>();
    }

    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    public void input(KeyHandler k) {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!onTheGrid() || !onGameplayWindow()) return;

        if (k.k_left.isPressed && xSpd >= 0 && !WallCollisionDetector.checkWallCollision(this, -spd, 0)) {
            new_xSpd = -spd;
        } else if (k.k_right.isPressed && xSpd <= 0 && !WallCollisionDetector.checkWallCollision(this, spd, 0)) {
            new_xSpd = spd;
        } else if (k.k_up.isPressed && ySpd >= 0 && !WallCollisionDetector.checkWallCollision(this, 0, -spd)) {
            new_ySpd = -spd;
        } else if (k.k_down.isPressed && ySpd <= 0 && !WallCollisionDetector.checkWallCollision(this, 0, spd)) {
            new_ySpd = spd;
        }

        if (new_xSpd == 0 && new_ySpd == 0) return;

        if (!Game.getFirstInput()) Game.setFirstInput(true);

        if (Math.abs(new_xSpd) != Math.abs(new_ySpd)) {
            xSpd = new_xSpd;
            ySpd = new_ySpd;
        } else {
            // switch direction priority based on previous direction
            if (xSpd != 0) {
                xSpd = 0;
                ySpd = new_ySpd;
            } else {
                xSpd = new_xSpd;
                ySpd = 0;
            }
        }
    }

    @Override
    public void update() {
        // Check collisions and notify observers
        PacGum pg = (PacGum) collisionDetector.checkCollision(this, PacGum.class);
        if (pg != null) {
            notifyObserverPacGumEaten(pg);
        }

        SuperPacGum spg = (SuperPacGum) collisionDetector.checkCollision(this, SuperPacGum.class);
        if (spg != null) {
            notifyObserverSuperPacGumEaten(spg);
        }

        Ghost gh = (Ghost) collisionDetector.checkCollision(this, Ghost.class);
        if (gh != null) {
            notifyObserverGhostCollision(gh);
        }

        // Move if no wall collision
        if (!WallCollisionDetector.checkWallCollision(this, xSpd, ySpd)) {
            updatePosition();
        }
    }

    // === Observer Pattern Implementation ===
    @Override
    public void registerObserver(Observer observer) {
        if (!observerCollection.contains(observer)) {
            observerCollection.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObserverPacGumEaten(PacGum pg) {
        for (Observer obs : observerCollection) {
            obs.updatePacGumEaten(pg);
        }
    }

    @Override
    public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
        for (Observer obs : observerCollection) {
            obs.updateSuperPacGumEaten(spg);
        }
    }

    @Override
    public void notifyObserverGhostCollision(Ghost gh) {
        for (Observer obs : observerCollection) {
            obs.updateGhostCollision(gh);
        }
    }
}

