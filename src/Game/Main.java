package Game;

import java.io.IOException;
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
        clearScreen();
        System.out.println(dealer.getName() + " \"Hi im your dealer of the night, whats your name?\"");
        players.add(0, new Player(in.nextLine()));

        // first round of the game, before dealer acts everyone must get their two cards
        // Dont need the creation of a player in the game loop
        while (!(wishToContinue.equals("n"))) {
            dealer.dealOneCard();
            dealer.setDealersTurnToAct(false);
            dealer.dealOneCard();
            clearScreen();

            while (!dealer.isAWinner()) {
                // Players act
                while (dealer.breakGame()) {
                    dealer.printPlayersCards();
                    System.out.println();
                    dealer.playerCheckHitOrStand();
                    System.out.println();
                    dealer.dealOneCard();
                }
                //Dealer act
                dealer.printPlayersCards();
                System.out.println();
                dealer.playerCheckHitOrStand();
                System.out.println();
                dealer.dealOneCard();
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

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
        }
    }
}
