import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // debugging to follow my code as it progress
        ArrayList<Person> players = new ArrayList<>();
        Player player1 = new Player("Alex");
        Computer player2 = new Computer("Linda");

        players.add(player1);
        players.add(player2);

        Dealer dealer = new Dealer("Svarte Petter", players);

        //TODO place bets


        // First round
        dealer.dealOneCard();
        for (Person player : dealer.getTable().getPlayers()) {
            System.out.println(player.getName() + ", got first card");
            //Dont know why there has to be a try-catch
            //"pauses" the print in terminal to make it look more interactive
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dealer.dealOneCard();
        for (Person player : dealer.getTable().getPlayers()) {
            System.out.println(player.getName() + ", got second card");
            //Dont know why there has to be a try-catch
            //"pauses" the print in terminal to make it look more interactive
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // end of first round

        // TODO finish method to check for winner
        // Loop starts
        while (!dealer.isAWinner()) {
            for (Person player : dealer.getTable().getPlayers()) {
                if (player.getClass() == Player.class) {
                    System.out.println("\n" + player.getName());

                    System.out.println(player.showCards());
                } else if (player.getClass() == Dealer.class) {
                    //TODO somethings wrong, there is a /n between name and value
                    System.out.println("\n" + player.getName());
                    System.out.println(player.getCardList().get(0));
                }
            }
            // check if someone is bust and dont give any more cards
            dealer.checkIfBust();
            // check if players want more card and give them
            dealer.checkHitOrStand();
            dealer.dealOneCard();
        }
    }
}
