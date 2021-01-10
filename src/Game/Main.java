package Game;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // initializing and for debugging
        ArrayList<Person> players = new ArrayList<>();

        Computer player2 = new Computer("Linda");
        Computer player3 = new Computer("Martin");
        Computer player4 = new Computer("Robin");
        players.add(player2);
        players.add(player3);
        players.add(player4);
        Dealer dealer = new Dealer("Svarte Petter", players);
        String wishToContinue = "";

        // Here starts the game
        System.out.println(dealer.getName() + " \"Hi im your dealer of the night, whats your name?\"");
        players.add(0, new Player(in.nextLine()));

        // first round of the game, before dealer acts everyone must get their two cards
        while (!(wishToContinue.equals("n"))) {
            dealer.doRoundOne();
            while (!dealer.isAWinner()) {
                // Players act
                while (dealer.breakGame()) {
                    dealer.doGameRound();
                }
                // Dealer act
                dealer.doGameRound();
            }

            System.out.println("Another game? [Y / N]");
            wishToContinue = in.nextLine().toLowerCase();
            if (wishToContinue.equals("y")) {
                dealer.clearPlayersForNewRound();
            } else {
                System.out.println("Goodbye!");
            }
        }
    }

}
