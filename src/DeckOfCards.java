import java.util.*;

public class DeckOfCards {

    private ArrayList<Card> cards = new ArrayList<Card>();

    // Ace is given only one value, 1. Maybe later implementations im able to give ace two values, 1 and 11.
    // ACE, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
    private int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10 };
    private String[] suit = { "Club", "Spade", "Diamond", "Heart" };

    public DeckOfCards() {
        for (int i = 0; i < suit.length; i++) {
            for (int j = 0; j < values.length; j++) {
                this.cards.add(new Card(suit[i], values[j]));
            }
        }
        // shuffle the deck after created
        Collections.shuffle(this.cards);
    }

    public ArrayList<Card> getCardsList() {
        return this.cards;
    }

    public Card giveOneCard(){
        return this.cards.get(0);
    }

    public void removeOneCard(){
        this.cards.remove(0);
    }

}