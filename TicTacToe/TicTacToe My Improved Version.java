import java.util.Scanner;

enum Symbol{
    S,O,EMPTY
}
class Position{
    int row;
    int col;
    Position(int row,int col){
        this.row=row;
        this.col=col;
    }
}
class Board{
    Symbol[][] board;
    int row;
    int col;
        
    
    Board(int row,int col){
        this.row=row;
        this.col=col;
        board=new Symbol[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                board[i][j]=Symbol.EMPTY;
            }
        }
    }

    boolean validmove(Position position){
        int r=position.row;
        int c=position.col;
         //System.out.println(symbol);
        return r>=0 && r<=board.length-1 && c>=0 && c<=board[0].length-1 && board[r][c]==Symbol.EMPTY;
    }

    void makemove(Position position,Symbol symbol){
        //System.out.println(symbol);
        board[position.row][position.col]=symbol;
    }

    boolean win(Position position){
        
        //row
        for(Symbol[] i:board){
            if(checking(i)){
                return true;
            }
        }

        //col
        Symbol[] col=new Symbol[board.length];
        for(int i=0;i<board.length;i++){
            col[i]=board[i][position.col];
        }
        if(checking(col)){
                return true;
        }
        //Diag
        Symbol[] diag=new Symbol[board.length];
        for(int i=0;i<board.length;i++){
            diag[i]=board[i][i];
        }
        if(checking(diag)){
                return true;
        }
        
        Symbol[] diag2=new Symbol[board.length];
        for(int i=0;i<board.length;i++){
            diag2[i]=board[i][board.length-1-i];
        }
        if(checking(diag2)){
            return true;
        }
        
        return false;
    }

    boolean checking(Symbol[] arr){

        Symbol first=arr[0];
        if(first==Symbol.EMPTY) return false;
        for(int i=1;i<arr.length;i++){
            if(first!=arr[i]){
                return false;
            }
        }
        return true;
    }

    void print(){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        
        System.out.println("\n");
    }

    //draw
    boolean isDraw(){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==Symbol.EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

}

interface PlayerStratergy{
    Position move();
}
abstract class HumanPlayer implements PlayerStratergy{
    Scanner scanner=new Scanner(System.in);
    @Override
    public Position move() {
        // TODO Auto-generated method stub
        try{
            int row=scanner.nextInt();
            int col=scanner.nextInt();

            Position move=new Position(row, col);
            
            return move;
        }
        catch(Exception e){
            System.out.println("Try to enter only of integer values as an input!");
            scanner.nextLine();
        }
        return move();
    }
    
}
class Player extends HumanPlayer{
    Symbol name;
    Player(Symbol name){
        this.name=name;
    }    
}
interface BoardGame{
    void play();
}
class TicTacToe implements BoardGame{
    Board board;
    Player player1;
    Player player2;
    Player currentPlayer;
    TicTacToe(Player player1,Player player2,int row,int col){
        this.player1=player1;
        this.player2=player2;
        this.currentPlayer=player1;
        this.board=new Board(row, col);
    }
    @Override
    public void play() {
        while(true){
            board.print();
            System.out.println("Enter the row and column(Player: "+currentPlayer.name+" ): ");
            Position move=currentPlayer.move();
           if(!board.validmove(move)){
              System.out.println("Invalid Move");
              continue;
            }

            board.makemove(move,currentPlayer.name);

            if(board.win(move)){
                board.print();
                System.out.println(currentPlayer.name+" Won!");
                break;
            }

            if(board.isDraw()){
            board.print();
            System.out.println("Match Draw!");
            break;
            }

            currentPlayer =
            (currentPlayer==player1)
            ? player2
            : player1;

        }
    }
}
public class joker {
    public static void main(String[] args) {
        Player player1=new Player(Symbol.S);
        Player player2=new Player(Symbol.O);
        TicTacToe game=new TicTacToe(player1,player2,3,3);
        game.play();
    }
}
