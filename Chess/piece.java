interface MovementStratergy{
    boolean canMove(board board,cell startcell,cell endcell);
}
abstract class piece{
   boolean isWhite;
   boolean isKilled=false;
   MovementStratergy movementStratergy;

   piece(boolean isWhite,MovementStratergy movementStratergy){
      this.isWhite=isWhite;
      this.movementStratergy=movementStratergy;
   }

   boolean isWhite(){
      return isWhite;
   }

   boolean isKilled(){
      return isKilled;
   }

   void setKilled(boolean killed){
      this.isKilled=killed;
   }

   boolean canMove(board board,cell startblock,cell endblock){
    return movementStratergy.canMove(board, startblock, endblock);
   }
}

class king extends piece{
    MovementStratergy stratergy;
   king(boolean isWhite) {
      super(isWhite,new KingMovementStratergy());
      //TODO Auto-generated constructor stub
   }
   
   @Override
   boolean canMove(board board, cell startblock, cell endblock) {
    // TODO Auto-generated method stub
      return super.canMove(board, startblock, endblock);
   }
   
   
}
class queen extends piece{
    
    MovementStratergy stratergy;
   queen(boolean isWhite,MovementStratergy stratergy) {
      super(isWhite,new QueenMovementStratergy());
      //TODO Auto-generated constructor stub
   }

   
   @Override
   boolean canMove(board board, cell startblock, cell endblock) {
    // TODO Auto-generated method stub
      return super.canMove(board, startblock, endblock);
   }
   
}
class bishop extends piece{

    MovementStratergy stratergy;
   bishop(boolean isWhite,MovementStratergy stratergy) {
      super(isWhite,new BishopMovementStratergy());
      //TODO Auto-generated constructor stub
   }

   
   @Override
   boolean canMove(board board, cell startblock, cell endblock) {
    // TODO Auto-generated method stub
      return super.canMove(board, startblock, endblock);
   }
   
}
class rook extends piece{
   
    MovementStratergy stratergy;
   rook(boolean isWhite,MovementStratergy stratergy) {
      super(isWhite,new RookMovementStratergy());
      //TODO Auto-generated constructor stub
   }

   
   @Override
   boolean canMove(board board, cell startblock, cell endblock) {
    // TODO Auto-generated method stub
      return super.canMove(board, startblock, endblock);
   }
   
}
class knight extends piece{
   
    MovementStratergy stratergy;
   knight(boolean isWhite,MovementStratergy stratergy) {
      super(isWhite,new KnightMovementStratergy());
      //TODO Auto-generated constructor stub
   }

   
   @Override
   boolean canMove(board board, cell startblock, cell endblock) {
    // TODO Auto-generated method stub
      return super.canMove(board, startblock, endblock);
   }
   
}
class pawn extends piece{
    
    MovementStratergy stratergy;
   pawn(boolean isWhite,MovementStratergy stratergy) {
      super(isWhite,new PawnMovementStratergy());
      //TODO Auto-generated constructor stub
   }

   
   @Override
   boolean canMove(board board, cell startblock, cell endblock) {
    // TODO Auto-generated method stub
      return super.canMove(board, startblock, endblock);
   }  
}

abstract class piecefactory{
static piece creatPiece(String piecetype, boolean isWhite){
   switch (piecetype.toLowerCase()) {
      case "king":
         return new king(isWhite);
      case "queen":
         return new queen(isWhite);
      case "bishop":
         return new bishop(isWhite);
      case "pawn":
         return new pawn(isWhite);
      case "knight":
         return new knight(isWhite);
      case "rook":
         return new rook(isWhite);
      default:
         throw new IllegalArgumentException("Unknown piece Type: "+piecetype);
   }
}
}