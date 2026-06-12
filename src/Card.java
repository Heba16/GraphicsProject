import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private BufferedImage image;
    private Rectangle hitbox;
    private boolean highlight;

    public Card(String suit, String value) {
        this.highlight = false;
        this.suit = suit;
        this.value = value;
        this.imageFileName = "card_"+suit+"_"+value+".png";
        this.image = readImage();
        this.hitbox = new Rectangle(-10, -10, image.getWidth(), image.getHeight());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean getHighlight() {
        return this.highlight;
    }

    public void flipHighlight() {
        this.highlight = !this.highlight;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public String getSuit() {
        return suit;
    }


    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }


    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() {
        try {
            File f = new File("images/" + imageFileName);

            System.out.println("Loading: " + f.getName());

            BufferedImage img = ImageIO.read(f);

            System.out.println("Result image = " + img);

            return img;
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
            return null;
        }
    }

}