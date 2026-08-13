import java.util.*;
class board{
  char[][] mat;
  static char[][] createBoard(int size){
    return new char[size][size];
  }
}

interface player{
void move(int row,int col,char[][] mat);
player next();
void setNext(player player);
char getName();
}

class playerx implements player{
  char name;
  player next;

  public char getName(){
    return this.name;
  }
  public void setNext(player next){
    this.next=next;
  }
  playerx(char name,player next){
    this.name=name;
    this.next=next;
  }

  @Override
  public void move(int row, int col, char[][] mat) {
    // TODO Auto-generated method stub
    mat[row][col]=this.name;  
  }

  @Override
  public player next() {
    // TODO Auto-generated method stub
    return this.next;
  }

}

class playery implements player{
  char name;
  player next;

  playery(char name,player next){
    this.name=name;
    this.next=next;
  }

  
  public char getName(){
    return this.name;
  }
  public void setNext(player next){
    this.next=next;
  }

  @Override
  public void move(int row, int col, char[][] mat) {
    // TODO Auto-generated method stub
    mat[row][col]=this.name;  
  }

  @Override
  public player next() {
    // TODO Auto-generated method stub
    return this.next;
  }

}

class boardFunctions{

   boolean row(char[][] mat,char name){
    for(int i=0;i<mat.length;i++){
      for(int j=0;j<mat[0].length;j++){
        if(name!=mat[i][j]){
          break;
        }
        else if(j==mat[0].length-1){
          return true;
        }
      }
    }
    return false;
   }

    boolean col(char[][] mat,char name){
    for(int i=0;i<mat[0].length;i++){
      for(int j=0;j<mat.length;j++){
        if(name!=mat[j][i]){
          break;
        }
        else if(j==mat.length-1){
          return true;
        }
      }
    }
    return false;
   }
   
   boolean ldiag(char[][] mat,char name){
    int l=0;
    int r=0;
    while(l<mat.length && r<mat[0].length){
      if(name!=mat[l][r]){
        return false;
      }
      l++;
      r++;
    }

    return true;
   }

    boolean rdiag(char[][] mat,char name){
    int l=0;
    int r=mat[0].length-1;
    while(l<mat.length && r>=0){
      if(name!=mat[l][r]){
        return false;
      }
      l++;
      r--;
    }

    return true;
   }
   boolean draw(char[][] mat){
    for(int i=0;i<mat.length;i++){
      for(int j=0;j<mat[0].length;j++){
        if(mat[i][j]=='0'){
          return false;
        }
      }
    }
    return true;
   }
   boolean winOrDraw(char[][] mat,player player){
    if(row(mat,player.getName())||col(mat,player.getName())||ldiag(mat,player.getName())||rdiag(mat,player.getName())){
      System.out.print("Player "+player.getName()+" Wins the match\n\n");
      display(mat);
      return true;
    }
    else if(draw(mat)){
      System.out.println("Match Draw!");
      return true;
    }
    return false;
   }

   boolean validLocation(char[][] mat,int row,int col){
    if(row>=mat.length || col>=mat[0].length) return false;
    return mat[row][col]=='0';
   }

   void display(char[][] mat){
    for(int i=0;i<mat.length;i++){
      for(int j=0;j<mat.length;j++){
        System.out.print(mat[i][j]+" ");
      }
      System.out.println();
    }
   }
}
public class TicTacToe{
  public static void main(String[] args) {
    
    Scanner sc=new Scanner(System.in);
    boardFunctions functions=new boardFunctions();

      player y=new playery('Y',null);
      player x=new playerx('X', y);
      y.setNext(x);

      board gameBoard=new board();
      char[][] board=gameBoard.createBoard(3);
      for(char[] i:board){
        Arrays.fill(i,'0');
      }
      player cur=x;
      while(true){
        System.out.println("\n\n");
        functions.display(board);
        System.out.println("Player "+cur.getName()+" the Location(Row_Col): ");
        int row=sc.nextInt();
        int col=sc.nextInt();
        if(!functions.validLocation(board, row, col)){
          System.out.println("Re enter the Valid Location");
          continue;
        }
        cur.move(row, col, board);
        if(functions.winOrDraw(board,cur)){
          break;
        }
        cur=cur.next();
      }
  }
}
