import java.util.Scanner;

enum Symbol {
    X, O, EMPTY
}

class Position {
    int row;
    int col;

    Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

interface PlayerStrategy {
    Position makeMove(Board board);
}

class HumanPlayer implements PlayerStrategy {

    private final Scanner scanner = new Scanner(System.in);
    private final String playerName;

    HumanPlayer(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public Position makeMove(Board board) {

        while (true) {

            try {
                System.out.println(playerName + " Enter Row and Column:");

                int row = scanner.nextInt();
                int col = scanner.nextInt();

                Position move = new Position(row, col);

                if (board.isValidMove(move)) {
                    return move;
                }

                System.out.println("Invalid Move. Try Again!");

            } catch (Exception e) {

                System.out.println("Please enter integer values only.");
                scanner.nextLine();
            }
        }
    }
}

class Player {

    Symbol symbol;
    PlayerStrategy strategy;

    Player(Symbol symbol, PlayerStrategy strategy) {
        this.symbol = symbol;
        this.strategy = strategy;
    }
}

interface GameState {
    boolean isGameOver();
}

class XTurnState implements GameState {
    public boolean isGameOver() {
        return false;
    }
}

class OTurnState implements GameState {
    public boolean isGameOver() {
        return false;
    }
}

class XWinState implements GameState {
    public boolean isGameOver() {
        return true;
    }
}

class OWinState implements GameState {
    public boolean isGameOver() {
        return true;
    }
}

class DrawState implements GameState {
    public boolean isGameOver() {
        return true;
    }
}

class GameContext {

    private GameState state = new XTurnState();

    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public boolean isGameOver() {
        return state.isGameOver();
    }
}

class Board {

    private final Symbol[][] board;

    Board(int size) {

        board = new Symbol[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = Symbol.EMPTY;
            }
        }
    }

    boolean isValidMove(Position move) {

        return move.row >= 0 &&
                move.row < board.length &&
                move.col >= 0 &&
                move.col < board.length &&
                board[move.row][move.col] == Symbol.EMPTY;
    }

    void makeMove(Position move, Symbol symbol) {
        board[move.row][move.col] = symbol;
    }

    boolean isFull() {

        for (Symbol[] row : board) {
            for (Symbol cell : row) {
                if (cell == Symbol.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    Symbol checkWinner() {

        int n = board.length;

        // rows
        for (int i = 0; i < n; i++) {

            if (board[i][0] != Symbol.EMPTY) {

                boolean win = true;

                for (int j = 1; j < n; j++) {
                    if (board[i][j] != board[i][0]) {
                        win = false;
                        break;
                    }
                }

                if (win) return board[i][0];
            }
        }

        // columns
        for (int j = 0; j < n; j++) {

            if (board[0][j] != Symbol.EMPTY) {

                boolean win = true;

                for (int i = 1; i < n; i++) {
                    if (board[i][j] != board[0][j]) {
                        win = false;
                        break;
                    }
                }

                if (win) return board[0][j];
            }
        }

        // main diagonal
        if (board[0][0] != Symbol.EMPTY) {

            boolean win = true;

            for (int i = 1; i < n; i++) {
                if (board[i][i] != board[0][0]) {
                    win = false;
                    break;
                }
            }

            if (win) return board[0][0];
        }

        // anti diagonal
        if (board[0][n - 1] != Symbol.EMPTY) {

            boolean win = true;

            for (int i = 1; i < n; i++) {
                if (board[i][n - 1 - i] != board[0][n - 1]) {
                    win = false;
                    break;
                }
            }

            if (win) return board[0][n - 1];
        }

        return Symbol.EMPTY;
    }

    void printBoard() {

        for (Symbol[] row : board) {

            for (Symbol cell : row) {
                System.out.print(cell + " ");
            }

            System.out.println();
        }

        System.out.println();
    }
}

class TicTacToe {

    private final Board board;
    private final Player playerX;
    private final Player playerO;

    private Player currentPlayer;

    private final GameContext context;

    TicTacToe(PlayerStrategy xStrategy, PlayerStrategy oStrategy) {

        board = new Board(3);

        playerX = new Player(Symbol.X, xStrategy);
        playerO = new Player(Symbol.O, oStrategy);

        currentPlayer = playerX;

        context = new GameContext();
    }

    public void play() {

        while (!context.isGameOver()) {

            board.printBoard();

            Position move =
                    currentPlayer.strategy.makeMove(board);

            board.makeMove(move, currentPlayer.symbol);

            Symbol winner = board.checkWinner();

            if (winner == Symbol.X) {
                context.setState(new XWinState());
            }
            else if (winner == Symbol.O) {
                context.setState(new OWinState());
            }
            else if (board.isFull()) {
                context.setState(new DrawState());
            }

            if (!context.isGameOver()) {
                switchPlayer();
            }
        }

        board.printBoard();
        announceResult();
    }

    private void switchPlayer() {

        currentPlayer =
                (currentPlayer == playerX)
                        ? playerO
                        : playerX;
    }

    private void announceResult() {

        GameState state = context.getState();

        if (state instanceof XWinState) {
            System.out.println("Player X Wins!");
        }
        else if (state instanceof OWinState) {
            System.out.println("Player O Wins!");
        }
        else {
            System.out.println("Match Draw!");
        }
    }
}

public class joker {

    public static void main(String[] args) {

        PlayerStrategy x =
                new HumanPlayer("Player X");

        PlayerStrategy o =
                new HumanPlayer("Player O");

        TicTacToe game =
                new TicTacToe(x, o);

        game.play();
    }
}
