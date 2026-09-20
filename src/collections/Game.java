package collections;


import java.util.Scanner;

public class Game
{
    public static HumanPlayer human = new HumanPlayer('X');
    public static HumanPlayer human2 = new HumanPlayer('O');
    public static AIPlayer AI = new AIPlayer('O', 4);
    private static Board board = new Board();
    
    public Game() {
        
    }
    
    public static void main(String[] args) 
    {
        Game game= new Game();
        game.play();
    }
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(board.toString());
        while(!board.isFull()) {
            //The Player's moves
            int col = human.getMove()-1;
            int row = board.placePiece(col, human.getPiece());
            while(row == -1) {
                System.out.println("Hey you can't play there, do something else");
                col = human.getMove() -1;
                row = board.placePiece(col, human.getPiece());
            }
            System.out.println(board.toString());
            if(board.checkWin(row, col, human.getPiece())) {
                System.out.print("You Win! ");
                break;
            }
            //AI's Moves
            int col2 = AI.bestMove(board);
            int row2 = board.placePiece(col2, AI.getPiece());
            
            System.out.println(board.toString());
            if(board.checkWin(row2, col2, AI.getPiece())) {
                break;
            }
        }
        while(true) {
            System.out.println("GO AGAIN? [Y or N]");
            String goAgain = scanner.nextLine();
            if(goAgain.equals("Y") || goAgain.equals("y")) {
                restart();
                play();
                return;
            }
            else if(goAgain.equals("N") || goAgain.equals("n")){
                break;
            }
        }
        
        
    }
    
    public void restart() {
        human.newGame();
        AI.newGame();
        board.reset();
    }
  
}
