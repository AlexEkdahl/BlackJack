package Game;

import java.util.*;

public class Table {

   private ArrayList<Person> players;
   
   public Table(ArrayList<Person> players){
      this.players = players;
   }

   public ArrayList<Person> getPlayers() {
      return players;
   }

   //Add anyone from motherclass, person, to the list of players
   public void addPlayer(Person person) {
      this.players.add(person);
   }
}