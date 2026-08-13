//Head of all

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

interface BoardGame{
    void start();
}

//Top of your game
class SnakeGame implements BoardGame{
    //Game initializer
    public void start(){
        int row=5;
        int col=5;
        Board initialize=new Board(row,col);
        char[][] board=initialize.getboard();
        Queue<Position> queue=initialize.getQueue();

        //Storing head in centralized place
        Position head=new Position(0, 0);
        //Game Food Generation
        FoodGeneration food=new CustomGeneration();
        food.generate(board);

        //For Making movement
        MakeMove move=new MakeMove();

        //Starting of game loop
        System.out.println("Welcome to the Joker's Game Universe!\nSnake Game");
        
        //For taking input from user
        Scanner sc=new Scanner(System.in);
        //Here Game Continue untill of Lost state is false
        while(!LostState.lost){
            //Displaying Board
            initialize.display();
            System.out.println("Enter the position(U,D,R,L): ");
            String s=sc.next().toUpperCase();
            if(s.isEmpty()) continue;
            
            char input=s.charAt(0);
            Position nextHead = move.makeMove(input, head, board, queue);
            
            if(nextHead != null) {
                head = nextHead;
            }
        }

        sc.close();
    }
}

//Board
class Board{
    char[][] board;
    //For movement of snake
    Queue<Position> queue;
    public Board(int row,int col){
        board=new char[row][col];
        queue=new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = ' ';
            }
        }
        //Initially the snake is at 0,0
        board[0][0]='.';
        queue.add(new Position(0, 0));
    }

    public char[][] getboard(){
        return board;
    }

    public Queue<Position> getQueue(){
        return queue;
    }

    // view of snake board
    public void display() {
        System.out.println("-----------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-----------");
    }
}

//Position
class Position{
    int row;
    int col;
    public Position(int row,int col){
        this.row=row;
        this.col=col;
    }
}

//Food generation
interface FoodGeneration{
    void generate(char[][] board);
}

//Now i am implementing randomm generation startergy for food. 
//But in future we can able to extend the food with depends of game stratergy inn random manner
class CustomGeneration implements FoodGeneration{
    @Override
    public void generate(char[][] board) {
        board[0][3]='X';
        board[2][1]='X';
        board[1][2]='X';
        board[3][0]='X';
        board[4][0]='X';
        board[4][4]='X';
    }
}

//Snake Move stratergy
abstract class MoveStratergy{
    protected Position head;
    protected char[][] board;
    protected Queue<Position> queue;
    protected int n, m;

    public MoveStratergy(Position head, char[][] board, Queue<Position> queue) {
        this.head = head;
        this.board = board;
        this.queue = queue;
        this.n = board.length;
        this.m = board[0].length;
    }

    public Position executemove(char[][] board,int row,int col,Queue<Position> queue){
        //Take old position
        Position newpos=new Position(row, col);
        
        //If new position have food
        if(board[row][col]=='X'){
            //Change it as snake
            board[row][col]='.';
        }
        else if(board[row][col]=='.'){
            //Game Lost
            new LostState(board);
            return null;
        }
        else{
        //make old posiiton disappear
        Position old=queue.poll();
        if(old != null) {
                board[old.row][old.col] = ' ';
        }
        //Now move the snake
        board[row][col]='.';
        }

        //On queue, you should have to add of all new movement for futrther processing
        queue.add(newpos);

        return newpos;
    }

    abstract boolean possible();
    abstract Position move();
}

//In future, you can also extends my code for diagonal directions too
class UpSide extends MoveStratergy{
    public UpSide(Position head, char[][] board, Queue<Position> queue) {
        super(head, board, queue);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean possible() {
        int row=head.row-1;
        return row>=0;
    }

    @Override
    public Position move() {
      return executemove(board, head.row - 1, head.col, queue);
    }
    
}

class DownSide extends MoveStratergy{
   public DownSide(Position head, char[][] board, Queue<Position> queue) {
        super(head, board, queue);
        //TODO Auto-generated constructor stub
    }

@Override
    public boolean possible() {
        int row=head.row+1;
        return row<n;
    }

    @Override
    public Position move() {
      return executemove(board, head.row + 1, head.col, queue);
    }
    
}

class RightSide extends MoveStratergy{
    public RightSide(Position head, char[][] board, Queue<Position> queue) {
        super(head, board, queue);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean possible() {
        int col=head.col+1;
        return col<m;
    }

    @Override
    public Position move() {
      return executemove(board, head.row , head.col+1, queue);
    }
    
}

class LeftSide extends MoveStratergy{
    public LeftSide(Position head, char[][] board, Queue<Position> queue) {
        super(head, board, queue);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean possible() {
        int col=head.col-1;
        return col>=0;
    }

    @Override
    public Position move() {
      return executemove(board, head.row, head.col-1, queue);
    }
    
}

class MakeMove{
    //Factory Design Pattern
    public Position makeMove(char input, Position head, char[][] board, Queue<Position> queue) {
    MoveStratergy stratergy;
    if (input == 'U') {
        stratergy = new UpSide(head, board, queue);
    } else if (input == 'D') {
        stratergy = new DownSide(head, board, queue);
    } else if (input == 'L') {
        stratergy = new LeftSide(head, board, queue);
    } else if (input == 'R') {
        stratergy = new RightSide(head, board, queue);
    } else {
        System.out.println("Kindly Enter the valid input!");
        return null;
    }

    // After setting stratergy check it is possible or not
    boolean possible = stratergy.possible();

    // If the movement is possible, then
    if (possible) {
        head = stratergy.move();
        // Have to change the head to input
        return head;
    }
    // Change game state to Lost...
    else {
        new LostState(board);
        // Make sure should have to stop after here...
    }

    return null;
  }
}

//Loss state
class LostState{
    static boolean lost=false;
    //Score
    int c=0;
    public LostState(char[][] board){
        lost=true;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]=='.'){
                    c++;
                }
            }
        }
        System.out.println("Game Over!\nScore is: "+c);
    }
}

public class joker {
    public static void main(String[] args) {
        BoardGame snake=new SnakeGame();
        snake.start();
    }
}