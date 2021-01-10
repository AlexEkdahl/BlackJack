package Game;

import java.util.*;

public class Dealer extends Person {

   private ArrayList<Person> players;
   private DeckOfCards deck;
   private boolean dealersTurnToAct = true;

   public Dealer(String name, ArrayList<Person> players) {
      super(name);
      this.deck = new DeckOfCards();
      this.players = players;
      players.add(this);
      // add "Dealer" to the dealers name
      this.setName("Dealer: " + name);
   }

   public Scanner input = new Scanner(System.in);

   // Give everyone at the table a card and removes it from deck
   public void dealOneCard() {
      for (Person player : this.players) {

         // check if player should get a card
         if (player.getCard() && !player.isBust()) {
            player.getOneCard(deck.giveOneCard());

            // To not show dealers second card
            if (player == this && !dealersTurnToAct) {
               System.out.println(player.getName() + " got a card and its hidden");
               //Because dealer should not be asked any questions until its dealers turn to act
               player.setGetCard(false);
               this.deck.removeOneCard();
               waitMilliSeconds(700);
               checkIfBust();
            } else {
               System.out.println(player.getName() + " got " + deck.giveOneCard().toString());
               this.deck.removeOneCard();
               waitMilliSeconds(700);
               checkIfBust();
            }
         }
      }
      System.out.println();
   }

   public void setDealersTurnToAct(boolean dealersTurnToAct) {
      this.dealersTurnToAct = dealersTurnToAct;
   }

   public boolean isDealersTurnToAct() {
      return this.dealersTurnToAct;
   }

   // choice of either take a new card or stay
   public void playerCheckHitOrStand() {
      for (Person player : this.players) {

         // Branches of to check if its a object of Player and wants a new card
         //Player is a user and be asked for input
         if (player.getClass() == Player.class && player.getCard()) {
            System.out.println("\nNew card or stay?" + "[Y / N]");
            String answer = input.nextLine().toLowerCase();
            switch (answer) {
               case "y":
                  player.setGetCard(true);
                  System.out.println(player.getName() + " said, hit me!");
                  waitMilliSeconds(700);
                  break;
               case "n":
                  player.setGetCard(false);
                  System.out.println(player.getName() + " choose to stay");
                  waitMilliSeconds(700);
                  break;
               default:
                  System.out.println("Wrong input");
                  break;
            }
            // Branches of to check if its a object of Computer
            // This computer will stop if at 18 or above, more risk taking then the dealer
         } else if (player.getClass() == Computer.class && player.getCard()) {

            if (player.getSum() >= 18) {
               player.setGetCard(false);
               System.out.println(player.getName() + " choose to stay");
               waitMilliSeconds(700);
            } else {
               player.setGetCard(true);
               System.out.println(player.getName() + " said, hit me!");
               waitMilliSeconds(700);
            }
         }
         // Branches of to check if its a object of dealer
         // In black jack, the dealer draw until 16 and must stop at 17 or above
         else if (player.getClass() == Dealer.class && player.getCard() && this.isDealersTurnToAct()) {
            if (player.getSum() >= 17) {
               player.setGetCard(false);
               System.out.println(player.getName() + " must stop");
               waitMilliSeconds(700);
            } else {
               player.setGetCard(true);
               System.out.println(player.getName() + " takes a new card");
               waitMilliSeconds(700);
            }
         }
      }
   }

   // Bust is when you have more then 21
   public void checkIfBust() {
      for (Person player : this.players) {
         if (player.getSum() > 21 && !player.isBust()) {
            System.out.println(player.getName() + " got bust and is out");
            player.setName(player.getName() + " \tBUST");
            player.setBust(true);
            player.setGetCard(false);
         }
      }
   }

   // "Sleeps" the terminal for N ms
   // Just making the game feel a bit more interactive
   public void waitMilliSeconds(int ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
   //TODO Make terminal more pretty
   // method to break the while loop and chose a winner
   public boolean isAWinner() {
      for (Person player : this.players) {
         // TODO check if another player got 21
         if (player.getSum() == 21) {
            if (this.getSum() == 21) {
               System.out.println("Dealer won");
               return true;
            } else {
               System.out.println(player.getName() + " won");
               printAllScores();
               return true;
            }
            // If Dealer is bust
         } else if (player == this && player.isBust()) {
            System.out.println("Dealer is bust!");
            // check who is not bust and they will get their money back (aka won)
            for (Person p : this.players) {
               if (!p.isBust()) {
                  System.out.println(p.getName() + " wins got their money back");
               }
            }
            return true;
            //TODO
         } else if(breakGame()) {
            System.out.println(whoHasHighestScore().getName() + "Won");
            printAllScores();
            return true;

         }
      }
      return false;
   }

   public void printAllPlayersCards() {
      for (Person player : getPlayers()) {
         System.out.println("\n" + player.getName());
         System.out.println(player.showCards());
      }
   }

   // check if all players getCard is set to false
   // or if they are bust. Return a boolean if that is so
   // if count equals the amount of player excluding dealer then there are no moves
   // left
   public boolean breakGame() {
      int count = 0;
      for (Person player : this.players) {
         if (!(player == this) && (player.isBust() || !player.getCard())) {
            count++;
         }
      }
      // if count equals the amount of player except dealer
      if (count == this.players.size() - 1) {
         this.dealersTurnToAct = true;
         this.setGetCard(true);
         return true;
      }
      return false;
   }

   public ArrayList<Person> getPlayers() {
      return players;
   }

   // Add anyone from motherclass, person, to the list of players
   public void addPlayer(Person person) {
      this.players.add(person);
   }

   public void printAllScores() {
      for (Person player : this.players) {
         System.out.println(player.getName() + "\t\tgot:" + player.getSum());
      }
   }

   // Set all values to starting values
   public void clearPlayersForNewRound() {
      for (Person player : this.players) {
         player.setSum(0);
         player.setGetCard(true);
         player.setBust(false);
         player.setName(player.getName().replace(" \tBUST", ""));
         player.setNewPlayersCards();
         this.deck = new DeckOfCards();
      }
   }

   // Get the players highest score excluding the dealer
   public int playersHighestScore() {
      int highestScore = 0;
      for (Person player : this.players) {
         if (!(player == this)) {
            if (!player.isBust() && player.getSum() > highestScore) {
               highestScore = player.getSum();
            }
         }
      }
      return highestScore;
   }

   // Get the player with the highest score
   public Person whoHasHighestScore() {
      int bestScore = 0;
      int bestIndex = 0;
      for (int i = 0; i < players.size(); i++) {
         if (!players.get(i).isBust() && players.get(i).getSum() > bestScore) {
            bestScore = players.get(i).getSum();
            bestIndex = i;
         }
      }
      return players.get(bestIndex);
   }
   // Print players cards and depending if its dealers turn or not, dealers second card
   // is hidden.
   public void printPlayersCards() {
      for (Person player : getPlayers()) {
         if (!(player == this)) {
            System.out.println("\n" + player.getName());
            System.out.println(player.showCards());
         } else {
            System.out.println("\n" + player.getName());
            if (this.dealersTurnToAct) {
               System.out.println(player.showCards());
            } else {
               System.out.println(player.getCardList().get(0).toString());
               System.out.println("[HIDDEN CARD]");
            }
         }
      }
   }

}