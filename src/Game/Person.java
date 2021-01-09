package Game;

import java.util.*;

public class Person {

   private ArrayList<Card> playersCards;
   private String name;
   private boolean bust = false;
   private boolean getNewCard = true;
   private int sum;

   public Person(String name) {
      this.name = name;
      this.playersCards = new ArrayList<>();
   }

   public int getSum() {
      return sum;
   }

   public void setSum(int sum) {
      this.sum = sum;
   }

   public void setGetNewCard(boolean getNewCard) {
      this.getNewCard = getNewCard;
   }

   public boolean getNewCard() {
      return this.getNewCard;

   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isBust() {
      return this.bust;
   }

   public void setBust(boolean bust) {
      this.bust = bust;
   }

   public String getName() {
      return name;
   }

   public ArrayList<Card> getCardList(){
      return this.playersCards;
   }

   // add one card and add it to value
   public void getOneCard(Card card) {
      this.playersCards.add(card);
      this.sum += card.getValue();
   }

   //WAIT

   public String showCards(){
      String returnString = "";
      for(Card card: playersCards){
         returnString = returnString + card.toString() + "\n";
      }
      return returnString;
   }

}
