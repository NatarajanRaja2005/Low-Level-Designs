import java.util.ArrayList;

interface BoardGames{
    //This has an large number of game like of snake , tic tac toe
}
public class game implements BoardGames{
    board board;
    player player1;
    player player2;
    boolean isWhiteTurn;
    ArrayList<move> gamelog;
    status status;
    public game(player player1, player player2) {
        this.board = board.getInstance(8);
        this.player1 = player1;
        this.player2 = player2;
        this.isWhiteTurn = true;
        this.gamelog = new ArrayList<>();
        this.status = status.ACTIVE;
    }

    void start(){
        while(this.status ==status.ACTIVE){
            if(isWhiteTurn){
                makemove(new move(0, 0),player1);
            }
            else{
                makemove(new move(0, 0),player2);
            }
        }
    }

    void makemove(move move,player player){
        if(move.isValid()){
            piece sourcepiece=move.getStartCell().getPiece();

            if(sourcepiece.canMove(board, move.getStartCell(), move.getendCell())){
                piece destinationPiece=move.getendCell().getPiece();

                if(destinationPiece!=null){

                    //Chek its an king white
                    if(destinationPiece instanceof king && isWhiteTurn){
                        this.status=status.WHITE_WIN;
                        return;
                    }

                    
                    //Chek its an king black
                    if(destinationPiece instanceof king && !isWhiteTurn){
                        this.status=status.BLACK_WIN;
                        return;
                    }

                    destinationPiece.setKilled(true);
                }

                //Here makking move
                gamelog.add(move);

                move.getendCell().setPiece(sourcepiece);

                move.getStartCell().setPiece(null);

                //Switchin player
                isWhiteTurn =!isWhiteTurn;
            }
        }
    }
}
