package Game.entities.ghosts;

import Game.ghostStrategies.ClydeStrategy;


public class Clyde extends Ghost {
    public Clyde(int xPos, int yPos) {
        super(xPos, yPos, "clyde.png");
        setStrategy(new ClydeStrategy(this));
    }
}
