//Singleton Design Pattern
public class board {
    static board instance;
    cell[][] board;

    public board(int rows){
        initializeBoard(rows);
    }

    static board getInstance(int rows){
        if(instance==null){
            instance=new board(rows);
        }
        return instance;
    }

    void initializeBoard(int rows){
        board=new cell[rows][rows];

        //Setting white(true) pieces using factory
        setPieceRow(0,true);
        setPawnRow(1,true);

        //Setting black(false) pieces
        setPieceRow(rows-1,false);
        setPawnRow(rows-2,false);

        //Defining rest cells having no pieces
        for(int row=2;row<rows-2;row++){
            for(int col=0;col<rows;col++){
                board[row][col]=new cell(row,col,null);
            }
        }
    }

    void setPieceRow(int row,boolean isWhite){
        board[row][0]=new cell(row,0,piecefactory.creatPiece("rook", isWhite));
        board[row][1]=new cell(row,1,piecefactory.creatPiece("knight", isWhite));
        board[row][2]=new cell(row,2,piecefactory.creatPiece("bishop", isWhite));
        board[row][3]=new cell(row,3,piecefactory.creatPiece("queen", isWhite));
        board[row][4]=new cell(row,4,piecefactory.creatPiece("king", isWhite));
        board[row][5]=new cell(row,5,piecefactory.creatPiece("bishop", isWhite));
        board[row][6]=new cell(row,6,piecefactory.creatPiece("knight", isWhite));
        board[row][7]=new cell(row,7,piecefactory.creatPiece("rook", isWhite));
    }

    void setPawnRow(int row,boolean isWhite){
        for(int j=0;j<row;j++){
            board[row][j]=new cell(row,j,piecefactory.creatPiece("pawn", isWhite));
        }
    }

    
}
