package java.game.ghostStrategies;

public interface IGhostStrategy {
    int[] getChaseTargetPosition(); //Case ciblée lorsque le fantôme poursuit Pacman
    int[] getScatterTargetPosition(); //Case ciblée lorsque le fantôme fait une pause
}
