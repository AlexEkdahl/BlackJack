package Game;

public class DealerLogic {

   Dealer dealer;

   public DealerLogic(Dealer dealer) {
      this.dealer = dealer;
   }

   // Handle the visibility of dealers cards 
   public void dealToDealer() {
      if(dealer.getCard() && dealer.isDealersTurnToAct()) {
         System.out.println(dealer.getName() + " got " + dealer.getDeck().giveOneCard().toString());
         dealer.getOneCard(dealer.getDeck().giveOneCard());
         dealer.getDeck().removeOneCard();
      }
      // To not show dealers second card
      else if (!dealer.isDealersTurnToAct() && dealer.getCard()) {
         System.out.println(dealer.getName() + " got a card and its hidden");
         // Dealer dont get any cards until every player is either bust or done, get card = false
         dealer.setGetCard(false);
         dealer.getDeck().removeOneCard();
      } 
   }

   // Pre decided route for compute to take regarding hit or stand
   public void dealerHitOrStand() {
      // checkIfBust();
      // In black jack, the dealer draw until 16 and must stop at 17 or above
      if (dealer.getCard() && dealer.isDealersTurnToAct() || dealer.getSum() == 21) {
         if (dealer.getSum() >= 17) {
            dealer.setGetCard(false);
            // waitMilliSeconds(700);
         } else {
            dealer.setGetCard(true);
            System.out.println(dealer.getName() + " takes a new card");
            // waitMilliSeconds(700);
         }
      }
   }
}
