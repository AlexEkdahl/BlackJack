import java.util.*;

public class Dealer extends Person {

   private String name;
   private DeckOfCards deck;
   private Table table;

   public Dealer(String name, ArrayList<Person> players) {
      super(name);
      this.name = "Dealer: " + name;
      this.deck = new DeckOfCards();
      this.table = new Table(players);
      this.table.addPlayer(this);
   }

   public Scanner input = new Scanner(System.in);

   public Table getTable() {
      return this.table;
   }

   // Give everyone at the table a card and removes it from deck
   public void dealOneCard() {
      for (Person player : table.getPlayers()) {
         //check if player should get a card
         if (player.getNewCard() && !player.isBust())
            player.getOneCard(this.deck.giveOneCard());
         this.deck.removeOneCard();
      }
   }
   
   // choice of either take a new card och stay
   public void checkHitOrStand() {
      for (Person player : table.getPlayers()) {

         // users choice
         if (player instanceof Player) {
            System.out.println("New card or stay?" + "\n[ Y / N ]");
            String answer = input.nextLine().toLowerCase();
            if (answer.equals("y")) {
               player.setGetNewCard(true);
            } else {
               player.setGetNewCard(false);
            }
         }

         // The computer is bold and like to take risk
         if (player instanceof Computer) {
            if (player.getSum() >= 18) {
               player.setGetNewCard(false);
            } else {
               player.setGetNewCard(true);
            }
         }
         // In black jack, the dealer stop at 17.
         if (player instanceof Dealer) {
            if (player.getSum() >= 17) {
               player.setGetNewCard(false);
            } else {
               player.setGetNewCard(true);
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

   //TODO a method to check is someone got 21, if dealer bets players or player beat dealer
   //note can only check of everyone is either stayed or busted
}
