package Game.ghostsFactory;

import Game.entities.ghosts.Ghost;
import Game.entities.ghosts.Inky;


public class InkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Inky(xPos, yPos);
    }
}
