package Game;

import Game.entities.PacGum;
import Game.entities.SuperPacGum;
import Game.entities.ghosts.Ghost;

public interface Sujet {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserverPacGumEaten(PacGum pg);
    void notifyObserverSuperPacGumEaten(SuperPacGum spg);
    void notifyObserverGhostCollision(Ghost gh);
}
