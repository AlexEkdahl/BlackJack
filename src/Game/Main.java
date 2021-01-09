package Game;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // initializing  
        ArrayList<Person> players = new ArrayList<>();
        Player player1 = new Player("Alex");
        Computer player2 = new Computer("Linda");
        Computer player3 = new Computer("Martin");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        Dealer dealer = new Dealer("Svarte Petter", players);

        //TODO implement methods to handle bets and bidds

        // [First round]
        dealer.dealOneCard();
        dealer.dealOneCard();
        // [end of first round]

        // Black Jack Game
        while (!dealer.isAWinner()) {
            dealer.printPlayersCards();
            dealer.checkIfBust();
            dealer.checkHitOrStand();
            dealer.checkIfBust();
            dealer.dealOneCard();
            dealer.checkIfBust();
        }
    }
}
