import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // debugging
        ArrayList<Person> players = new ArrayList<>();
        Player player1 = new Player("Alex");
        Computer player2 = new Computer("Linda");

        players.add(player1);
        players.add(player2);

        Dealer dealer = new Dealer("Svarte Petter", players);

        // place bets
        dealer.dealOneCard();
        dealer.dealOneCard();
        // end of first round

        for (Person player : dealer.getTable().getPlayers()) {
            System.out.println(player.getName());
            System.out.println(player.getSum());
        }

        dealer.checkIfBust();
        dealer.checkHitOrStand();
        dealer.dealOneCard();

        for (Person player : dealer.getTable().getPlayers()) {
            System.out.println(player.getName());
            System.out.println(player.getSum());
        }
        dealer.checkIfBust();
        dealer.checkHitOrStand();

        for (Person player : dealer.getTable().getPlayers()) {
            System.out.println(player.getName());
            System.out.println(player.getSum());
        }

        // Check if continue or stand

        // Give one card

        // check if bust

        // give one card

        // Winner gets all the money
    }
}
