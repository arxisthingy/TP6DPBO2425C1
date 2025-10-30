import java.awt.*;

public class Player {
    // player attributes
    private float posX;
    private float posY;
    private int width;
    private int height;
    private float velocityY;
    private Image image;

    // constructor
    public Player(float posX, float posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityY = 0;
    }

    // getter setter
    public float getPosX() { return posX; }
    public float getPosY() { return posY; }
    public void setPosX(float posX) { this.posX = posX; }
    public void setPosY(float posY) { this.posY = posY; }

    // velocity Y
    public float getVelocityY() { return velocityY; }
    public void setVelocityY(float velocityY) { this.velocityY = velocityY; }

    // width and height
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Image getImage() { return image; }
}
