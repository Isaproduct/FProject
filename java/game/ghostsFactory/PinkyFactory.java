package java.game.ghostsFactory;

import java.game.entities.ghosts.Ghost;
import java.game.entities.ghosts.Pinky;


public class PinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Pinky(xPos, yPos);
    }
}
