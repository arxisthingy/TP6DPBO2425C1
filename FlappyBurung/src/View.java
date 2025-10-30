import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.InputStream;

public class View extends JPanel {
    // dimensions
    int width = 360
    int height = 640;

    // reference to logic
    private Logic logic;
    private Image background;
    private int score = 0;
    private Font pixelFont; // pixel font

    // menu state
    private boolean showMenu = true;
    // menu buttons
    private JButton startButton, exitButton;

    // constructor
    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setLayout(null); // manual layout for button positioning
        background = new ImageIcon(getClass().getResource("assets/background.png")).getImage();

        // load pixel font
        try {
            InputStream is = getClass().getResourceAsStream("assets/pixel.ttf");
            // register font
            if (is != null) {
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(pixelFont);
            } else pixelFont = new Font("Monospaced", Font.BOLD, 24);
        } catch (Exception e) {
            pixelFont = new Font("Monospaced", Font.BOLD, 24);
        }

        // create buttons for main menu
        createMenuButtons();

        setFocusable(true);
        addKeyListener(logic);
    }

    // create menu buttons
    private void createMenuButtons() {
        startButton = new JButton("Start Game");
        exitButton = new JButton("Exit Game");

        // set button fonts
        startButton.setFont(pixelFont.deriveFont(15f));
        exitButton.setFont(pixelFont.deriveFont(15f));

        // position buttons (centered)
        startButton.setBounds(width / 2 - 100, height / 2 - 40, 200, 50);
        exitButton.setBounds(width / 2 - 100, height / 2 + 30, 200, 50);

        // start game action
        startButton.addActionListener(e -> {
            showMenu = false;
            startButton.setVisible(false);
            exitButton.setVisible(false);
            logic.startGame(); // call start method in logic
            requestFocusInWindow(); // refocus to capture key input
        });

        // exit game action
        exitButton.addActionListener(e -> System.exit(0));

        // add buttons to panel
        add(startButton);
        add(exitButton);
    }

    // set score
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    // paint component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showMenu) drawMenu(g);
        else drawGame(g);
    }

    // draw main menu
    private void drawMenu(Graphics g) {
        // draw background
        g.drawImage(background, 0, 0, width, height, null);
        // draw title text
        g.setColor(Color.WHITE);
        g.setFont(pixelFont.deriveFont(25f));
        String title = "FLAPPY GAME";
        g.drawString(title, (width - g.getFontMetrics().stringWidth(title)) / 2, height / 2 - 120);
    }

    // draw game elements
    private void drawGame(Graphics g) {
        // draw background
        g.drawImage(background, 0, 0, width, height, null);
        // draw player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), (int) player.getPosX(), (int) player.getPosY(), player.getWidth(), player.getHeight(), null);
        }

        // draw pipes
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // draw score
        g.setColor(Color.WHITE);
        g.setFont(pixelFont.deriveFont(24f));
        g.drawString("Score: " + score, 20, 40);

        // draw start / game over text
        try {
            // access private fields
            var started = Logic.class.getDeclaredField("started");
            var over = Logic.class.getDeclaredField("gameOver");
            started.setAccessible(true);
            over.setAccessible(true);
            boolean gameStarted = started.getBoolean(logic);
            boolean gameOver = over.getBoolean(logic);

            // draw text
            if (!gameStarted) {
                g.setColor(Color.WHITE);
                g.setFont(pixelFont.deriveFont(18f));
                String startText = "Press SPACE";
                g.drawString(startText, (width - g.getFontMetrics().stringWidth(startText)) / 2, height / 2 - 20);
                g.setFont(pixelFont.deriveFont(18f));
                String startText2 = "to Start";
                g.drawString(startText2, (width - g.getFontMetrics().stringWidth(startText2)) / 2, height / 2 + 60);
            }
            if (gameOver) {
                g.setColor(Color.WHITE);
                g.setFont(pixelFont.deriveFont(18f));
                String gameOverText = "GAME OVER";
                g.drawString(gameOverText, (width - g.getFontMetrics().stringWidth(gameOverText)) / 2, height / 2 - 30);
                g.setFont(pixelFont.deriveFont(18f));
                String restart = "Press R to Restart";
                g.drawString(restart, (width - g.getFontMetrics().stringWidth(restart)) / 2, height / 2);
                g.setFont(pixelFont.deriveFont(18f));
                String menu = "Press M for Menu";
                g.drawString(menu, (width - g.getFontMetrics().stringWidth(menu)) / 2, height / 2 + 30);
            }
        } catch (Exception ignored) {}
    }

    // show menu again (used when pressing M after game over)
    public void showMenu() {
        showMenu = true;
        startButton.setVisible(true);
        exitButton.setVisible(true);
        repaint();
    }
}
