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

   // Give everyone at the table a card and removes it from deck
   public void dealOneCard() {
      for (Person player : table.getPlayers()) {
         // check if player should get a card
         if (player.getNewCard() && !player.isBust())
            player.getOneCard(this.deck.giveOneCard());
         this.deck.removeOneCard();
      }
   }
   
   // choice of either take a new card och stay
   public void checkHitOrStand() {
      for (Person player : table.getPlayers()) {
         // users choice
         if (player.getClass() == Player.class && player.getNewCard()) {
            System.out.println("\n New card or stay?" + "\n[ Y / N ]");
            String answer = input.nextLine().toLowerCase();
            if (answer.equals("y")) { 
               player.setGetNewCard(true);
               //debugg
               System.out.println("Who was here at get new card: " + player.getName());
            } else {
               player.setGetNewCard(false);
               //debugg
               System.out.println("Who was here at dont get new card: " + player.getName());

            }
            // The computer is bold and like to take risk
         } else if (player.getClass() == Computer.class && player.getNewCard()) {
            if (player.getSum() >= 18) {
               player.setGetNewCard(false);
               //debugg
               System.out.println("Who was here at dont get new card Computer: " + player.getName());

            } else {
               player.setGetNewCard(true);
               //debugg
               System.out.println("Who was here at get new card Computer: " + player.getName());

            }
            // In black jack, the dealer stop at 17.
         } else if (player.getClass() == Dealer.class && player.getNewCard()) {
            if (player.getSum() >= 17) {
               player.setGetNewCard(false);
               //debugg
               System.out.println("Who was here at dont get new card Dealer: " + player.getName());
            } else {
               player.setGetNewCard(true);
               //debugg
               System.out.println("Who was here at get new card Dealer : " + player.getName());

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

   public boolean isAWinner(){
      for (Person player: table.getPlayers()){
         if (player.getSum() == 21){
            //TODO player get money
            return true;
         }
         if (player.getClass() == Dealer.class && player.isBust()){
            //TODO betters split(?) get money
            return true;
         }

         //TODO check if all players.getNewCard == false
         // or all players.isBust == true
         

      }
      return false;
   }

   // TODO a method to check is someone got 21, if dealer bets players or player
   // beat dealer
   // note can only check of everyone is either stayed or busted
}
