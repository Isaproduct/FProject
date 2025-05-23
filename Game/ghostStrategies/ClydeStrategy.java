package Game.ghostStrategies;

import Game.Game;
import Game.entities.ghosts.Ghost;
import Game.utils.Utils;
import Game.GameplayPanel;


public class ClydeStrategy implements IGhostStrategy{
    private Ghost ghost;
    public ClydeStrategy(Ghost ghost) {
        this.ghost = ghost;
    }

    @Override
    public int[] getChaseTargetPosition() {
        if (Utils.getDistance(ghost.getxPos(), ghost.getyPos(), Game.getPacman().getxPos(), Game.getPacman().getyPos()) >= 256) {
            int[] position = new int[2];
            position[0] = Game.getPacman().getxPos();
            position[1] = Game.getPacman().getyPos();
            return position;
        }else{
            return getScatterTargetPosition();
        }
    }

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = GameplayPanel.height;
        return position;
    }
}
