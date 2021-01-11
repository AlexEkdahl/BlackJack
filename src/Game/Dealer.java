package Game;

import java.util.ArrayList;

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

   public DeckOfCards getDeck() {
      return deck;
   }

   public void setDealersTurnToAct(boolean dealersTurnToAct) {
      this.dealersTurnToAct = dealersTurnToAct;
   }

   public boolean isDealersTurnToAct() {
      return this.dealersTurnToAct;
   }

   public ArrayList<Person> getPlayers() {
      return players;
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





   public void doRoundOne() {
      dealOneCard();
      setDealersTurnToAct(false);
      dealOneCard();
      clearScreen();
   }

   public void doGameRound() {
      printPlayersCards();
      System.out.println();
      playerCheckHitOrStand();
      System.out.println();
      dealOneCard();
   }



}