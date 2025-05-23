package Game;

import Game.entities.*;
import Game.ghostsFactory.*;

import Game.entities.*;
import Game.entities.ghosts.Blinky;
import Game.entities.ghosts.Ghost;
import Game.ghostsFactory.*;
import Game.ghostStates.EatenMode;
import Game.ghostStates.FrightenedMode;
import Game.utils.CollisionDetector;
import Game.utils.CsvReader;
import Game.utils.KeyHandler;

import java.awt.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Game implements Observer {
    private List<Entity> objects = new ArrayList();
    private List<Ghost> ghosts = new ArrayList();
    private static List<Wall> walls = new ArrayList();

    private static Pacman pacman;
    private static Blinky blinky;

    private static boolean firstInput = false;

    public Game(){

        List<List<String>> data = null;
        try {
            data = new CsvReader().parseCsv(getClass().getClassLoader().getResource("level/level.csv").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int cellsPerRow = data.get(0).size();
        int cellsPerColumn = data.size();
        int cellSize = 8;

        CollisionDetector collisionDetector = new CollisionDetector(this);
        AbstractGhostFactory abstractGhostFactory = null;

        for(int xx = 0 ; xx < cellsPerRow ; xx++) {
            for(int yy = 0 ; yy < cellsPerColumn ; yy++) {
                String dataChar = data.get(yy).get(xx);
                if (dataChar.equals("x")) { //Création des murs
                    objects.add(new Wall(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("P")) { //Création de Pacman
                    pacman = new Pacman(xx * cellSize, yy * cellSize);
                    pacman.setCollisionDetector(collisionDetector);

                    pacman.registerObserver(GameLauncher.getUIPanel());
                    pacman.registerObserver(this);
                }else if (dataChar.equals("b") || dataChar.equals("p") || dataChar.equals("i") || dataChar.equals("c")) {
                    switch (dataChar) {
                        case "b":
                            abstractGhostFactory = new BlinkyFactory();
                            break;
                        case "p":
                            abstractGhostFactory = new PinkyFactory();
                            break;
                        case "i":
                            abstractGhostFactory = new InkyFactory();
                            break;
                        case "c":
                            abstractGhostFactory = new ClydeFactory();
                            break;
                    }

                    Ghost ghost = abstractGhostFactory.makeGhost(xx * cellSize, yy * cellSize);
                    ghosts.add(ghost);
                    if (dataChar.equals("b")) {
                        blinky = (Blinky) ghost;
                    }
                }else if (dataChar.equals(".")) { //Création des PacGums
                    objects.add(new PacGum(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("o")) { //Création des SuperPacGums
                    objects.add(new SuperPacGum(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("-")) { //Création des murs de la maison des fantômes
                    objects.add(new GhostHouse(xx * cellSize, yy * cellSize));
                }
            }
        }
        objects.add(pacman);
        objects.addAll(ghosts);

        for (Entity o : objects) {
            if (o instanceof Wall) {
                walls.add((Wall) o);
            }
        }
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public List<Entity> getEntities() {
        return objects;
    }

    public void update() {
        for (Entity o: objects) {
            if (!o.isDestroyed()) o.update();
        }
    }

    public void input(KeyHandler k) {
        pacman.input(k);
    }

    public void render(Graphics2D g) {
        for (Entity o: objects) {
            if (!o.isDestroyed()) o.render(g);
        }
    }

    public static Pacman getPacman() {
        return pacman;
    }
    public static Blinky getBlinky() {
        return blinky;
    }

    @Override
    public void updatePacGumEaten(PacGum pg) {
        pg.destroy();
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        spg.destroy();
        for (Ghost gh : ghosts) {
            gh.getState().superPacGumEaten();
        }
    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (gh.getState() instanceof FrightenedMode) {
            gh.getState().eaten();
        }else if (!(gh.getState() instanceof EatenMode)) {
            System.out.println("Game over !\nScore : " + GameLauncher.getUIPanel().getScore());
            System.exit(0); //TODO
        }
    }

    public static void setFirstInput(boolean b) {
        firstInput = b;
    }

    public static boolean getFirstInput() {
        return firstInput;
    }
}
