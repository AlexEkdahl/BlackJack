package Game;

import java.util.*;

public class Dealer extends Person {

   private DeckOfCards deck;
   private Table table;

   public Dealer(String name, ArrayList<Person> players) {
      super(name);
      this.deck = new DeckOfCards();
      this.table = new Table(players);
      this.table.addPlayer(this);

      // add "Dealer" to the dealers name
      this.setName("Dealer: " + name);
   }

   public Scanner input = new Scanner(System.in);

   public Table getTable() {
      return this.table;
   }

   public ArrayList<Person> getPlayers() {
      return this.table.getPlayers();
   }

   // Give everyone at the table a card and removes it from deck
   public void dealOneCard() {
      for (Person player : table.getPlayers()) {
         // check if player should get a card
         if (player.getNewCard() && !player.isBust()) {
            player.getOneCard(deck.giveOneCard());
            System.out.println(player.getName() + " got " + deck.giveOneCard().toString());
            this.deck.removeOneCard();

            // "Sleeps" the terminal for 800ms
            // Just for making the game feel a bit more interactive
            try {
               Thread.sleep(800);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
      System.out.println();
   }

   // choice of either take a new card och stay
   public void checkHitOrStand() {
      for (Person player : table.getPlayers()) {
         // users choice
         if (player.getClass() == Player.class && player.getNewCard()) {
            System.out.println("\nNew card or stay?" + "[Y / N]");
            String answer = input.nextLine().toLowerCase();
            switch (answer) {
               case "y":
                  player.setGetNewCard(true);
                  System.out.println(player.getName() + " said, hit me!");
                  break;
               case "n":
                  player.setGetNewCard(false);
                  System.out.println(player.getName() + "choose to stay");
                  break;
               default:
                  System.out.println("Wrong input");
                  break;
            }
         } else if (player.getClass() == Computer.class && player.getNewCard()) {
            if (player.getSum() >= 18) {
               player.setGetNewCard(false);
               // debugg
               System.out.println(player.getName() + "choose to stay");
            } else {
               player.setGetNewCard(true);
               System.out.println(player.getName() + " said, hit me!");
            }
            // In black jack, the dealer stop at 17.
         } else if (player.getClass() == Dealer.class && player.getNewCard()) {
            if (player.getSum() >= 17) {
               player.setGetNewCard(false);
               // debugg
               System.out.println(player.getName() + "must stop");
            } else {
               player.setGetNewCard(true);
               System.out.println(player.getName() + " have to continue");
            }
         }
      }
   }

   // Bust is when you have more then 21
   public void checkIfBust() {
      for (Person player : table.getPlayers()) {
         if (player.getSum() > 21) {
            System.out.println(player.getName() + "You're bust");
            player.setBust(true);
         }
      }
   }

   public boolean isAWinner() {
      for (Person player : table.getPlayers()) {
         if (player.getSum() == 21) {
            System.out.println(player.getName() + " won");
            printPlayersCards();

            // TODO player get money
            return true;
         } else if (player.getClass() == Dealer.class && player.isBust()) {
            // TODO betters split(?) get money
            System.out.println("Dealer is bust!");
            printPlayersCards();
            return true;
         } else if (breakGame()) {
            System.out.println("breakgame");
            return true;
         }

      }
      return false;

   }

   public void printPlayersCards() {
      for (Person player : getPlayers()) {
         if (player.getClass() == Dealer.class) {
            System.out.println("\n" + player.getName());
            System.out.println(player.showCards());
         } else {
            System.out.println("\n" + player.getName());
            System.out.println(player.showCards());
         }
      }
   }

   // check if all players.getNewCard == false
   // or all players.isBust == true // check if all players is eit
   public boolean breakGame() {
      int count = 0;
      for (Person player : this.table.getPlayers()) {
         if (player.isBust() || !player.getNewCard()) {
            count++;
         }
      }
      if (count == this.table.getPlayers().size()) {
         return true;
      }
      return false;
   }

   // TODO a method to check is someone got 21, if dealer bets players or player
   // beat dealer
   // note can only check of everyone is either stayed or busted
}
