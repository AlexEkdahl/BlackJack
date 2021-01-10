package Game;

import java.util.*;

public class Person {

   private ArrayList<Card> playersCards;
   private String name;
   private boolean isBust = false;
   private boolean getCard = true;
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

   public void setGetCard(boolean getCard) {
      this.getCard = getCard;
   }

   public boolean getCard() {
      return this.getCard;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isBust() {
      return this.isBust;
   }

   public void setBust(boolean isBust) {
      this.isBust = isBust;
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

   // get all of the players card, shown visible;
   public String showCards(){
      String returnString = "";
      for(Card card: playersCards){
         returnString = returnString + card.toString() + "\n";
      }
      return returnString;
   }

   public void setNewPlayersCards(){
      this.playersCards = new ArrayList<>();
   }

}
