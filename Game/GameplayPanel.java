package Game;

import Game.utils.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameplayPanel extends JPanel implements Runnable {
    public static int width;
    public static int height;
    private Thread thread;
    private boolean running = false;

    private BufferedImage img;
    private Graphics2D g;
    private Image backgroundImage;

    private KeyHandler key;

    private Game game;

    public GameplayPanel(int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("img/background.png"));
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        running = true;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();

        key = new KeyHandler(this);

        game = new Game();
    }

    public void update() {
        game.update();
    }

    public void input(KeyHandler key) {
        game.input(key);
    }

    public void render() {
        if (g != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, null);
            game.render(g);
        }
    }


    public void draw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
    }

    @Override
    public void run() {
        init();


        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ;

        final int MUBR = 5;

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60.0;
        final double TTBR = 1000000000 / TARGET_FPS;

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        int oldFrameCount = 0;

        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;
            while ((now - lastUpdateTime) > TBU && (updateCount < MUBR)) {
                input(key);
                update();
                lastUpdateTime += TBU;
                updateCount++;
            }

            if (now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            render();
            draw();
            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                if (frameCount != oldFrameCount) {
                    //System.out.println("FPS : " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while ((now - lastRenderTime < TTBR) && (now - lastUpdateTime < TBU)) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.err.println("ERROR yielding thread");
                }

                now = System.nanoTime();
            }
        }
    }
}