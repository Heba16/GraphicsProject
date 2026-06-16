import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;

class DrawPanel extends JPanel implements MouseListener {

    private Deck d;
    private Card[][] cards;
    int countHighlight = 0;
    ArrayList<Card> cardsChosen = new ArrayList<>();
    ArrayList<Integer> cardsRows = new ArrayList<>();
    ArrayList<Integer> cardsColumns = new ArrayList<>();
    int cardSum = 0;
    boolean switchCards = false;
    boolean continueGame;
    boolean finishedGame = false;

    private Rectangle replaceHitbox;
    private Rectangle playAgainHitbox;

    mySound sound = new mySound();

    public DrawPanel() {
        cards = new Card[3][3];
        d = new Deck();
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards.length; c++) {
                cards[r][c] = d.getRandomCard();
            }
        }

        checkDeck();
        this.addMouseListener(this);
    }
    //check any possible pairs that add to 11 or have a Jack, King, and Queen in an entire deck
    public void checkDeck(){
        boolean containsSix = false;
        boolean containsFive = false;

        boolean containsJack = false;
        boolean containsQueen = false;
        boolean containsKing = false;

        boolean containsTen = false;
        boolean containsAce = false;

        boolean containsSeven = false;
        boolean containsFour = false;

        boolean containsEight = false;
        boolean containsThree = false;

        boolean containsNine = false;
        boolean containsTwo = false;

        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards[r].length; c++) {
                String value = cards[r][c].getValue();
                if (value.equals("A")) {
                    containsAce = true;
                } else if (value.equals("J")) {
                    containsJack = true;
                } else if (value.equals("Q")) {
                    containsQueen = true;
                }else if (value.equals("K")) {
                    containsKing = true;
                } else {
                    int num = Integer.parseInt(value);

                    if (num == 6){
                        containsSix = true;
                    } else if (num == 5){
                        containsFive = true;
                    }  else if (num == 7){
                        containsSeven = true;
                    } else if (num == 3){
                        containsThree = true;
                    } else if (num == 4){
                        containsFour = true;
                    } else if (num == 8){
                        containsEight = true;
                    } else if (num == 9){
                        containsNine = true;
                    } else if (num == 10){
                        containsTen = true;
                    } else if (num == 2){
                        containsTwo = true;
                    }
                }
            }
        }
        if(containsAce && containsTen || containsFive && containsSix || containsEight && containsThree || containsSeven && containsFour || containsNine && containsTwo || containsKing && containsQueen && containsJack){
            continueGame = true;
        } else {
            continueGame = false;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards.length; c++) {
                g.drawImage(cards[r][c].getImage(), x, y, null);
                Rectangle cardHitBox = new Rectangle(x, y, cards[r][c].getImage().getWidth(), cards[r][c].getImage().getHeight());
                cards[r][c].setHitbox(cardHitBox);
                if (cards[r][c].getHighlight()){
                    g.drawRect(x, y, (int)cardHitBox.getWidth(), (int)cardHitBox.getHeight());
                }
                x += 80;



            }
            y += 100;
            x = 50;
        }

        if(!continueGame){
            g.drawString("game over ", 300, 200);
        }
        g.drawString("Number of cards left: " + d.getDeck().size(), x, y + 100);
        g.drawString("Play Again", 300, 300);
        playAgainHitbox = new Rectangle(300, 280, 160, 30);
        g.drawString("Replace Cards", 300, 100);
        replaceHitbox = new Rectangle(300, 80, 160, 30);

        if(finishedGame){
            g.drawString("Good Job!", 100, 100);
            continueGame = false;
        }
    }

    //adding sound effects
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void resetGame() {
        d = new Deck();
        playSE(2);
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards[r].length; c++) {
                cards[r][c] = d.getRandomCard();
            }
        }
        cardsChosen.clear();
        cardsRows.clear();
        cardsColumns.clear();
        cardSum = 0;
        countHighlight = 0;
        continueGame = true;
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        if(d.getDeck().size() == 0){
            finishedGame = true;
        }
        //if player presses Play Again, the reset method will run
        if (playAgainHitbox != null && playAgainHitbox.contains(e.getPoint())) {
            resetGame();
            checkDeck();
            return;
        }

        //if the deck is empty of possible pairs, do not allow for any mouse response
        if (!continueGame) {
            return;
        }
            Point p = e.getPoint();
            int button = e.getButton();
            System.out.println(countHighlight);

            for (int r = 0; r < cards.length; r++) {
                for (int c = 0; c < cards.length; c++) {
                //see what cards are left-click selected and create a number to represent their sum
                    if (button == 3 && cards[r][c].getHitbox().contains(p)) {
                        cards[r][c].flipHighlight();
                        countHighlight++;
                        playSE(0);
                        if (cards[r][c].getValue().equals("J")) {
                            cardsChosen.add(cards[r][c]);
                            cardsRows.add(r);
                            cardsColumns.add(c);
                            cardSum += 11;
                        } else if (cards[r][c].getValue().equals("K")) {
                            cardsChosen.add(cards[r][c]);
                            cardsRows.add(r);
                            cardsColumns.add(c);
                            cardSum += 13;
                        } else if (cards[r][c].getValue().equals("Q")) {
                            cardsChosen.add(cards[r][c]);
                            cardsRows.add(r);
                            cardsColumns.add(c);
                            cardSum += 12;
                        } else if (cards[r][c].getValue().equals("A")) {
                            cardsChosen.add(cards[r][c]);
                            cardsRows.add(r);
                            cardsColumns.add(c);
                            cardSum += 1;
                        } else {
                            cardsChosen.add(cards[r][c]);
                            cardsRows.add(r);
                            cardsColumns.add(c);
                            cardSum += Integer.parseInt(cards[r][c].getValue());
                        }
                        System.out.println(cardSum);
                    }
                }
            }
            //Figure out if the selected cards add up to 11 or contain Jack, King, and Queen
            if (replaceHitbox != null && replaceHitbox.contains(e.getPoint())) {
                System.out.println("pressed");
                if (cardSum == 11 && countHighlight == 2) {
                    for (Card card : cardsChosen) {
                        card.flipHighlight();
                    }
                    for (int i = 0; i < cardsRows.size(); i++) {
                        int c = cardsColumns.get(i);
                        int r = cardsRows.get(i);

                        cards[r][c] = d.getRandomCard();
                        playSE(1);
                    }
                    checkDeck();
                    cardsChosen.clear();
                    cardsRows.clear();
                    cardsColumns.clear();
                    countHighlight = 0;
                    cardSum = 0;
                    System.out.println("got 11");
                    switchCards = true;
                } else if (cardSum == 36 && countHighlight == 3) {
                    for (Card card : cardsChosen) {
                        card.flipHighlight();
                    }
                    for (int i = 0; i < cardsRows.size(); i++) {
                        int r = cardsRows.get(i);
                        int c = cardsColumns.get(i);
                        cards[r][c] = d.getRandomCard();
                        playSE(1);
                    }
                    checkDeck();
                    cardsChosen.clear();
                    cardsRows.clear();
                    cardsColumns.clear();
                    countHighlight = 0;
                    cardSum = 0;
                    System.out.println("got JKQ");
                } else {
                    System.out.println("WRONG");
                    for (Card card : cardsChosen) {
                        card.flipHighlight();
                    }
                    cardsChosen.clear();
                    countHighlight = 0;
                    cardSum = 0;
                }
            }
    }


    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}
