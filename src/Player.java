public class Player extends Person {

   private int moneyInWallet = 100;

   public Player(String name) {
      super(name);
   }

   public int getWallet() {
      return moneyInWallet;
   }

   public void addMoneyInWallet(int money) {
      this.moneyInWallet += moneyInWallet;
   }
   
}
