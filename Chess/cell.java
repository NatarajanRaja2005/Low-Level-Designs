class cell{
   int row,col;
   String label;
   piece piece;
   
   public cell(int row, int col, piece piece) {
      this.row = row;
      this.col = col;
      this.piece = piece;
   }

   public piece getPiece(){
      return piece;
   }

   public void setPiece(piece piece){
      this.piece=piece;
   }

}
