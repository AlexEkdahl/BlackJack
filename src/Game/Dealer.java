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
            waitMilliSeconds(700);
         }
      }
      System.out.println();
   }

   // choice of either take a new card or stay
   public void checkHitOrStand() {
      for (Person player : table.getPlayers()) {

         // Branches of to check if its a object of Player and wants a new card
         if (player.getClass() == Player.class && player.getNewCard()) {
            System.out.println("\nNew card or stay?" + "[Y / N]");
            String answer = input.nextLine().toLowerCase();
            switch (answer) {
               case "y":
                  player.setGetNewCard(true);
                  System.out.println(player.getName() + " said, hit me!");
                  waitMilliSeconds(500);

                  break;
               case "n":
                  player.setGetNewCard(false);
                  System.out.println(player.getName() + "choose to stay");
                  waitMilliSeconds(500);

                  break;
               default:
                  System.out.println("Wrong input");
                  break;
            }
            // Branches of to check if its a object of Computer
            // This computer will stop if at 18 or above, more risk taking then the dealer
         } else if (player.getClass() == Computer.class && player.getNewCard()) {
            if (player.getSum() >= 18) {
               player.setGetNewCard(false);
               // debugg
               System.out.println(player.getName() + " choose to stay");
               waitMilliSeconds(500);

            } else {
               player.setGetNewCard(true);
               System.out.println(player.getName() + " said, hit me!");
               waitMilliSeconds(500);

            }
            // Branches of to check if its a object of dealer
            // In black jack, the dealer stop when at 17 or above

            // TODO fix so that dealer must play if a player has a better score then they
         } else if (player.getClass() == Dealer.class && player.getNewCard()) {
            if (player.getSum() >= 17) {
               player.setGetNewCard(false);
               // debugg
               System.out.println(player.getName() + " must stop");
               waitMilliSeconds(500);

            } else {
               player.setGetNewCard(true);
               System.out.println(player.getName() + " have to continue");
               waitMilliSeconds(500);

            }
         }
      }
   }

   // Bust is when you have more then 21
   public void checkIfBust() {
      for (Person player : table.getPlayers()) {
         if (player.getSum() > 21) {
            System.out.println(player.getName() + " got bust and is out");
            player.setBust(true);
         }
      }
   }

   // "Sleeps" the terminal for 800ms
   // Just for making the game feel a bit more interactive
   public void waitMilliSeconds(int ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public boolean isAWinner() {
      for (Person player : table.getPlayers()) {

         // TODO check if another player got 21
         // and if the dealer got aswell, then dealer win
         if (player.getSum() == 21) {
            System.out.println(player.getName() + " won");
            printPlayersCards();
            return true;
         } else if (player.getClass() == Dealer.class && player.isBust()) {
            // TODO check who is not bust, they are the winner.
            System.out.println("Dealer is bust!");
            printPlayersCards();
            return true;
         } else if (breakGame()) {
            // TODO

            System.out.println("breakgame");
            return true;
         }
      }
      return false;
   }

   public void printPlayersCards() {
      for (Person player : getPlayers()) {
         System.out.println("\n" + player.getName());
         System.out.println(player.showCards());
      }
   }

   // check if all players getNewCard is set to false
   // or if they are bust. Return a boolean if that is so
   // if count equals the amount of player then there are no moves left

   public boolean breakGame() {
      int count = 0;
      for (Person player : this.table.getPlayers()) {
         if (player.isBust() || !player.getNewCard()) {
            count++;
         }
      }
      // if count equals the amount of player
      if (count == this.table.getPlayers().size()) {
         return true;
      }
      return false;
   }
}
