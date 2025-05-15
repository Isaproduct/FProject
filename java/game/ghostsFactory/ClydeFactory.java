package jave.game.ghostFactory;

import java.game.entities.ghosts.Clyde;
import java.game.entities.ghosts.Ghost;


public class ClydeFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Clyde(xPos, yPos);
    }
}
