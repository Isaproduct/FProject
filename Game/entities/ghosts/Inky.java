package Game.entities.ghosts;

import Game.Game;
import Game.ghostStrategies.InkyStrategy;

public class Inky extends Ghost {
    public Inky(int xPos, int yPos) {
        super(xPos, yPos, "inky.png");
        setStrategy(new InkyStrategy(Game.getBlinky()));
    }
}
