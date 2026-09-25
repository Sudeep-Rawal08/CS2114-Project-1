package collections;

/**
 * Represents a player in a Connect Four game.
 */
public class Player
{
    private char piece;

    /**
     * Constructs a player with the specified piece.
     *
     * @param piece the piece assigned to the player
     */
    public Player(char piece)
    {
        this.piece = piece;
    }

    /**
     * Gets the piece belonging to this player.
     *
     * @return the player's piece
     */
    public char getPiece()
    {
        return piece;
    }

    /**
     * Switches the player's piece between X and O
     * when starting a new game.
     */
    public void newGame()
    {
        if (piece == 'O')
        {
            piece = 'X';
        }
        else
        {
            piece = 'O';
        }
    }
}