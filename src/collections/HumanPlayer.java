package collections;

import java.util.Scanner;

/**
 * Represents a human player in a Connect Four game.
 */
public class HumanPlayer extends Player
{
    /**
     * Constructs a human player with the specified piece.
     *
     * @param piece the piece assigned to the player
     */
    public HumanPlayer(char piece)
    {
        super(piece);
    }

    /**
     * Prompts the human player to select a column.
     *
     * @return the column selected by the player
     */
    public int getMove()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(
            "HI, WHICH COLUMN WOULD YOU LIKE TO PLACE YOUR PIECE IN: "
                + this.getPiece());

        int c = sc.nextInt();
        return c;
    }
}