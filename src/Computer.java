public class Computer extends Player {

   private int moneyInWallet = 100;

   public Computer(String name) {
      super(name);
   }

   public int getWallet() {
      return this.moneyInWallet;
   }

   public void addMoneyInWallet(int money) {
      this.moneyInWallet += money;
   }

}