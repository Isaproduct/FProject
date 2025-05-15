package Game;

import Game.entities.PacGum;
import Game.entities.SuperPacGum;
import Game.entities.ghosts.Ghost;

public interface Observer {
    void updatePacGumEaten(PacGum pg);
    void updateSuperPacGumEaten(SuperPacGum spg);
    void updateGhostCollision(Ghost gh);
}
