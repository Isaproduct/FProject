package Game.ghostsFactory;

import Game.entities.ghosts.Clyde;
import Game.entities.ghosts.Ghost;


public class ClydeFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Clyde(xPos, yPos);
    }
}
