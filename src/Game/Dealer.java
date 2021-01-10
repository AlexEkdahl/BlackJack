package Game;

import java.util.*;

public class Dealer extends Person {

   private ArrayList<Person> players;
   private DeckOfCards deck;


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
            System.out.println(player.getName() + " got " + deck.giveOneCard().toString());
            this.deck.removeOneCard();
            waitMilliSeconds(700);
         }
      }
      System.out.println();
   }

   // choice of either take a new card or stay
   public void checkHitOrStand() {
      for (Person player : this.players) {

         // Branches of to check if its a object of Player and wants a new card
         if (player.getClass() == Player.class && player.getCard()) {
            System.out.println("\nNew card or stay?" + "[Y / N]");
            String answer = input.nextLine().toLowerCase();
            switch (answer) {
               case "y":
                  player.setGetCard(true);
                  System.out.println(player.getName() + " said, hit me!");
                  waitMilliSeconds(500);
                  break;
               case "n":
                  player.setGetCard(false);
                  System.out.println(player.getName() + "choose to stay");
                  waitMilliSeconds(500);
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
               waitMilliSeconds(500);

            } else {
               player.setGetCard(true);
               System.out.println(player.getName() + " said, hit me!");
               waitMilliSeconds(500);

            }
            // Branches of to check if its a object of dealer
            // In black jack, the dealer stop when at 17 or above if
            // No other player has a better score then the dealer
         } else if (player.getClass() == Dealer.class && player.getCard()) {
            if (player.getSum() >= 17 && player.getSum() >= getHighestScore()){
               player.setGetCard(false);
               System.out.println(player.getName() + " must stop");
               waitMilliSeconds(500);

            } else {
               player.setGetCard(true);
               System.out.println(player.getName() + " have to continue");
               waitMilliSeconds(500);
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
   // Just for making the game feel a bit more interactive
   public void waitMilliSeconds(int ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public boolean isAWinner() {
      for (Person player : this.players) {
         // TODO check if another player got 21
         // and if the dealer got it as well, then dealer win
         if (player.getSum() == 21) {
            System.out.println(player.getName() + " won");
            printAllScores();
            return true;
         } else if (player.getClass() == Dealer.class && player.isBust()) {
            System.out.println("Dealer is bust!");
            //check who is not bust and they will get their money back (aka won)
            for (Person p: this.players){
               if(!p.isBust()){
                  System.out.println(p.getName() + " got their money back");
               }
            }
            printAllScores();
            return true;
         } else if (breakGame()) {
            // TODO Fix this shit with a winner

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

   // check if all players getCard is set to false
   // or if they are bust. Return a boolean if that is so
   // if count equals the amount of player then there are no moves left
   public boolean breakGame() {
      int count = 0;
      for (Person player : this.players) {
         if (player.isBust() || !player.getCard()) {
            count++;
         }
      }
      // if count equals the amount of player
      if (count == this.players.size()) {
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


   //Get the players highest score excluding the dealer 
   public int getHighestScore(){
      int highestScore = 0;
      for(Person player: this.players){
         if (!(player.getClass() == Dealer.class)){
            if (player.getSum() > highestScore){
               highestScore = player.getSum();
            }
         }
      }
      return highestScore;
   }

   public void printAllScores(){
      for (Person player: this.players){
         System.out.println(player.getName() + "\t\tgot:" + player.getSum());
      }
   }

}