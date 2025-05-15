package java.game.ghostsFactory;

import java.game.entities.ghosts.Ghost;
import java.game.entities.ghosts.Inky;


public class InkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Inky(xPos, yPos);
    }
}
