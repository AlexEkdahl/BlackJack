package Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
   DealerLogic dealerLogic;
   Dealer dealer;

   public GameLogic(Dealer dealer) {
      this.dealer = dealer;
      this.dealerLogic = new DealerLogic(dealer);
   }

   public Scanner input = new Scanner(System.in);

   private ArrayList<Person> playerList() {
      return dealer.getPlayers();
   }

   private boolean isThisPlayerClass(Person player) {
      if (player.getClass() == Player.class) {
         return true;
      }
      return false;
   }

   private boolean isThisComputerClass(Person computer) {
      if (computer.getClass() == Computer.class) {
         return true;
      }
      return false;
   }

   // Print all cards except for dealer until its dealers turn to act
   public void printPlayersCards() {
      checkIfBust();
      for (Person player : playerList()) {
         if (!(player == dealer)) {
            System.out.println("\n" + player.getName());
            System.out.println(player.showCards());
         } else {
            System.out.println("\n" + player.getName());
            if (dealer.isDealersTurnToAct()) {
               System.out.println(player.showCards());
            } else {
               System.out.println(player.getCardList().get(0).toString());
               System.out.println("[HIDDEN CARD]");
            }
         }
      }
   }

   // Player choice of either take a new card or stay
   public void playerHitOrStand() {
      String answer = "";
      for (Person player : playerList()) {
         if (isThisPlayerClass(player) && player.getCard() || player.getSum() == 21) {
            while (answer.equals("n") || answer.equals("y")) {
               System.out.println("\nNew card?" + "[Y / N]");
               answer = input.nextLine().toLowerCase();
            }
            switch (answer) {
               case "y":
                  player.setGetCard(true);
                  System.out.println(player.getName() + " said, hit me!");
                  // waitMilliSeconds(700);
                  break;
               case "n":
                  player.setGetCard(false);
                  System.out.println(player.getName() + " choose to stay");
                  // waitMilliSeconds(700);
                  break;
            }
         }
      }
   }

   // Pre decided route for computer to take regarding hit or stand
   public void computerHitOrStand() {
      for (Person computer : playerList()) {
         if (isThisComputerClass(computer) && computer.getCard() || computer.getSum() == 21) {
            // The computer will stop at 18 or above, more risk taking then the dealer
            if (computer.getSum() >= 18) {
               System.out.println(computer.getName() + " choose to stay");
               computer.setGetCard(false);
               // waitMilliSeconds(700);
            } else {
               System.out.println(computer.getName() + " said hit me!");
               computer.setGetCard(true);
            }
         }
      }
   }

   // Give everyone at the table a card and removes it from deck
   public void dealCard() {
      checkIfBust();
      for (Person player : playerList()) {
         // check if player should get a card
         if (!(player == dealer) && player.getCard() && !player.isBust()) {
            player.getOneCard(dealer.getDeck().giveOneCard());
            System.out.println(player.getName() + " got " + dealer.getDeck().giveOneCard().toString());
            dealer.getDeck().removeOneCard();
            waitMilliSeconds(700);
         }
      }
      dealerLogic.dealToDealer();
      waitMilliSeconds(700);
   }

   // Bust is when you have more then 21
   public void checkIfBust() {
      for (Person player : playerList()) {
         if (player.getSum() > 21 && !player.isBust()) {
            player.setName(player.getName() + " \tBUST");
            player.setBust(true);
            player.setGetCard(false);
         } else if (player.getSum() == 21) {
            player.setGetCard(false);
         }
      }
   }

   // If getCard == false on all player
   public boolean noMoreMoves() {
      int count = 0;
      for (Person player : playerList()) {
         if (player.getCard() == false) {
            count++;
         }
      }
      if (count == playerList().size()) {
         return true;
      }
      return false;
   }

   // check if all players getCard is set to false
   // or if they are bust.
   public boolean breakGame() {
      int count = 0;
      for (Person player : playerList()) {
         if (!(player == dealer) && (player.isBust() || !player.getCard())) {
            count++;
         }
      }
      if (count == playerList().size() - 1 && !dealer.isDealersTurnToAct()) {
         // Now dealers turn begin
         dealer.setDealersTurnToAct(true);
         dealer.setGetCard(true);
         return true;
      }
      return false;
   }

   // Get the player with the highest score who is not bust
   public Person whoHasHighestScore() {
      int bestScore = 0;
      int bestIndex = 0;
      for (int i = 0; i < playerList().size(); i++) {
         if (!playerList().get(i).isBust() && playerList().get(i).getSum() > bestScore) {
            bestScore = playerList().get(i).getSum();
            bestIndex = i;
         }
      }
      return playerList().get(bestIndex);
   }

   // Get the players highest score excluding the dealer
   public int playersHighestScore() {
      int highestScore = 0;
      for (Person player : playerList()) {
         if (!(player == dealer)) {
            if (!player.isBust() && player.getSum() > highestScore) {
               highestScore = player.getSum();
            }
         }
      }
      return highestScore;
   }

   public boolean isAWinner() {
      for (Person player : playerList()) {

         // TODO check if another player and dealer got 21
         // TODO go trough this branch toughly

         if (player.getSum() == 21) {
            clearScreen();
            if (dealer.getSum() == 21) {
               System.out.println("Dealer won");
               return true;
            } else {
               System.out.println(player.getName() + " won");
               printAllScores();
               return true;
            }
            // If Dealer is bust
         } else if (player == dealer && player.isBust()) {
            clearScreen();
            System.out.println("Dealer is bust!");
            // check who is not bust and they will get their money back (aka won)
            for (Person p : playerList()) {
               if (!p.isBust()) {
                  System.out.println(p.getName() + " wins got their money back");
               }
            }
            return true;
         } else if (noMoreMoves() && dealer.isDealersTurnToAct()) {
            clearScreen();
            System.out.println(whoHasHighestScore().getName() + " Won");
            printPlayersCards();
            return true;
         }
      }
      return false;
   }

   public void printAllScores() {
      for (Person player : playerList()) {
         System.out.println(player.getName() + " got:" + player.getSum());
      }
   }

   // "Sleeps" the terminal for given int ms
   public void waitMilliSeconds(int ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void clearScreen() {
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
