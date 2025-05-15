package Game.ghostsFactory;

import Game.entities.ghosts.Blinky;
import Game.entities.ghosts.Ghost;


public class BlinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Blinky(xPos, yPos);
    }
}
