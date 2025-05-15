package java.game.ghostsFactory;

import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;


public class InkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Inky(xPos, yPos);
    }
}
