package Game.ghostsFactory;

import Game.entities.ghosts.Ghost;

import Game.entities.ghosts.*;


public abstract class AbstractGhostFactory {
    public abstract Ghost makeGhost(int xPos, int yPos);
}