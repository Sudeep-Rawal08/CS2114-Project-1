package collections;

import java.util.Scanner;

/**
 * Runs a game of Connect Four between a human player and an AI player.
 */
public class Game
{
    public static HumanPlayer human = new HumanPlayer('X');
    public static HumanPlayer human2 = new HumanPlayer('O');
    public static AIPlayer AI = new AIPlayer('O', 2);
    private static Board board = new Board();

    /**
     * Constructs a new Game.
     */
    public Game()
    {
    }

    /**
     * Starts the Connect Four game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }

    /**
     * Runs the game and handles player and AI turns.
     */
    public void play()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println(
            "Select AI Difficulty (Easy, Medium, Hard)");

        String difficulty = scanner.nextLine();

        if (difficulty.equalsIgnoreCase("Easy"))
        {
            AI.setDepth(2);
        }
        else if (difficulty.equalsIgnoreCase("Medium"))
        {
            AI.setDepth(4);
        }
        else if (difficulty.equalsIgnoreCase("Hard"))
        {
            AI.setDepth(8);
        }
        else
        {
            System.out.println("Invalid difficulty. Defaulting to Easy.");
            AI.setDepth(2);
        }

        System.out.println(board.toString());

        while (!board.isFull())
        {
            // The Player's moves
            if (board.currTurn == human.getPiece())
            {
                int col = human.getMove() - 1;
                int row = board.placePiece(col, human.getPiece());

                while (row == -1)
                {
                    System.out.println(
                        "Hey you can't play there, do something else");

                    col = human.getMove() - 1;
                    row = board.placePiece(col, human.getPiece());
                }

                System.out.println(board.toString());

                if (board.checkWin(row, col, human.getPiece()))
                {
                    System.out.print("You Win! ");
                    break;
                }
            }
            else
            {
                // AI's Moves
                int col2 = AI.bestMove(board);
                int row2 = board.placePiece(col2, AI.getPiece());

                System.out.println(board.toString());

                if (board.checkWin(row2, col2, AI.getPiece()))
                {
                    break;
                }
            }
        }

        while (true)
        {
            System.out.println("GO AGAIN? [Y or N]");

            String goAgain = scanner.nextLine();

            if (goAgain.equals("Y") || goAgain.equals("y"))
            {
                restart();
                play();
                return;
            }
            else if (goAgain.equals("N") || goAgain.equals("n"))
            {
                break;
            }
        }
    }

    /**
     * Resets the players and board for a new game.
     */
    public void restart()
    {
        human.newGame();
        AI.newGame();
        board.reset();
    }
}