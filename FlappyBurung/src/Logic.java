import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    // frame dimensions
    int frameWidth = 360;
    int frameHeight = 640;

    // player start position and size
    int playerStartPosX = frameWidth / 2 - 15;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 36, playerHeight = 24;

    // pipe start position and size
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // game state
    boolean gameOver = false;
    boolean started = false;
    int score = 0;

    // references
    View view;
    Image birdImage;
    Player player;
    Image lowerPipeImage, upperPipeImage;
    ArrayList<Pipe> pipes;

    // timers
    Timer gameLoop, pipesCooldown;
    // game parameters
    float gravity = 0.4f;
    int pipeVelocityX = -2;

    // constructor
    public Logic() {
        // load assets
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        // load pipe images
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<>();

        // pipe placement timer
        pipesCooldown = new Timer(1500, e -> placePipes());
        pipesCooldown.stop();

        // main game loop timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // setter for view
    public void setView(View view) {
        this.view = view;
    }

    // getter for player
    public Player getPlayer() {
        return player;
    }

    // getter for pipes
    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    // place pipes at random vertical positions
    public void placePipes() {
        // random vertical position for the opening
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;
        // create upper and lower pipes
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(upperPipe);
        pipes.add(lowerPipe);
    }

    // update game state
    public void move() {
        // do nothing if game not started or over
        if (!started || gameOver) return;

        // update player position
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // update pipes position and check for collisions
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);
            // check collision
            if (checkCollision(player, pipe)) {
                gameOver = true;
                pipesCooldown.stop();
            }
            // update score
            if (!pipe.isPassed() && pipe.getImage() == upperPipeImage && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                pipe.setPassed(true);
                score++;
                view.setScore(score);
            }
        }

        // remove off-screen pipes
        if (!pipes.isEmpty() && pipes.get(0).getPosX() + pipes.get(0).getWidth() < 0) {
            pipes.remove(0);
            pipes.remove(0);
        }

        // check if player hits the ground
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            gameOver = true;
            pipesCooldown.stop();
        }
    }

    // check collision between player and pipe
    private boolean checkCollision(Player player, Pipe pipe) {
        // create rectangles for collision detection
        Rectangle p = new Rectangle((int) player.getPosX(), (int) player.getPosY(), player.getWidth(), player.getHeight());
        Rectangle q = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
        return p.intersects(q);
    }

    @Override
    // main game loop action
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) view.repaint();
    }

    // restart the game
    private void restartGame() {
        pipes.clear();
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        score = 0;
        view.setScore(score);
        gameOver = false;
        started = false;
    }

    // start the game
    public void startGame() {
        restartGame();
        gameOver = false;
        pipesCooldown.start();
    }

    @Override
    // key pressed event
    public void keyPressed(KeyEvent e) {
        if (!started && e.getKeyCode() == KeyEvent.VK_SPACE) {
            started = true;
            pipesCooldown.start();
        }

        // player jump
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) player.setVelocityY(-7f);
        } else {
            // game over controls
            if (e.getKeyCode() == KeyEvent.VK_R) restartGame(); // restart game
            else if (e.getKeyCode() == KeyEvent.VK_M) {
                // return to menu
                restartGame();
                view.showMenu();
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
