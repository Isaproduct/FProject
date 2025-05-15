package Game.ghostsFactory;

import Game.entities.ghosts.Ghost;
import Game.entities.ghosts.Pinky;


public class PinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Pinky(xPos, yPos);
    }
}
