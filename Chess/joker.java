enum status{
   ACTIVE, SAVED, BLACK_WIN, WHITE_WIN, STALEMATE;
}
class player{
   String name;
   boolean isWhite;
   player(String name,boolean isWhite){
      this.name=name;
      this.isWhite=isWhite;
   }

   public String getName(){
      return name;
   }

   public boolean isWhite(){
      return isWhite;
   }
}

class joker{
   public static void main(String[] args){
     player player1=new player("Player1", true);
     player player2=new player("Player2", false);

     game chess=new game(player1,player2);
     chess.start();
   }
}
